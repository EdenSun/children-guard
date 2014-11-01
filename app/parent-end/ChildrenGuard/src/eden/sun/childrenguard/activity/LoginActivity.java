package eden.sun.childrenguard.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.igexin.sdk.PushManager;

import eden.sun.childrenguard.R;
import eden.sun.childrenguard.server.dto.IsFirstLoginViewDTO;
import eden.sun.childrenguard.server.dto.LoginViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.util.Config;
import eden.sun.childrenguard.util.JSONUtil;
import eden.sun.childrenguard.util.RequestHelper;
import eden.sun.childrenguard.util.RequestURLConstants;
import eden.sun.childrenguard.util.ShareDataKey;
import eden.sun.childrenguard.util.StringUtil;
import eden.sun.childrenguard.util.UIUtil;


public class LoginActivity extends CommonActivity {
	private final String TAG = "LoginActivity";
	private Button loginBtn;
	private TextView registerLinkBtn;
	private TextView forgotYourPasswordLinkBtn;
	private EditText emailEditText;
	private EditText passwordEditText;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushManager.getInstance().initialize(this.getApplicationContext());
        
        setContentView(R.layout.activity_login);

        emailEditText = (EditText)findViewById(R.id.emailEditText);
        passwordEditText = (EditText)findViewById(R.id.passwordEditText);

        loginBtn = (Button)findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new OnClickListener(){
        	
			@Override
			public void onClick(View arg0) {
				boolean valid = doValidation();
				
				if( valid ){
					/*AsyncTask<Map<String, Object>,Integer,String> task = new LoginTask(LoginActivity.this);
					
					
					Map<String, Object> data = getLoginParam();
					
					task.execute(data);*/
					
					String title = "Login";
					String msg = "Please wait...";
					showProgressDialog(title,msg);
					
					RequestHelper helper = getRequestHelper();
					final String finalEmail = emailEditText.getText().toString();
					final String finalPassword = passwordEditText.getText().toString();
					String url = String.format(
							Config.BASE_URL_MVC + RequestURLConstants.URL_IS_FIRST_LOGIN + "?email=%1$s&password=%2$s",  
							finalEmail,  
							finalPassword);  
	  
					helper.doGet(
						url,
						new Response.Listener<String>() {
							@Override
							public void onResponse(String response) {
								ViewDTO<IsFirstLoginViewDTO> view = JSONUtil.getIsFirstLoginView(response);
						    	
						    	if( view.getMsg().equals(ViewDTO.MSG_SUCCESS)){
						    		if( view.getData().isFirstLogin() ){
						    			dismissProgressDialog();
						    			
						    			AlertDialog.Builder dialog = UIUtil.getLegalInfoDialog(LoginActivity.this,view.getData().getLegalInfo());
										
										dialog.show();
						    		}else{
						    			LoginActivity.this.doLogin();
						    		}
						    	}else{
						    		dismissProgressDialog();
						    		AlertDialog.Builder dialog = UIUtil.getErrorDialog(LoginActivity.this,view.getInfo());
						    		
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
    	
        /*registerBtn = (Button)findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent it = new Intent(LoginActivity.this, RegisterActivity.class);
				startActivity(it);   
			}
        	
        });
        
        forgetPasswordBtn = (Button)findViewById(R.id.forgetPasswordBtn);
        forgetPasswordBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent it = new Intent(LoginActivity.this, PasswordResetActivity.class);
				startActivity(it);   
			}
        	
        });*/
    }


	private boolean doValidation() {
    	String email = UIUtil.getEditTextValue(emailEditText);
		String password = UIUtil.getEditTextValue(passwordEditText);
				
		if( StringUtil.isBlank(email) ){
			String title = "Login";
			String msg = "Email can not be blank.";
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
		
		String email = UIUtil.getEditTextValue(emailEditText);
		String password = UIUtil.getEditTextValue(passwordEditText);
		
		data.put("email", email);
		data.put("password", password);
		return data;
	}
	
	public void doLogin(){
		// do login
    	//runtime.publish(getLoginParam(), CometdConfig.LOGIN_CHANNEL,new LoginListener(LoginActivity.this));

		String email = UIUtil.getEditTextValue(emailEditText);
		String password = UIUtil.getEditTextValue(passwordEditText);
		
		RequestHelper helper = getRequestHelper();
		
		String url = String.format(
				Config.BASE_URL_MVC + RequestURLConstants.URL_LOGIN + "?email=%1$s&password=%2$s",  
				email,  
				password);  

		helper.doGet(
			url,
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					Log.d(TAG, "response");
					dismissProgressDialog();
					ViewDTO<LoginViewDTO> view = JSONUtil.getLoginView(response);
					
					if( view.getMsg().equals(ViewDTO.MSG_SUCCESS) ){
						LoginActivity.this.putStringShareData(ShareDataKey.PARENT_ACCESS_TOKEN,view.getData().getAccessToken());
						
						Toast toast = UIUtil.getToast(LoginActivity.this,"Login Success!");
						toast.show();
						
						Intent it = new Intent(LoginActivity.this, ChildrenListActivity.class);
						LoginActivity.this.startActivity(it);
						
						// finish login activity
						LoginActivity.this.finish();
					}else{
						AlertDialog.Builder dialog = UIUtil.getErrorDialog(LoginActivity.this,view.getInfo());
			    		
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
	
}
