package com.soloappinfo.client.activity;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.soloappinfo.client.R;
import com.soloappinfo.client.util.Callback;
import com.soloappinfo.client.util.Config;
import com.soloappinfo.client.util.DeviceHelper;
import com.soloappinfo.client.util.JSONUtil;
import com.soloappinfo.client.util.RequestHelper;
import com.soloappinfo.client.util.RequestURLConstants;
import com.soloappinfo.client.util.UIUtil;

import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;

public class InitActivity extends CommonBindServiceActivity {
	private static final String TAG = "InitActivity";
	private TextView text;
	private Timer timer;
	private SharedPreferences sharedPref;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//initJPush();
		sharedPref = getSharedPreferences( getString(R.string.preference_file_key_common),Context.MODE_PRIVATE);
		
		setContentView(R.layout.activity_init);
		timer = new Timer(true);

		initComponent();
		
		initService();
		
	}
	
	/*private void initJPush() {
		JPushInterface.setDebugMode(true);
        JPushInterface.init(this);		
	}*/
	
	@Override
	protected void onPause() {
		super.onPause();
	}

		
	private void initComponent() {
		text = (TextView)findViewById(R.id.text);
	}

	private void initService() {
		startMainService();
		//bindMainService();		
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		isActivate();
	}

	private void isActivate() {
		String imei = DeviceHelper.getIMEI(this);
		if( imei != null ){
			/* 通过 imei 检查是否已经激活
			 * 若已经激活，自动登录
			 * 若没有激活，跳转激活页
			 */
			/*String title = "Login";
			String msg = "Please wait...";
			showProgressDialog(title,msg);*/
			
			RequestHelper helper = getRequestHelper();
			String url = String.format(
					Config.BASE_URL_MVC + RequestURLConstants.URL_IS_ACTIVATE + "?imei=%1$s",  
					imei);  

			helper.doGet(
				url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						ViewDTO<ChildViewDTO> view = JSONUtil.getIsActivateView(response);
				    	
				    	if( view.getMsg().equals(ViewDTO.MSG_SUCCESS)){
				    		if( view.getData() == null ){
				    			// not activate, go to activate page
				    			Intent intent = new Intent(InitActivity.this,ActivationActivity.class);
				    			startActivity(intent);
				    		}else{
				    			// have activated, auto login
				    			text.setText("please wait,logining...");
				    			
				    			doLogin(new Callback(){

									@Override
									public void callback() {
										text.setText("Login success.");
										
										Intent intent = new Intent(InitActivity.this,MainActivity.class);
						    			startActivity(intent);
						    			InitActivity.this.finish();
									}
				    				
				    			});
				    		}
				    		
				    		
				    	}else{
				    		//dismissProgressDialog();
				    		AlertDialog.Builder dialog = UIUtil.getErrorDialog(InitActivity.this,view.getInfo());
				    		
							dialog.show();
				    	}
				    	
					}

				}, 
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e(TAG, error.getMessage(), error);
						AlertDialog.Builder dialog = UIUtil.getServerErrorDialog(
							InitActivity.this,
							new DialogInterface.OnClickListener(){

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
									AsyncTask task = new AsyncTask() {
										@Override
										protected Object doInBackground(
												Object... params) {
											 Log.i(TAG, "Retry call is activate");
											 isActivate();
											 return null;
										}
								    }; 
								    task.execute();
								}
								
							});
			    		
						dialog.show();
				}
			});
			
		}
	}
	
	private void doLogin(final Callback successCallback) {
		RequestHelper helper = getRequestHelper();
		String url = Config.BASE_URL_MVC + RequestURLConstants.URL_DO_LOGIN;

		String imei = DeviceHelper.getIMEI(this);
		Map<String,String> param = new HashMap<String,String>();
		param.put("imei", imei);
		helper.doPost(
			url,
			param,
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					ViewDTO<ChildViewDTO> view = JSONUtil.getDoLoginView(response);
			    	
			    	if( view.getMsg().equals(ViewDTO.MSG_SUCCESS)){
			    		saveChildIdToSharedPreferences(view.getData().getId());
			    		
			    		successCallback.callback();
			    		
			    	}else{
			    		//dismissProgressDialog();
			    		AlertDialog.Builder dialog = UIUtil.getErrorDialog(InitActivity.this,view.getInfo());
			    		
						dialog.show();
			    	}
			    	
				}

			}, 
			new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					Log.e(TAG, error.getMessage(), error);
					AlertDialog.Builder dialog = UIUtil.getServerErrorDialog(InitActivity.this);
		    		
					dialog.show();
			}
		});
	}
	
	
	private void saveChildIdToSharedPreferences(Integer childId) {
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putInt(getString(R.string.sp_key_login_child_id) ,childId);
		editor.commit();					
	}
}
