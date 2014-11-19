package eden.sun.childrenguard.child.receiver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import eden.sun.childrenguard.child.activity.MainActivity;
import eden.sun.childrenguard.child.db.dao.AppDao;
import eden.sun.childrenguard.child.db.dao.ChildSettingDao;
import eden.sun.childrenguard.child.util.BroadcastActionConstants;
import eden.sun.childrenguard.child.util.Config;
import eden.sun.childrenguard.child.util.DeviceHelper;
import eden.sun.childrenguard.child.util.JSONUtil;
import eden.sun.childrenguard.child.util.RequestHelper;
import eden.sun.childrenguard.child.util.RequestURLConstants;
import eden.sun.childrenguard.child.util.UIUtil;
import eden.sun.childrenguard.server.dto.AppViewDTO;
import eden.sun.childrenguard.server.dto.ChildSettingViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;

public class JPushCustomMsgReceiver extends BroadcastReceiver {
	private static final String TAG = "JPushCustomMsgReceiver";

	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		Log.d(TAG, "onReceive - " + intent.getAction());

		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
			Log.d(TAG, "JPush register returned - " + intent.getAction());
			String registionId = JPushInterface.getRegistrationID(context);
			if( registionId != null ){
				String imei = DeviceHelper.getIMEI(context);
				saveRegistionId(context,imei,registionId);
			}
		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent
				.getAction())) {
			Log.d(TAG, "收到了自定义消息。消息内容是："
					+ bundle.getString(JPushInterface.EXTRA_MESSAGE));
			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
			if( message != null ){
				if( message.equals("Apply App Changes") ){
					// reload app lock/unlock setting

					syncAppFromServer(context);
					
				}else if( message.equals("Apply Setting Changes")){
					// reload settings
					
					syncChildSettingFromServer(context);
				}
			}
			
			Toast.makeText(context, bundle.getString(JPushInterface.EXTRA_MESSAGE) , 3000);
		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
				.getAction())) {
			System.out.println("收到了通知");
			// 在这里可以做些统计，或者做些其他工作
		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
				.getAction())) {
			System.out.println("用户点击打开了通知");
			// 在这里可以自己写代码去定义用户点击后的行为
			/*Intent i = new Intent(context, TestActivity.class); // 自定义打开的界面
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);*/

		} else {
			Log.d(TAG, "Unhandled intent - " + intent.getAction());
		}
	}

	private void saveRegistionId(Context context,String imei, String registionId) {
		RequestHelper helper = RequestHelper.getInstance(context);	
		String url = Config.BASE_URL_MVC + RequestURLConstants.URL_SAVE_REGISTION_ID;

		Map<String,String> param = new HashMap<String,String>();
		param.put("imei", imei);
		param.put("registionId", registionId);
		helper.doPost(
			url,
			param,
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					ViewDTO<Boolean> view = JSONUtil.getSaveRegistionIdView(response);
			    	
			    	if( view.getMsg().equals(ViewDTO.MSG_SUCCESS)){
			    		Log.i(TAG, "Save registion id success.");
			    	}else{
			    		Log.e(TAG, "Save registion id failure.");
			    	}
				}
			}, 
			new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					Log.e(TAG, error.getMessage(), error);
			}
		});
	}
	
	private void syncAppFromServer(final Context context) {
		RequestHelper helper = RequestHelper.getInstance(context);
		String imei = DeviceHelper.getIMEI(context);
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
			    			AppDao appDao = new AppDao(context);
			    			
			    			appDao.clearAll();
			    			appDao.batchAdd(appList);
			    			
			    			//init locked app list in watch dog service
			    			//watchDogService.initAppData();
			    			Intent intent = new Intent();
			    			intent.setAction(BroadcastActionConstants.INIT_SERVICE_APP_DATA);
		    			   
			    			context.sendBroadcast(intent);
			    		}
			    		
			    	}else{
			    		Log.e(TAG, "Server error.");
			    	}
			    	
				}

			}, 
			new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					Log.e(TAG, error.getMessage());
			}
		});	
	}
	
	
	private void syncChildSettingFromServer(final Context context) {
		RequestHelper helper = RequestHelper.getInstance(context);
		String imei = DeviceHelper.getIMEI(context);
		String url = String.format(
				Config.BASE_URL_MVC + RequestURLConstants.URL_RETRIEVE_CHID_SETTING + "?imei=%1$s",  
				imei);  
		final ChildSettingDao childSettingDao = new ChildSettingDao(context);
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
			    			
			    			Log.e(TAG, "update setting success." + childSettingDao.getLockCallSwitch());
			    		}
			    		
			    	}else{
			    		Log.e(TAG, "Server error.");
			    	}
			    	
				}

			}, 
			new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					Log.e(TAG, error.getMessage(), error);
			}
		});	
	}
}
