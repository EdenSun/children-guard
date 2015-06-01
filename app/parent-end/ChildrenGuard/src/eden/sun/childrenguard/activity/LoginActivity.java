package eden.sun.childrenguard.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

import eden.sun.childrenguard.R;
import eden.sun.childrenguard.errhandler.DefaultVolleyErrorHandler;
import eden.sun.childrenguard.server.dto.IsFirstLoginViewDTO;
import eden.sun.childrenguard.server.dto.LoginViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.util.Callback;
import eden.sun.childrenguard.util.Config;
import eden.sun.childrenguard.util.JSONUtil;
import eden.sun.childrenguard.util.RequestURLConstants;
import eden.sun.childrenguard.util.ShareDataKey;
import eden.sun.childrenguard.util.StringUtil;
import eden.sun.childrenguard.util.UIUtil;


public class LoginActivity extends CommonActivity {
	private final String TAG = "LoginActivity";
	private Button loginBtn;
	private TextView registerLinkBtn;
	private TextView forgotYourPasswordLinkBtn;
	private EditText mobileEditText;
	private EditText passwordEditText;
	
	private FrameLayout layout;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_login);

        layout = (FrameLayout)findViewById(android.R.id.content);
        layout.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				hideKeyboard(v);
		        return false;
			}
        	
        });
        
        mobileEditText = (EditText)findViewById(R.id.mobileEditText);
        passwordEditText = (EditText)findViewById(R.id.passwordEditText);

        loginBtn = (Button)findViewById(R.id.loginBtn);
        /*loginBtn.setOnClickListener(new OnClickListener(){
        	
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(LoginActivity.this,PresetLockActivity.class);
				
				startActivity(intent);
			}
		});*/

        loginBtn.setOnClickListener(new OnClickListener(){
        	
			@Override
			public void onClick(View arg0) {
				boolean valid = doValidation();
				
				if( valid ){
					String title = "Login";
					String msg = "Please wait...";
					showProgressDialog(title,msg);
					
					final String finalMobile = mobileEditText.getText().toString();
					final String finalPassword = passwordEditText.getText().toString();

					if( Config.getInstance().IS_TRIAL == true ){
						// trial version 
						String url = String.format(
								Config.getInstance().BASE_URL_MVC + RequestURLConstants.URL_IS_IN_TRIAL + "?mobile=%1$s",  
								finalMobile); 
						
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
							    			isFirstLogin(finalMobile,finalPassword);
							    		}else{
							    			// TODO: 超过试用期，提示下载付费版本
							    			
							    			AlertDialog.Builder dialog = UIUtil.getAlertDialogWithTwoBtn(
							    					LoginActivity.this,
							    					"Trial",
							    					"试用期结婚素，请下载正式版，下载请点击OK，否则点击Cancel",
							    					"Ok",
							    					"Cancel",
							    					new DialogInterface.OnClickListener() {
							    			            @Override
							    			            public void onClick(DialogInterface dialog, int which) {
							    			            	dialog.dismiss();
							    			            	
							    			            	//TODO: go to google market to download 
							    			            	
							    			            	
							    			            }
							    			        },
							    			        new DialogInterface.OnClickListener() {
							    			            @Override
							    			            public void onClick(DialogInterface dialog, int which) {
							    			            	dialog.dismiss();
							    			            }
							    			        }
							    				);
							    			
											dialog.show();
							    			
							    		}
							    	}else{
							    		AlertDialog.Builder dialog = UIUtil.getErrorDialog(LoginActivity.this,view.getInfo());
							    		
										dialog.show();
							    	}
							    	
								}
							}, 
							new DefaultVolleyErrorHandler(LoginActivity.this));		
					}else{
						isFirstLogin(finalMobile,finalPassword);
					}
					
				}
				
			}

			

        });
        
        
        registerLinkBtn = (TextView)findViewById(R.id.registerLinkBtn);
        registerLinkBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent it = new Intent(LoginActivity.this, RegisterActivity.class);
				startActivity(it);
			}
        	
        });
        forgotYourPasswordLinkBtn = (TextView)findViewById(R.id.forgotYourPasswordLinkBtn);
        forgotYourPasswordLinkBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent it = new Intent(LoginActivity.this, PasswordResetActivity.class);
				startActivity(it); 
			}
        	
        });
    	
        //autoLogin();
    }

    
    private void isFirstLogin(String finalMobile, String finalPassword) {
		String url = String.format(
				Config.getInstance().BASE_URL_MVC + RequestURLConstants.URL_IS_FIRST_LOGIN + "?mobile=%1$s&password=%2$s",  
				finalMobile,  
				finalPassword);  

		getRequestHelper().doGet(
			url,
			this.getClass(),
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					dismissProgressDialog();
					ViewDTO<IsFirstLoginViewDTO> view = JSONUtil.getIsFirstLoginView(response);
			    	
			    	if( view.getMsg().equals(ViewDTO.MSG_SUCCESS)){
			    		if( view.getData().isFirstLogin() ){
			    			
			    			AlertDialog.Builder dialog = UIUtil.getLegalInfoDialog(
			    					LoginActivity.this,
			    					view.getData().getLegalInfo(),
			    					new Callback(){

										@Override
										public void execute(
												CallbackResult result) {
											String mobile = UIUtil.getEditTextValue(mobileEditText);
							    			String password = UIUtil.getEditTextValue(passwordEditText);
							    			
											doLogin(mobile,password);
										}
			    						
			    					});
							
							dialog.show();
			    		}else{
			    			String mobile = UIUtil.getEditTextValue(mobileEditText);
			    			String password = UIUtil.getEditTextValue(passwordEditText);
			    			
			    			LoginActivity.this.doLogin(mobile,password);
			    		}
			    	}else{
			    		AlertDialog.Builder dialog = UIUtil.getErrorDialog(LoginActivity.this,view.getInfo());
			    		
						dialog.show();
			    	}
			    	
				}
			}, 
			new DefaultVolleyErrorHandler(LoginActivity.this));				
	}
    

	private void autoLogin() {
		String loginAccount = this.getStringShareData(ShareDataKey.LOGIN_ACCOUNT);
		String loginPassword = this.getStringShareData(ShareDataKey.LOGIN_PASSWORD);
		
		if( loginAccount != null && loginPassword != null ){
			//do login
			this.doLogin(loginAccount,loginPassword);
		}
	}
	
	private void saveAutoLoginData(String account, String password) {
		this.putStringShareData(ShareDataKey.LOGIN_ACCOUNT, account);
		this.putStringShareData(ShareDataKey.LOGIN_PASSWORD, password);
	}

	private boolean doValidation() {
    	String mobile = UIUtil.getEditTextValue(mobileEditText);
		String password = UIUtil.getEditTextValue(passwordEditText);
				
		if( StringUtil.isBlank(mobile) ){
			String title = "Login";
			String msg = "Mobile can not be blank.";
			String btnText = "OK";
			
			AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
				LoginActivity.this,
				title,
				msg,
				btnText,
				new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int which) {
		            	dialog.dismiss();
		            }
		        }
			);
			
			dialog.show();
			return false;
		}
		
		if( mobile.length() != 10 ){
			String title = "Login";
			String msg = "10-digit phone number is required.";
			String btnText = "OK";
			
			AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
				LoginActivity.this,
				title,
				msg,
				btnText,
				new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int which) {
		            	dialog.dismiss();
		            }
		        }
			);
			
			dialog.show();
			return false;
		}
		
		if( StringUtil.isBlank(password) ){
			String title = "Login";
			String msg = "Password can not be blank.";
			String btnText = "OK";
			
			AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
				LoginActivity.this,
				title,
				msg,
				btnText,
				new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int which) {
		            	dialog.dismiss();
		            }
		        }
			);
			
			dialog.show();
			return false;
		}
		
		if( password.length() != 4 ){
			String title = "Login";
			String msg = "4-digit number password is required.";
			String btnText = "OK";
			
			AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
				LoginActivity.this,
				title,
				msg,
				btnText,
				new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int which) {
		            	dialog.dismiss();
		            }
		        }
			);
			
			dialog.show();
			return false;
		}
		
		return true;
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


	private Map<String, Object> getLoginParam() {
		Map<String, Object> data = new HashMap<String,Object>();
		
		String mobile = UIUtil.getEditTextValue(mobileEditText);
		String password = UIUtil.getEditTextValue(passwordEditText);
		
		data.put("mobile", mobile);
		data.put("password", password);
		return data;
	}
	
	public void doLogin(final String account ,final String password){
		// do login
    	//runtime.publish(getLoginParam(), CometdConfig.LOGIN_CHANNEL,new LoginListener(LoginActivity.this));
		String url = String.format(
				Config.getInstance().BASE_URL_MVC + RequestURLConstants.URL_LOGIN + "?mobile=%1$s&password=%2$s",  
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
					saveAutoLoginData(account,password);
					ViewDTO<LoginViewDTO> view = JSONUtil.getLoginView(response);
					
					if( view.getMsg().equals(ViewDTO.MSG_SUCCESS) ){
						Intent it = new Intent(LoginActivity.this, ChildrenListActivity.class);
						LoginActivity.this.startActivity(it);
						
						// finish login activity
						LoginActivity.this.finish();

						LoginActivity.this.putStringShareData(ShareDataKey.PARENT_ACCESS_TOKEN,view.getData().getAccessToken());
						
						Toast toast = UIUtil.getToast(LoginActivity.this,"Login Success!");
						toast.show();
					}else{
						AlertDialog.Builder dialog = UIUtil.getErrorDialog(LoginActivity.this,view.getInfo());
			    		
						dialog.show();
					}
					
				}

			}, 
			new DefaultVolleyErrorHandler(LoginActivity.this)
		);
	}

	/**
	* Hides virtual keyboard
	*/
	protected void hideKeyboard(View view)
	{
	    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	    in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}
}
