package eden.sun.childrenguard.child.activity;

import java.util.List;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import eden.sun.childrenguard.child.R;
import eden.sun.childrenguard.child.db.dao.AppDao;
import eden.sun.childrenguard.child.db.dao.ChildInfoDao;
import eden.sun.childrenguard.child.db.dao.ChildSettingDao;
import eden.sun.childrenguard.child.service.LocationMonitorService;
import eden.sun.childrenguard.child.service.WatchDogService;
import eden.sun.childrenguard.child.util.Config;
import eden.sun.childrenguard.child.util.DeviceHelper;
import eden.sun.childrenguard.child.util.JSONUtil;
import eden.sun.childrenguard.child.util.RequestHelper;
import eden.sun.childrenguard.child.util.RequestURLConstants;
import eden.sun.childrenguard.child.util.UIUtil;
import eden.sun.childrenguard.server.dto.AppViewDTO;
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
	
	private ServiceConnection watchDogServiceConnection = new ServiceConnection() {
		Context context = MainActivity.this;
		public void onServiceConnected(ComponentName className, IBinder service) {
			watchDogService = ((WatchDogService.LocalBinder) service).getService();

			Toast.makeText(context,
					"watch dog service connected", Toast.LENGTH_SHORT)
					.show();
		}

		public void onServiceDisconnected(ComponentName className) {
			watchDogService = null;
			Toast.makeText(context,
					"watch dog service disconnected", Toast.LENGTH_SHORT)
					.show();
		}
	};
	
	private ServiceConnection locationMonitorServiceConnection = new ServiceConnection() {
		Context context = MainActivity.this;
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			locationMonitorService = ((LocationMonitorService.LocalBinder) service).getService();

			Toast.makeText(context,
					"locationMonitorService connected", Toast.LENGTH_SHORT)
					.show();			
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			locationMonitorService = null;
			Toast.makeText(context,
					"locationMonitorService disconnected", Toast.LENGTH_SHORT)
					.show();			
		}

	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//initJPush();
		
		startServices();
		
		appDao = new AppDao(this);
		childInfoDao = new ChildInfoDao(this);
		childSettingDao = new ChildSettingDao(this);
		
		text = (TextView)findViewById(R.id.text);
		
		text.setText("Retrieve person infomation...");
		syncChildInfoFromServer();
		syncChildSettingFromServer();
		syncAppFromServer();
		
	}

	private void initJPush() {
		JPushInterface.setDebugMode(true);
        JPushInterface.init(this);		
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

	private void syncAppFromServer() {
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
	}

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
	    super.onDestroy();  
	}
}
