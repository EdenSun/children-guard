package eden.sun.childrenguard.activity;

import java.util.HashMap;
import java.util.Map;

import org.cometd.bayeux.Message;
import org.cometd.bayeux.client.ClientSessionChannel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import eden.sun.childrenguard.R;
import eden.sun.childrenguard.comet.LoginListener;
import eden.sun.childrenguard.server.dto.LoginViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.util.CometdConfig;
import eden.sun.childrenguard.util.JSONUtil;
import eden.sun.childrenguard.util.Runtime;
import eden.sun.childrenguard.util.StringUtil;
import eden.sun.childrenguard.util.UIUtil;


public class LoginActivity extends CommonActivity {
	private Button loginBtn;
	private Button registerBtn ;
	private Button forgetPasswordBtn;
	private EditText emailEditText;
	private EditText passwordEditText;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        emailEditText = (EditText)findViewById(R.id.emailEditText);
        passwordEditText = (EditText)findViewById(R.id.passwordEditText);

        loginBtn = (Button)findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new OnClickListener(){
        	
			@Override
			public void onClick(View arg0) {
				boolean valid = doValidation();
				
				if( valid ){
					AsyncTask<Map<String, Object>,Integer,String> task = new LoginTask(LoginActivity.this);
					
					Map<String, Object> data = new HashMap<String,Object>();
					
					String email = UIUtil.getEditTextValue(emailEditText);
					String password = UIUtil.getEditTextValue(passwordEditText);
					
					data.put("email", email);
					data.put("password", password);
					
					task.execute(data);
				}
				
			}

        });
        
        registerBtn = (Button)findViewById(R.id.registerBtn);
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
        	
        });
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


	@Override
	protected void onStart() {
		super.onStart();
		
		runtime.subscribe(CometdConfig.LOGIN_CHANNEL,new LoginListener(LoginActivity.this));
	}
    
	
	class LoginTask extends AsyncTask<Map<String, Object>,Integer,String>{
		private Activity context;
		
		public LoginTask(Activity context) {
			super();
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			String title = "Login";
			String msg = "Please wait...";
			showProgressDialog(title,msg);
		}

		@Override
		protected String doInBackground(Map<String, Object>... params) {
			Map<String, Object> data = params[0];
			
			String msg = runtime.publish(data, CometdConfig.LOGIN_CHANNEL,new LoginListener(LoginActivity.this));
			
			return msg;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			
			if( result != null && !result.equals(Runtime.PUBLISH_SUCCESS)){
				// login fail,show message
				String title = "Network Error";
				String msg = result;
				String btnText = "OK";
				
				AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
					context,
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
				
				dismissProgressDialog();
			}
		}
		
	}
	
	
}
