package eden.sun.childrenguard.runnable;


import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import eden.sun.childrenguard.activity.ChildrenListActivity;
import eden.sun.childrenguard.activity.SplashActivity;
import eden.sun.childrenguard.helper.RequestHelper;
import eden.sun.childrenguard.server.dto.LoginViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.util.Config;
import eden.sun.childrenguard.util.JSONUtil;
import eden.sun.childrenguard.util.RequestURLConstants;
import eden.sun.childrenguard.util.ShareDataKey;

public class JPushRegistionIdUploadRunnable implements Runnable{
	private static final String TAG = "JPushRegistionIdUploadRunnable";
	private Context context;
	private String accessToken;
	
	public JPushRegistionIdUploadRunnable(Context context, String accessToken) {
		super();
		this.context = context;
		this.accessToken = accessToken;
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
				
				doUpdateRegistionId(accessToken,registionId);
				
				isLoop = false;
			}
		}
		
	}


	private void doUpdateRegistionId(String accessToken,String registionId) {
		String url = Config.BASE_URL_MVC + RequestURLConstants.URL_UPDATE_REGISTRATION_ID ;  
		Map<String,String> param = new HashMap<String,String>();
		param.put("accessToken", accessToken);
		param.put("registrationId", registionId);
		
		RequestHelper.getInstance(context).doPost(
			url,
			param,
			this.getClass(),
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
