package eden.sun.childrenguard.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import eden.sun.childrenguard.R;
import eden.sun.childrenguard.server.dto.IsFirstLoginViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.util.Config;
import eden.sun.childrenguard.util.JSONUtil;
import eden.sun.childrenguard.util.RequestHelper;
import eden.sun.childrenguard.util.RequestURLConstants;
import eden.sun.childrenguard.util.StringUtil;
import eden.sun.childrenguard.util.UIUtil;

public class ModifyLockPasswordActivity extends CommonActivity {
	private Integer childId;
	private Button cancelBtn;
	private Button okBtn;
	private ProgressDialog progress;
	private EditText passwordEditText;
	private EditText confirmPasswordEditText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_lock_password);
		Intent intent = this.getIntent();
		childId = intent.getIntExtra("childId",0);
		
		initComponent();
		
		
	}

	private void initComponent() {
		okBtn = (Button)findViewById(R.id.okBtn);
		okBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				boolean valid = doValidation();
				
				if( valid ){
					changePassword();
				}
				/*progress = ProgressDialog.show(ModifyLockPasswordActivity.this, "Change Lock Password",
					    "Please wait...", true);

					Timer timer;
					TimerTask task = new TimerTask(){
						public void run(){    
							progress.dismiss();
							
							ModifyLockPasswordActivity.this.runOnUiThread(new Runnable(){

								@Override
								public void run() {
									new AlertDialog.Builder(ModifyLockPasswordActivity.this)
							        .setIcon(android.R.drawable.ic_dialog_alert)
							        .setTitle("Change Lock Password")
							        .setMessage("Change Password Success!")
							        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		
							            @Override
							            public void onClick(DialogInterface dialog, int which) {
							            	ModifyLockPasswordActivity.this.finish();
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

			
		});
		cancelBtn = (Button)findViewById(R.id.cancelBtn);
		cancelBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				ModifyLockPasswordActivity.this.finish();
			}
		});
		
		passwordEditText = (EditText)findViewById(R.id.passwordEditText);
		confirmPasswordEditText = (EditText)findViewById(R.id.confirmPasswordEditText);
	}

	private boolean doValidation() {
		String password = UIUtil.getEditTextValue(passwordEditText);
		String confirmPassword = UIUtil.getEditTextValue(confirmPasswordEditText);
				
		if( StringUtil.isBlank(password) ){
			String title = "Change Lock Password";
			String msg = "Password can not be blank.";
			String btnText = "OK";
			
			AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
				ModifyLockPasswordActivity.this,
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
			String title = "Change Lock Password";
			String msg = "Password can not be blank.";
			String btnText = "OK";
			
			AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
				ModifyLockPasswordActivity.this,
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
			String title = "Change Lock Password";
			String msg = "Password and confirm password must be same.";
			String btnText = "OK";
			
			AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
				ModifyLockPasswordActivity.this,
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
	
	private void changePassword() {
		String title = "Change Lock Password";
		String msg = "Please wait...";
		showProgressDialog(title,msg);
		
		RequestHelper helper = getRequestHelper();
		String password = UIUtil.getEditTextValue(passwordEditText);
		String confirmPassword = UIUtil.getEditTextValue(confirmPasswordEditText);
		
		String url = String.format(
				Config.BASE_URL_MVC + RequestURLConstants.URL_CHANGE_LOCK_PASSWORD + "?childId=%1$s&password=%2$s",  
				childId,
				password);  

		helper.doGet(
			url,
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					ViewDTO<Boolean> view = JSONUtil.getChangeLockPasswordView(response);
			    	
			    	if( view.getMsg().equals(ViewDTO.MSG_SUCCESS)){
			    		String title = "Change Password";
						String msg = "Change lock password success.";
						String btnText = "OK";
						
						AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
								ModifyLockPasswordActivity.this,
							title,
							msg,
							btnText,
							new DialogInterface.OnClickListener() {
					            @Override
					            public void onClick(DialogInterface dialog, int which) {
					            	dialog.dismiss();
					            	ModifyLockPasswordActivity.this.finish();
					            }
					        }
						);
						
						dialog.show();
			    	}else{
			    		dismissProgressDialog();
			    		AlertDialog.Builder dialog = UIUtil.getErrorDialog(ModifyLockPasswordActivity.this,view.getInfo());
			    		
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
