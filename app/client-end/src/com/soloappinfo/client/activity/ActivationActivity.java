package com.soloappinfo.client.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.soloappinfo.client.R;
import com.soloappinfo.client.task.UploadAppRunnable;
import com.soloappinfo.client.util.Config;
import com.soloappinfo.client.util.DeviceHelper;
import com.soloappinfo.client.util.HandlerConstants;
import com.soloappinfo.client.util.JSONUtil;
import com.soloappinfo.client.util.RequestHelper;
import com.soloappinfo.client.util.RequestURLConstants;
import com.soloappinfo.client.util.UIUtil;

import eden.sun.childrenguard.server.dto.ViewDTO;

public class ActivationActivity extends CommonActivity {
	private Button activateBtn;
	private EditText parentMobileEditText;
	private EditText childMobileEditText;
	private Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activation);

		initComponent();
		
		activateBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				doActivate();
				
			}

		});
	}

	private void initComponent() {
		activateBtn = (Button)this.findViewById(R.id.activateBtn);
		parentMobileEditText = (EditText)this.findViewById(R.id.parentMobileEditText);
		childMobileEditText = (EditText)this.findViewById(R.id.childMobileEditText);
	}

	private void doActivate() {
		String title = "Activate Account";
		String msg = "Please wait...";
		showProgressDialog(title,msg);
		
		RequestHelper helper = getRequestHelper();
		String url = Config.BASE_URL_MVC + RequestURLConstants.URL_ACTIVATE_ACCOUNT;

		String parentMobile = UIUtil.getEditTextValue(parentMobileEditText);
		String childMobile = UIUtil.getEditTextValue(childMobileEditText);
		String imei = DeviceHelper.getIMEI(this);
		Map<String,String> params = new HashMap<String,String>();
		params.put("parentMobile", parentMobile);
		params.put("childMobile", childMobile);
		params.put("imei", imei);
		
		helper.doPost(
			url,
			params,
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					ViewDTO<Boolean> view = JSONUtil.getActivateAccountView(response);
			    	
			    	if( view.getMsg().equals(ViewDTO.MSG_SUCCESS)){
			    		// activate success
			    		
			    		// upload applications info
			    		setProgressDialogText(null, "Uploading data...");  
			    		uploadApplicationInfo();
			    		
			    	}else{
			    		dismissProgressDialog();
			    		AlertDialog.Builder dialog = UIUtil.getErrorDialog(ActivationActivity.this,view.getInfo());
			    		
						dialog.show();
			    	}
			    	
				}
			}, 
			new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					Log.e("TAG", error.getMessage(), error);
			}
		});		
	}
	
	private void uploadApplicationInfo() {
		handler = new Handler(){

			@Override
			public void handleMessage(Message message) {
				if ( message.what == HandlerConstants.UPLOAD_FINISH ) {
					// upload application finish
                    dismissProgressDialog();
                    
                    
                    String title = "Activate Account";
    				String msg = "Activate success. Press OK to login.";
    				String btnText = "OK";
    				
    				AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
    					ActivationActivity.this,
    					title,
    					msg,
    					btnText,
    					new DialogInterface.OnClickListener() {
    			            @Override
    			            public void onClick(DialogInterface dialog, int which) {
    			            	dialog.dismiss();
    			            	ActivationActivity.this.finish();
    			            }
    			        }
    				);
    				
    				dialog.show();
                }  
				
				super.handleMessage(message);
			}
			
		};

		handler.post(new UploadAppRunnable(ActivationActivity.this,handler));
		
		/*//upload all applications info to server every 24 hours
		int interval = 1000 * 24 * 3600;	
		Timer timer = new Timer();
		
		timer.scheduleAtFixedRate(new TimerTask() {

		    @Override
		    public void run() {
		        // Do upload task
		    	Runnable uploadAppRunnable = new UploadAppRunnable(ActivationActivity.this,handler);
				handler.post(uploadAppRunnable);
		    }

		}, 0, interval);*/
	}
	
	/*private void doInitData(String childMobile) {
		String title = "Initializing data";
		String msg = "Please wait...";
		showProgressDialog(title,msg);
		
		RequestHelper helper = getRequestHelper();
		String url = String.format(
				Config.BASE_URL_MVC + RequestURLConstants.URL_CHILD_LOGIN + "?childMobile=%1$s",  
				childMobile);  

		helper.doGet(
			url,
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					ViewDTO<ChildViewDTO> view = JSONUtil.getChildLoginView(response);
			    	
			    	if( view.getMsg().equals(ViewDTO.MSG_SUCCESS)){
			    		String accessToken = view.getData().getAccessToken();
			    		
			    		putStringShareData(ShareDataKey.CHILD_MOBILE,accessToken);
			    		
			    	}else{
			    		dismissProgressDialog();
			    		AlertDialog.Builder dialog = UIUtil.getErrorDialog(ActivationActivity.this,view.getInfo());
			    		
						dialog.show();
			    	}
			    	
				}
			}, 
			new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					Log.e("TAG", error.getMessage(), error);
			}
		});
	}*/
	

}
