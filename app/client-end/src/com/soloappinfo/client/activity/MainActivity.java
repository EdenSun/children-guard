package com.soloappinfo.client.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.soloappinfo.client.R;
import com.soloappinfo.client.db.dao.AppDao;
import com.soloappinfo.client.db.dao.ChildInfoDao;
import com.soloappinfo.client.db.dao.ChildSettingDao;
import com.soloappinfo.client.service.LocationMonitorService;
import com.soloappinfo.client.service.WatchDogService;
import com.soloappinfo.client.task.JPushRegistionIdUploadRunnable;
import com.soloappinfo.client.util.BroadcastActionConstants;
import com.soloappinfo.client.util.Callback;
import com.soloappinfo.client.util.Config;
import com.soloappinfo.client.util.DeviceHelper;
import com.soloappinfo.client.util.JSONUtil;
import com.soloappinfo.client.util.RequestHelper;
import com.soloappinfo.client.util.RequestURLConstants;
import com.soloappinfo.client.util.UIUtil;

import eden.sun.childrenguard.server.dto.ChildInfoViewDTO;
import eden.sun.childrenguard.server.dto.ChildSettingViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;

public class MainActivity extends CommonActivity {
	protected static final String TAG = "MainActivity";
	private TextView text;
	private AppDao appDao;
	private ChildInfoDao childInfoDao;
	private ChildSettingDao childSettingDao;
	private int syncFinishCnt;
	private WatchDogService watchDogService;
	private LocationMonitorService locationMonitorService;
	private SharedPreferences sharedPref;
	
	private ServiceConnection watchDogServiceConnection = new ServiceConnection() {
		Context context = MainActivity.this;
		public void onServiceConnected(ComponentName className, IBinder service) {
			watchDogService = ((WatchDogService.LocalBinder) service).getService();

			watchDogService.syncAppFromServer(new Callback(){
				@Override
				public void callback() {
					syncFinished();
				}
			});
			
			/*Toast.makeText(context,
					"watch dog service connected", Toast.LENGTH_SHORT)
					.show();*/
		}

		public void onServiceDisconnected(ComponentName className) {
			watchDogService = null;
			Toast.makeText(context,
					"S.O.L.O service disconnected", Toast.LENGTH_SHORT)
					.show();
		}
	};
	
