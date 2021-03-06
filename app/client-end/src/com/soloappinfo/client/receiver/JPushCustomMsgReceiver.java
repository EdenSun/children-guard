package com.soloappinfo.client.receiver;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.soloappinfo.client.db.dao.AppDao;
import com.soloappinfo.client.db.dao.ChildSettingDao;
import com.soloappinfo.client.db.dao.PresetLockAppDao;
import com.soloappinfo.client.db.dao.PresetLockDao;
import com.soloappinfo.client.db.model.PresetLockApp;
import com.soloappinfo.client.util.BroadcastActionConstants;
import com.soloappinfo.client.util.Config;
import com.soloappinfo.client.util.DeviceHelper;
import com.soloappinfo.client.util.JSONUtil;
import com.soloappinfo.client.util.RequestHelper;
import com.soloappinfo.client.util.RequestURLConstants;

import eden.sun.childrenguard.server.dto.AppViewDTO;
import eden.sun.childrenguard.server.dto.ChildSettingViewDTO;
import eden.sun.childrenguard.server.dto.PresetLockViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;

public class JPushCustomMsgReceiver extends BroadcastReceiver {
	private static final String TAG = "JPushCustomMsgReceiver";

	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		Log.d(TAG, "onReceive - " + intent.getAction());

		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
			Log.d(TAG, "JPush register returned - " + intent.getAction());
			/*String registionId = JPushInterface.getRegistrationID(context);
			if( registionId != null ){
				String imei = DeviceHelper.getIMEI(context);
				saveRegistionId(context,imei,registionId);
			}*/
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
				}else if( message.equals("Re-sync Schedule Lock Request")){
					// re-sync schedule lock setting
					
					syncScheduleLockSettingFromServer(context);
				}else if( message.equals("test_online") ){
					// online test
					
				}
			}
			
			//Toast.makeText(context, bundle.getString(JPushInterface.EXTRA_MESSAGE) , 3000);
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

	private void syncScheduleLockSettingFromServer(final Context context) {
		RequestHelper helper = RequestHelper.getInstance(context);
		String imei = DeviceHelper.getIMEI(context);
		String url = String.format(
				Config.BASE_URL_MVC + RequestURLConstants.URL_LIST_ALL_SCHEDULE_LOCK_BY_IMEI + "?imei=%1$s",  
				imei);  

		helper.doGet(
			url,
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					Log.d(TAG, response);
					ViewDTO<List<PresetLockViewDTO>> view = JSONUtil.getListAllScheduleLockByImeiDataView(response);
			    	
			    	if( view.getMsg().equals(ViewDTO.MSG_SUCCESS)){
			    		if( view.getData() != null ){
			    			List<PresetLockViewDTO> presetLockViewList = view.getData();
			    			
			    			if( presetLockViewList != null ){
			    				PresetLockDao presetLockDao = new PresetLockDao(context);
			    				PresetLockAppDao presetLockAppDao = new PresetLockAppDao(context);
			    				presetLockDao.clearAll();
			    				presetLockAppDao.clearAll();

			    				for(PresetLockViewDTO presetLockData:presetLockViewList){
			    					// save preset lock
					    			presetLockDao.add(presetLockData);
					    			
					    			// save preset lock app
					    			List<AppViewDTO> appList = presetLockData.getAppList();
					    			
					    			if( appList != null ){
					    				List<PresetLockApp> presetLockAppList = new ArrayList<PresetLockApp>();
					    				
					    				
					    				int id = 1;
					    				for( AppViewDTO appViewDTO: appList){
					    					PresetLockApp presetLockApp = new PresetLockApp();
					    					presetLockApp.setAppId(appViewDTO.getId());
					    					presetLockApp.setId(id++);
					    					presetLockApp.setPresetLockId(presetLockData.getId());
					    					presetLockAppList.add(presetLockApp);
					    				}
					    				presetLockAppDao.batchAdd(presetLockAppList);
					    			}
			    				}
			    				
			    				
			    				Intent intent = new Intent();
				    			intent.setAction(BroadcastActionConstants.INIT_SERVICE_PRESET_LOCK_APP_DATA);
				    			context.sendBroadcast(intent);
			    			}
			    			
			    			/*PresetLockViewDTO presetLockData = view.getData();
			    			
			    			// save preset lock
			    			PresetLockDao presetLockDao = new PresetLockDao(context);
			    			presetLockDao.clearAll();
			    			presetLockDao.add(presetLockData);
			    			
			    			// save preset lock app
			    			PresetLockAppDao presetLockAppDao = new PresetLockAppDao(context);
			    			List<AppViewDTO> appList = presetLockData.getAppList();
			    			
			    			if( appList != null ){
			    				List<PresetLockApp> presetLockAppList = new ArrayList<PresetLockApp>();
			    				presetLockAppDao.clearAll();
			    				
			    				int id = 1;
			    				for( AppViewDTO appViewDTO: appList){
			    					PresetLockApp presetLockApp = new PresetLockApp();
			    					presetLockApp.setAppId(appViewDTO.getId());
			    					presetLockApp.setId(id++);
			    					presetLockApp.setPresetLockId(presetLockData.getId());
			    					presetLockAppList.add(presetLockApp);
			    				}
			    				presetLockAppDao.batchAdd(presetLockAppList);
			    			}
			    			
			    			Intent intent = new Intent();
			    			intent.setAction(BroadcastActionConstants.INIT_SERVICE_PRESET_LOCK_APP_DATA);
			    			context.sendBroadcast(intent);*/
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

	/*private void saveRegistionId(Context context,String imei, String registionId) {
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
	}*/
	
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
			    			
			    			Log.d(TAG, "update setting success." + childSettingDao.getLockCallSwitch());
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
