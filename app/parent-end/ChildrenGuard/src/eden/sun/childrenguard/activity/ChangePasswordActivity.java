package eden.sun.childrenguard.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;

import eden.sun.childrenguard.R;
import eden.sun.childrenguard.errhandler.DefaultVolleyErrorHandler;
import eden.sun.childrenguard.helper.DeviceHelper;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.util.Config;
import eden.sun.childrenguard.util.JSONUtil;
import eden.sun.childrenguard.util.RequestURLConstants;
import eden.sun.childrenguard.util.UIUtil;

public class ChangePasswordActivity extends CommonActivity  {
	/* UI Components */
	private Button changePasswordBtn;
	private Button cancelBtn;
	private EditText newPasswordEditText;
	private EditText resetCodeEditText;
	/* END - UI Components */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
		
		resetCodeEditText = (EditText)findViewById(R.id.resetCodeEditText);
		newPasswordEditText = (EditText)findViewById(R.id.newPasswordEditText);
		
		changePasswordBtn = (Button)findViewById(R.id.changePasswordBtn);
		changePasswordBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				doChangePassword();
				
				/*String title = "Change Password";
				String msg = "Change password success. Press OK to login.";
				String btnText = "OK";
				
				AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
					ChangePasswordActivity.this,
					title,
					msg,
					btnText,
					new DialogInterface.OnClickListener() {
			            @Override
			            public void onClick(DialogInterface dialog, int which) {
			            	dialog.dismiss();
			            	ChangePasswordActivity.this.finish();
			            }
			        }
				);
				
				dialog.show();*/
			}

        });
		
		cancelBtn = (Button)findViewById(R.id.cancelBtn);
		cancelBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				ChangePasswordActivity.this.finish();
			}
        	
        });
		
	}
	
	@Override
	public void onStart() {
		super.onStart();
	}
	

	private void doChangePassword() {
		String resetCode = UIUtil.getEditTextValue(resetCodeEditText);
		String password = UIUtil.getEditTextValue(newPasswordEditText);
		
		Map<String, String> params = new HashMap<String,String>();
		params.put("imei", DeviceHelper.getIMEI(this));
		params.put("resetCode", resetCode);
		params.put("password", password);
		
		String url = Config.getInstance().BASE_URL_MVC + RequestURLConstants.URL_DO_CHANGE_PASSWORD;

		String title = "Change Password";
		String msg = "Please wait...";
		showProgressDialog(title,msg);
		
		getRequestHelper().doPost(
			url,
			params,
			ChangePasswordActivity.this.getClass(),
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					dismissProgressDialog();
					
					final ViewDTO<String> view = JSONUtil.getDoChangePasswordView(response);
			    	
					if( view.getMsg().equals(ViewDTO.MSG_SUCCESS) ){
						String title = "Change Password";
						String msg = view.getInfo();
						String btnText = "Back to Login";
						
						AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
							ChangePasswordActivity.this,
							title,
							msg,
							btnText,
							new DialogInterface.OnClickListener() {
					            @Override
					            public void onClick(DialogInterface dialog, int which) {
					            	dialog.dismiss();
					            	
					            	finish();
					            }
					        }
						);
						
						dialog.show();
						
					}else{
						String title = "Error";
						String msg = view.getInfo();
						String btnText = "OK";
						
						AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
							ChangePasswordActivity.this,
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
			new DefaultVolleyErrorHandler(ChangePasswordActivity.this)
		);		
	}
}