	private ServiceConnection locationMonitorServiceConnection = new ServiceConnection() {
		Context context = MainActivity.this;
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			locationMonitorService = ((LocationMonitorService.LocalBinder) service).getService();

			Toast.makeText(context,
					"S.O.L.O Monitor Service connected", Toast.LENGTH_SHORT)
					.show();			
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			locationMonitorService = null;
			Toast.makeText(context,
					"S.O.L.O Monitor Service disconnected", Toast.LENGTH_SHORT)
					.show();			
		}

	};
	
	private BroadcastReceiver initServiceAppDataReceiver;
	private BroadcastReceiver initServicePresetLockAppDataReceiver;
	
	private Integer childId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sharedPref = getSharedPreferences( getString(R.string.preference_file_key_common),Context.MODE_PRIVATE);
		childId = sharedPref.getInt(getString(R.string.sp_key_login_child_id), 0);
		Log.d(TAG, "**login child id : **** " + childId);
		
		initJPush();
		
		IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BroadcastActionConstants.INIT_SERVICE_APP_DATA);
        initServiceAppDataReceiver = new BroadcastReceiver() {
        	 
            @Override
            public void onReceive(Context context, Intent intent) {
            	String action = intent.getAction();
                watchDogService.initAppData();
            }
        };
        registerReceiver( initServiceAppDataReceiver, intentFilter);
        
        
        IntentFilter presetLockAppIntentFilter = new IntentFilter();
        presetLockAppIntentFilter.addAction(BroadcastActionConstants.INIT_SERVICE_PRESET_LOCK_APP_DATA);
        initServicePresetLockAppDataReceiver = new BroadcastReceiver() {
        	 
            @Override
            public void onReceive(Context context, Intent intent) {
            	String action = intent.getAction();
                watchDogService.initPresetlockData();
            }
        };
        registerReceiver( initServicePresetLockAppDataReceiver, presetLockAppIntentFilter);
        
        
		
		startServices();
		
		appDao = new AppDao(this);
		childInfoDao = new ChildInfoDao(this);
		childSettingDao = new ChildSettingDao(this);
		
		text = (TextView)findViewById(R.id.text);
		
		text.setText("Retrieve person infomation...");
		syncChildInfoFromServer();
		syncChildSettingFromServer();
		
	}

	private void initJPush() {
		JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        
        JPushRegistionIdUploadRunnable runnable = new JPushRegistionIdUploadRunnable(this,childId);
        new Thread(runnable).start();
	}
	

	private void startServices() {
		startWatchDogService();
		startLocationMonitorService();		
	}

	private void startLocationMonitorService() {
		Intent intent = new Intent(this, LocationMonitorService.class);
		
		startService(intent);

		bindService(intent, locationMonitorServiceConnection, Context.BIND_AUTO_CREATE); 
	}

	private void startWatchDogService() {
		Intent appLockIntent = new Intent(this, WatchDogService.class);
	
		startService(appLockIntent);
		bindService(appLockIntent, watchDogServiceConnection, Context.BIND_AUTO_CREATE); 
	}
	

	private void syncChildSettingFromServer() {
		RequestHelper helper = getRequestHelper();
		String imei = DeviceHelper.getIMEI(this);
		String url = String.format(
				Config.BASE_URL_MVC + RequestURLConstants.URL_RETRIEVE_CHID_SETTING + "?imei=%1$s",  
				imei);  

		helper.doGet(
			url,
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					Log.d(TAG, response);
					ViewDTO<ChildSettingViewDTO> view = JSONUtil.getRetrieveChildSettingView(response);
			    	
			    	if( view.getMsg().equals(ViewDTO.MSG_SUCCESS)){
			    		if( view.getData() != null ){
			    			ChildSettingViewDTO childSettingView = view.getData();

			    			childSettingDao.addOrUpdate(childSettingView);
			    		}
			    		
			    		syncFinished();
			    	}else{
			    		AlertDialog.Builder dialog = UIUtil.getErrorDialog(MainActivity.this,view.getInfo());
			    		
						dialog.show();
			    	}
			    	
				}

			}, 
			new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					Log.e(TAG, error.getMessage(), error);
					AlertDialog.Builder dialog = UIUtil.getErrorDialog(MainActivity.this,error.getMessage());
		    		
					dialog.show();
			}
		});	
	}

	private void syncChildInfoFromServer() {
		RequestHelper helper = getRequestHelper();
		String imei = DeviceHelper.getIMEI(this);
		String url = String.format(
				Config.BASE_URL_MVC + RequestURLConstants.URL_RETRIEVE_CHID_INFO + "?imei=%1$s",  
				imei);  

		helper.doGet(
			url,
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					Log.d(TAG, response);
					ViewDTO<ChildInfoViewDTO> view = JSONUtil.getRetrieveChildInfoView(response);
			    	
			    	if( view.getMsg().equals(ViewDTO.MSG_SUCCESS)){
			    		if( view.getData() != null ){
			    			ChildInfoViewDTO childInfoView = view.getData();

			    			childInfoDao.addOrUpdate(childInfoView);
			    		}
			    		syncFinished();
			    		
			    	}else{
			    		AlertDialog.Builder dialog = UIUtil.getErrorDialog(MainActivity.this,view.getInfo());
			    		
						dialog.show();
			    	}
			    	
				}

			}, 
			new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					Log.e(TAG, error.getMessage(), error);
					AlertDialog.Builder dialog = UIUtil.getErrorDialog(MainActivity.this,error.getMessage());
		    		
					dialog.show();
			}
		});	
	}

	/*private void syncAppFromServer() {
		RequestHelper helper = getRequestHelper();
		String imei = DeviceHelper.getIMEI(this);
		String url = String.format(
				Config.BASE_URL_MVC + RequestURLConstants.URL_LIST_CHILD_APP_INFO + "?imei=%1$s",  
				imei);  

		helper.doGet(
			url,
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					Log.d(TAG, response);
					ViewDTO<List<AppViewDTO>> view = JSONUtil.getListChildAppInfoView(response);
			    	
			    	if( view.getMsg().equals(ViewDTO.MSG_SUCCESS)){
			    		if( view.getData() != null ){
			    			List<AppViewDTO> appList = view.getData();

			    			appDao.clearAll();
			    			appDao.batchAdd(appList);
			    			
			    			//init locked app list in watch dog service
			    			watchDogService.initAppData();
			    		}
			    		
			    		syncFinished();
			    	}else{
			    		AlertDialog.Builder dialog = UIUtil.getErrorDialog(MainActivity.this,view.getInfo());
			    		
						dialog.show();
			    	}
			    	
				}

			}, 
			new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					Log.e(TAG, error.getMessage(), error);
					AlertDialog.Builder dialog = UIUtil.getErrorDialog(MainActivity.this,error.getMessage());
		    		
					dialog.show();
			}
		});	
	}*/

	private synchronized void syncFinished(){
		syncFinishCnt++;
		
		if( getSyncFinishCnt() == 3 ){
			text.setText("Retrieve person infomation...Finished");
		}
	}
	
	private synchronized int getSyncFinishCnt(){
		return syncFinishCnt;
	}
	
	
	@Override
	protected void onDestroy() {
		if (watchDogServiceConnection != null)  
	    {  
	        unbindService(watchDogServiceConnection);  
	        watchDogServiceConnection = null;  
	    }
		
		if (locationMonitorServiceConnection != null)  
	    {  
	        unbindService(locationMonitorServiceConnection);  
	        locationMonitorServiceConnection = null;  
	    }
		
		unregisterReceiver(initServiceAppDataReceiver);
	    super.onDestroy();  
	}
}
