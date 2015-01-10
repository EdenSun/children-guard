package eden.sun.childrenguard.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

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
	private TextView textView;
	private Timer timer;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JPushInterface.setDebugMode(false);
        JPushInterface.init(this);
        
        setContentView(R.layout.activity_splash);
        
        textView = (TextView)findViewById(R.id.textView);
        
        
        startTimer();
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
		String url = String.format(
				Config.BASE_URL_MVC + RequestURLConstants.URL_LOGIN + "?email=%1$s&password=%2$s",  
				account,  
				password);  

		getRequestHelper().doGet(
			url,
			this.getClass(),
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					Log.d(TAG, "response");
					dismissProgressDialog();
					ViewDTO<LoginViewDTO> view = JSONUtil.getLoginView(response);
					
					if( view.getMsg().equals(ViewDTO.MSG_SUCCESS) ){
						Intent it = new Intent(SplashActivity.this, ChildrenListActivity.class);
						startActivity(it);
						
						// finish splash activity
						finish();

						putStringShareData(ShareDataKey.PARENT_ACCESS_TOKEN,view.getData().getAccessToken());
						
						Toast toast = UIUtil.getToast(SplashActivity.this,"Login Success!");
						toast.show();
					}else{
						toLoginActivity();
					}
					
				}

			}, 
			new Response.ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
					toLoginActivity();
				}
				
			}
		);
	}
    
    private void startTimer() {
    	if( timer == null ){
    		timer = new Timer();
    	}
        timer.schedule(new PendingTask(), 5*1000);
    }
    
    private void cancelTimer() {
    	if( timer != null ){
    		timer.cancel();
    		timer = null;
    	}
    }
    
	@Override
	protected void onResume() {
		super.onResume();
		JPushInterface.onResume(this);
		
		startTimer();
	}




	@Override
	protected void onPause() {
		super.onPause();
		JPushInterface.onPause(this);
		
		cancelTimer();
	}
	
	
	class PendingTask extends TimerTask {

		@Override
		public void run() {
			processLogin();
		}
		
	}
}
