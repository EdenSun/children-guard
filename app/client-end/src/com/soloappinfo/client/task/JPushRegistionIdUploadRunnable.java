package com.soloappinfo.client.task;


import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.soloappinfo.client.util.Config;
import com.soloappinfo.client.util.JSONUtil;
import com.soloappinfo.client.util.RequestHelper;
import com.soloappinfo.client.util.RequestURLConstants;

import eden.sun.childrenguard.server.dto.ViewDTO;


public class JPushRegistionIdUploadRunnable implements Runnable{
	private static final String TAG = "JPushRegistionIdUploadRunnable";
	private Context context;
	private Integer childId;
	
	public JPushRegistionIdUploadRunnable(Context context, Integer childId) {
		super();
		this.context = context;
		this.childId = childId;
	}


	@Override
	public void run() {
		boolean isLoop = true;
		
		while( isLoop ){
			String registionId = JPushInterface.getRegistrationID(context);
			if( registionId == null || registionId.trim().length() == 0 ){
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					Log.e(TAG, "thread interrupted.", e);
				}
			}else{
				Log.d(TAG, "** Success to retrieve registration id ** :" + registionId);
				
				doUpdateRegistionId(childId,registionId);
				
				isLoop = false;
			}
		}
		
	}


	private void doUpdateRegistionId(Integer childId,String registionId) {
		String url = Config.BASE_URL_MVC + RequestURLConstants.URL_UPDATE_REGISTRATION_ID ;  
		Map<String,String> param = new HashMap<String,String>();
		param.put("childId", childId.toString());
		param.put("registrationId", registionId);
		
		RequestHelper.getInstance(context).doPost(
			url,
			param,
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					Log.d(TAG, "response");
					ViewDTO<Boolean> view = JSONUtil.getUpdateRegistrationIdView(response);
					
					if( view.getMsg().equals(ViewDTO.MSG_SUCCESS) && view.getData()!=null && view.getData().booleanValue()==true){
						Log.d(TAG, "update registration id success.");
					}else{
						//toLoginActivity();
						Log.e(TAG, "update registration id fail.");
					}
					
				}

			}, 
			new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					//toLoginActivity();
					Log.e(TAG, "update registration id error.");
				}
			}
		);		
	}		
		

}
