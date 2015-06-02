package eden.sun.childrenguard.activity;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import eden.sun.childrenguard.R;
import eden.sun.childrenguard.errhandler.DefaultVolleyErrorHandler;
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
		    config.IS_TRIAL = properties.get("isTrial")==null? false: Boolean.parseBoolean(properties.get("isTrial").toString());
		    config.PAY_VERSION_MARKET_URL = properties.get("payVersionMarketUrl").toString();
		    
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
			// check version , auto login
			this.isInTrail(loginAccount,loginPassword);
		}else{
			//go to login activity
			toLoginActivity();
		}
	}
    
    private void toLoginActivity() {
    	Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
		startActivity(intent);
	}
    
    public void isInTrail(final String account ,final String password){
    	
    	if( Config.getInstance().IS_TRIAL == true ){
			// trial version 
			String url = String.format(
					Config.getInstance().BASE_URL_MVC + RequestURLConstants.URL_IS_IN_TRIAL + "?mobile=%1$s",  
					account); 
			
			getRequestHelper().doGet(
				url,
				this.getClass(),
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						dismissProgressDialog();
						ViewDTO<Boolean> view = JSONUtil.getIsInTrialView(response);
				    	
				    	if( view.getMsg().equals(ViewDTO.MSG_SUCCESS)){
				    		if( view.getData().booleanValue() == true ){
				    			// 在试用期内，可以继续登录
				    			doLogin(account,password);
				    		}else{
				    			// TODO: 超过试用期，提示下载付费版本
				    			
				    			AlertDialog.Builder dialog = UIUtil.getAlertDialogWithTwoBtn(
				    					SplashActivity.this,
				    					"Trial",
				    					"This is Trial version.Please click Ok to download pay version.",
				    					"Ok",
				    					"Exit",
				    					new DialogInterface.OnClickListener() {
				    			            @Override
				    			            public void onClick(DialogInterface dialog, int which) {
				    			            	dialog.dismiss();
				    			            	
				    			            	//go to google market to download 
				    			            	Intent intent = new Intent();
				    			            	intent.setAction(Intent.ACTION_VIEW);
				    			            	intent.setData(Uri.parse(Config.getInstance().PAY_VERSION_MARKET_URL));
				    			            	startActivity(intent);
				    			            }
				    			        },
				    			        new DialogInterface.OnClickListener() {
				    			            @Override
				    			            public void onClick(DialogInterface dialog, int which) {
				    			            	dialog.dismiss();
				    			            	System.exit(0);
				    			            }
				    			        }
				    				);
				    			
								dialog.show();
				    			
				    		}
				    	}else{
				    		AlertDialog.Builder dialog = UIUtil.getErrorDialog(SplashActivity.this,view.getInfo());
				    		
							dialog.show();
				    	}
				    	
					}
				}, 
				new DefaultVolleyErrorHandler(SplashActivity.this));
    	}else{
    		doLogin(account,password);
    	}
			
	}
    
    private void doLogin(String account, String password) {
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
