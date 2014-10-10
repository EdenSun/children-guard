package eden.sun.childrenguard.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import eden.sun.childrenguard.R;
import eden.sun.childrenguard.comet.RegisterListener;
import eden.sun.childrenguard.util.CometdConfig;
import eden.sun.childrenguard.util.Runtime;
import eden.sun.childrenguard.util.StringUtil;
import eden.sun.childrenguard.util.UIUtil;

public class RegisterActivity extends CommonActivity {
	private static final String TAG = "RegisterActivity";
	
	/* ui components */
	private Button registerBtn;
	private Button backBtn;
	private EditText firstNameEditText;
	private EditText lastNameEditText;
	private EditText emailEditText;
	private EditText passwordEditText;
	private EditText confirmPasswordEditText;
	
	/* END - ui components */
	
	private Runtime runtime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		firstNameEditText = (EditText)findViewById(R.id.firstNameEditText);
		lastNameEditText = (EditText)findViewById(R.id.lastNameEditText);
		emailEditText = (EditText)findViewById(R.id.emailEditText);
		passwordEditText = (EditText)findViewById(R.id.passwordEditText);
		confirmPasswordEditText = (EditText)findViewById(R.id.confirmPasswordEditText);
		
		registerBtn = (Button)findViewById(R.id.registerBtn);
		registerBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				boolean valid = doValidation();
				
				if( valid ){
					String title = "Register";
					String msg = "Please wait...";
					showProgressDialog(title,msg);

					Map<String, Object> data = new HashMap<String,Object>();
					
					String firstName = firstNameEditText.getText().toString().trim();
					String lastName = lastNameEditText.getText().toString().trim();
					String email = emailEditText.getText().toString().trim();
					String password = passwordEditText.getText().toString().trim();
					
					data.put("firstName", firstName);
					data.put("lastName", lastName);
					data.put("email", email);
					data.put("password", password);
					
					runtime.publish(data, CometdConfig.REGISTER_CHANNEL, new RegisterListener(RegisterActivity.this));
					
						
						/*Timer timer;
						TimerTask task = new TimerTask(){
							public void run(){    
								progress.dismiss();
								
								RegisterActivity.this.runOnUiThread(new Runnable(){

									@Override
									public void run() {
										new AlertDialog.Builder(RegisterActivity.this)
								        .setIcon(android.R.drawable.ic_dialog_alert)
								        .setTitle("Register")
								        .setMessage("Register Success! Click OK to Login.")
								        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
								            @Override
								            public void onClick(DialogInterface dialog, int which) {
								            	RegisterActivity.this.finish();
								            }
			
								        })
								        .show();
									}
									
								});
							}    
						};
						timer = new Timer();  
						timer.schedule(task, 3000);*/
				}
				
			}

        });
		
		backBtn = (Button)findViewById(R.id.backBtn);
		backBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				RegisterActivity.this.finish();
			}
        	
        });
	}

	private boolean doValidation() {
		String firstName = firstNameEditText.getText().toString().trim();
		String lastName = lastNameEditText.getText().toString().trim();
		String email = emailEditText.getText().toString().trim();
		String password = passwordEditText.getText().toString().trim();
		String confirmPassword = confirmPasswordEditText.getText().toString().trim(); 
				
		if( StringUtil.isBlank(firstName) ){
			String title = "Register";
			String msg = "First name can not be blank.";
			String btnText = "OK";
			
			AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
				RegisterActivity.this,
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

		if( StringUtil.isBlank(lastName) ){
			String title = "Register";
			String msg = "Last name can not be blank.";
			String btnText = "OK";
			
			AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
				RegisterActivity.this,
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
		
		if( StringUtil.isBlank(email) ){
			String title = "Register";
			String msg = "Email can not be blank.";
			String btnText = "OK";
			
			AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
				RegisterActivity.this,
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
			String title = "Register";
			String msg = "Password can not be blank.";
			String btnText = "OK";
			
			AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
				RegisterActivity.this,
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
		
		if( StringUtil.isBlank(confirmPassword) ){
			String title = "Register";
			String msg = "Confirm password can not be blank.";
			String btnText = "OK";
			
			AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
				RegisterActivity.this,
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
		
		if( !password.equals(confirmPassword) ){
			String title = "Register";
			String msg = "Password and confirm password is not the same.";
			String btnText = "OK";
			
			AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
				RegisterActivity.this,
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
		
		Log.i(TAG, "Validation is pass.");
		return true;
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
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
		runtime = Runtime.getInstance(RegisterActivity.this);
	}
}
