package eden.sun.childrenguard.activity;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import eden.sun.childrenguard.R;
import eden.sun.childrenguard.server.dto.LoginViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.util.Config;
import eden.sun.childrenguard.util.JSONUtil;
import eden.sun.childrenguard.util.RequestURLConstants;
import eden.sun.childrenguard.util.ShareDataKey;
import eden.sun.childrenguard.util.UIUtil;


public class SplashActivity extends CommonActivity {
	private final String TAG = "SplashActivity";
	private Timer timer;
	private boolean isLoginFailDialogShow = false;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_splash);
        
        readConfig();
        //startTimer();
    }

    
	private void readConfig() {
		try {
			AssetManager assetManager = this.getAssets();
		    InputStream inputStream = assetManager.open("config.properties");
		    Properties properties = new Properties();
		    properties.load(inputStream);
		    Config config = Config.getInstance();
		    
		    Log.i(TAG,"Loading config...");
		    config.BASE_URL = properties.get("baseUrl").toString();
		    config.BASE_URL_MVC = config.BASE_URL + properties.get("baseUrlMvcContext");
		    config.TERMS_OF_SERVICE_PATH = properties.get("termsOfServicePath").toString();
		    config.PRIVACY_POLICY_PATH = properties.get("privacyPolicyPath").toString();
		    config.IS_TRIAL = properties.get("isTrial")==null? false: Boolean.getBoolean(properties.get("isTrial").toString());
		    
		    Log.i(TAG,"Load config finish. ----> " + config.toString());
		} catch (IOException e) {
		    Log.e(TAG,"Failed to open config property file");
		    return ;
		}
	}


	private void processLogin() {
		String loginAccount = this.getStringShareData(ShareDataKey.LOGIN_ACCOUNT);
		String loginPassword = this.getStringShareData(ShareDataKey.LOGIN_PASSWORD);
		
		if( loginAccount != null && loginPassword != null ){
			// auto login
			this.doLogin(loginAccount,loginPassword);
		}else{
			//go to login activity
			toLoginActivity();
		}
	}
    
    private void toLoginActivity() {
    	Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
		startActivity(intent);
	}
    
    public void doLogin(final String account ,final String password){
		// do login
		String url = Config.getInstance().BASE_URL_MVC + RequestURLConstants.URL_LOGIN ;  
		Map<String,String> param = new HashMap<String,String>();
		param.put("mobile", account);
		param.put("password", password);
		
		getRequestHelper().doPost(
			url,
			param,
			this.getClass(),
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					Log.d(TAG, "response");
					dismissProgressDialog();
					ViewDTO<LoginViewDTO> view = JSONUtil.getLoginView(response);
					
					if( view.getMsg().equals(ViewDTO.MSG_SUCCESS) ){
						putStringShareData(ShareDataKey.PARENT_ACCESS_TOKEN,view.getData().getAccessToken());
						
						Intent it = new Intent(SplashActivity.this, ChildrenListActivity.class);
						startActivity(it);
						// finish splash activity
						finish();
					}else{
						//toLoginActivity();
						onLoginFail();
					}
					
				}

			}, 
			new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					//toLoginActivity();
					onLoginFail();
				}
				
			}
		);
	}
    
    private void onLoginFail() {
    	isLoginFailDialogShow = true;
    	
    	String title = "Login";
    	String msg = "Login failure,please make sure your network is available or try again later.";
    	String leftBtnText = "Try Again";
    	String rightBtnText = "Exit";
		UIUtil.getAlertDialogWithTwoBtn(
				SplashActivity.this, 
				title, 
				msg, 
				leftBtnText, 
				rightBtnText, 
				new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						processLogin();
					}
					
				}, 
				new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
						System.exit(0);
					}
					
				}).show();
	}
    
    private void startTimer() {
    	if( timer == null ){
    		timer = new Timer();
    	}
        timer.schedule(new PendingTask(), 3*1000);
    }
    
    private void cancelTimer() {
    	if( timer != null ){
    		timer.cancel();
    		timer = null;
    	}
    }
    
	@Override
	public void onResume() {
		super.onResume();
		//JPushInterface.onResume(this);
		
		if( !isLoginFailDialogShow ){
			startTimer();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		//JPushInterface.onPause(this);
		
		cancelTimer();
	}
	
	
	class PendingTask extends TimerTask {

		@Override
		public void run() {
			processLogin();
		}
		
	}
}
