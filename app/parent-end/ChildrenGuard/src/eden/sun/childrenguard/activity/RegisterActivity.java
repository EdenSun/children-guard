package eden.sun.childrenguard.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import eden.sun.childrenguard.R;
import eden.sun.childrenguard.comet.RegisterListener;
import eden.sun.childrenguard.server.dto.RegisterViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.util.CometdConfig;
import eden.sun.childrenguard.util.Config;
import eden.sun.childrenguard.util.JSONUtil;
import eden.sun.childrenguard.util.RequestHelper;
import eden.sun.childrenguard.util.RequestURLConstants;
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
					doRegister();
					/*AsyncTask<Map<String, Object>,Integer,Boolean> task = new RegisterTask(RegisterActivity.this);
					
					String firstName = UIUtil.getEditTextValue(firstNameEditText);
					String lastName = UIUtil.getEditTextValue(lastNameEditText);
					String email = UIUtil.getEditTextValue(emailEditText);
					String password = UIUtil.getEditTextValue(passwordEditText);
					
					Map<String, Object> data = new HashMap<String,Object>();
					
					data.put("firstName", firstName);
					data.put("lastName", lastName);
					data.put("email", email);
					data.put("password", password);
					
					task.execute(data);*/
					
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

	private void doRegister() {
		RequestHelper helper = getRequestHelper();

		String url = Config.BASE_URL_MVC + RequestURLConstants.URL_REGISTER;  

		String title = "Register";
		String msg = "Please wait...";
		showProgressDialog(title,msg);	
		
		Map<String,String> params = this.getRegisterParams();
		helper.doPost(
			url,
			params,
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					dismissProgressDialog();
					
					final ViewDTO<RegisterViewDTO> view = JSONUtil.getRegisterView(response);
							
					if( view.getMsg().equals(ViewDTO.MSG_SUCCESS) ){
						String title = "Register";
						String msg = "Register Success.Press OK to login.";
						String btnText = "OK";
						
						AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
							RegisterActivity.this,
							title,
							msg,
							btnText,
							new DialogInterface.OnClickListener() {
					            @Override
					            public void onClick(DialogInterface dialog, int which) {
					            	RegisterActivity.this.finish();
					            }
					        }
						);
						
						dialog.show();
					}else{
						String title = "Error";
						String msg = view.getInfo();
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
	
	private Map<String, String> getRegisterParams() {
		String firstName = UIUtil.getEditTextValue(firstNameEditText);
		String lastName = UIUtil.getEditTextValue(lastNameEditText);
		String email = UIUtil.getEditTextValue(emailEditText);
		String password = UIUtil.getEditTextValue(passwordEditText);
		
		Map<String, String> param = new HashMap<String,String>();
		
		param.put("firstName", firstName);
		param.put("lastName", lastName);
		param.put("email", email);
		param.put("password", password);
		
		return param;
	}

	private boolean doValidation() {
		String firstName = UIUtil.getEditTextValue(firstNameEditText);
		String lastName = UIUtil.getEditTextValue(lastNameEditText);
		String email = UIUtil.getEditTextValue(emailEditText);
		String password = UIUtil.getEditTextValue(passwordEditText);
		String confirmPassword = UIUtil.getEditTextValue(confirmPasswordEditText);
				
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
	
	/*class RegisterTask extends AsyncTask<Map<String, Object>,Integer,Boolean>{
		private Activity context;
		
		public RegisterTask(Activity context) {
			super();
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			String title = "Register";
			String msg = "Please wait...";
			showProgressDialog(title,msg);
		}

		@Override
		protected Boolean doInBackground(Map<String, Object>... params) {
			Map<String, Object> data = params[0];
			
			runtime.publish(data, CometdConfig.REGISTER_CHANNEL,new RegisterListener(RegisterActivity.this));
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			
			dismissProgressDialog();
		}
		
	}*/
}
