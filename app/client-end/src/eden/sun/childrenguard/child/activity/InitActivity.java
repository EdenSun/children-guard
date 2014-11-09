package eden.sun.childrenguard.child.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import eden.sun.childrenguard.child.R;
import eden.sun.childrenguard.child.util.Callback;
import eden.sun.childrenguard.child.util.Config;
import eden.sun.childrenguard.child.util.DeviceHelper;
import eden.sun.childrenguard.child.util.JSONUtil;
import eden.sun.childrenguard.child.util.RequestHelper;
import eden.sun.childrenguard.child.util.RequestURLConstants;
import eden.sun.childrenguard.child.util.UIUtil;
import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;

public class InitActivity extends CommonBindServiceActivity {
	private static final String TAG = "InitActivity";
	private TextView text;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_init);
		initComponent();
		
		initService();
	}

	private void initComponent() {
		text = (TextView)findViewById(R.id.text);
	}

	private void initService() {
		startMainService();
		bindMainService();		
	}

	@Override
	protected void onStop() {
		super.onStop();
		unbindMainService();
	}

	@Override
	protected void onResume() {
		super.onResume();
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
						AlertDialog.Builder dialog = UIUtil.getServerErrorDialog(InitActivity.this);
			    		
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
}
