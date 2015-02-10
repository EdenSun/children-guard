package eden.sun.childrenguard.activity;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import eden.sun.childrenguard.R;
import eden.sun.childrenguard.runnable.JPushRegistionIdUploadRunnable;
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
        
        initJPush();
        
        setContentView(R.layout.activity_splash);
        
        //startTimer();
    }

    
    private void initJPush() {
    	JPushInterface.setDebugMode(true);
    	JPushInterface.init(this);
		
    	JPushRegistionIdUploadRunnable runnable = new JPushRegistionIdUploadRunnable(this);
    	new Thread(runnable).start();
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
		String url = Config.BASE_URL_MVC + RequestURLConstants.URL_LOGIN ;  
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
