package eden.sun.childrenguard.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
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

import com.android.volley.Response;

import eden.sun.childrenguard.R;
import eden.sun.childrenguard.errhandler.DefaultVolleyErrorHandler;
import eden.sun.childrenguard.helper.DeviceHelper;
import eden.sun.childrenguard.server.dto.RegisterViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.util.Config;
import eden.sun.childrenguard.util.JSONUtil;
import eden.sun.childrenguard.util.RegexHelper;
import eden.sun.childrenguard.util.RequestURLConstants;
import eden.sun.childrenguard.util.StringUtil;
import eden.sun.childrenguard.util.UIUtil;

public class RegisterActivity extends CommonActivity {
	private static final String TAG = "RegisterActivity";
	
	/* ui components */
	private FrameLayout layout;
	private Button registerBtn;
	private Button backBtn;
	
	private EditText mobileEditText;
	private EditText passwordEditText;
	private EditText confirmPasswordEditText;
	private TextView agreementTextView;
	
	/* END - ui components */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		layout = (FrameLayout)findViewById(android.R.id.content);
        layout.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				hideKeyboard(v);
		        return false;
			}
        	
        });
        
		agreementTextView = (TextView)findViewById(R.id.agreementTextView);
		agreementTextView.setText(Html.fromHtml("By clicking \"Sign Up\" you are indicating that you have read and agree to the <a href='"+ Config.getInstance().BASE_URL + Config.getInstance().TERMS_OF_SERVICE_PATH + "'>Terms of Service</a> and <a href='"+ Config.getInstance().BASE_URL + Config.getInstance().PRIVACY_POLICY_PATH + "'>Privacy Policy</a>."));
		agreementTextView.setMovementMethod(LinkMovementMethod.getInstance());

		mobileEditText = (EditText)findViewById(R.id.mobileEditText);
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
		String url = Config.getInstance().BASE_URL_MVC + RequestURLConstants.URL_REGISTER;  

		String title = "Register";
		String msg = "Please wait...";
		showProgressDialog(title,msg);	
		
		Map<String,String> params = this.getRegisterParams();
		getRequestHelper().doPost(
			url,
			params,
			RegisterActivity.this.getClass(),
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
			new DefaultVolleyErrorHandler(RegisterActivity.this));
	}
	
	private Map<String, String> getRegisterParams() {
		String mobile = UIUtil.getEditTextValue(mobileEditText);
		String password = UIUtil.getEditTextValue(passwordEditText);
		String imei = DeviceHelper.getIMEI(this);
		
		Map<String, String> param = new HashMap<String,String>();
		
		param.put("mobile", mobile);
		param.put("password", password);
		param.put("imei", imei);
		
		return param;
	}

	private boolean doValidation() {
		String mobile = UIUtil.getEditTextValue(mobileEditText);
		String password = UIUtil.getEditTextValue(passwordEditText);
		String confirmPassword = UIUtil.getEditTextValue(confirmPasswordEditText);
				
		if( StringUtil.isBlank(mobile) ){
			String title = "Register";
			String msg = "Mobile can not be blank.";
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
		
		if( mobile.length() != 10 ){
			String title = "Login";
			String msg = "10-digit phone number is required.";
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
		}else{
			if( !RegexHelper.isValidPassword(password) ){
				String title = "Register";
				String msg = "Password can only contain 4 numbers.";
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
		}else{
			if( !RegexHelper.isValidPassword(confirmPassword) ){
				String title = "Register";
				String msg = "Confirm password can only contain 4 numbers.";
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
	
	/**
	* Hides virtual keyboard
	*/
	protected void hideKeyboard(View view)
	{
	    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	    in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}
}
