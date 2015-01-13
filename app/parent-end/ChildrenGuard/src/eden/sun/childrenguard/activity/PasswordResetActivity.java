package eden.sun.childrenguard.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;

import eden.sun.childrenguard.R;
import eden.sun.childrenguard.errhandler.DefaultVolleyErrorHandler;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.util.Config;
import eden.sun.childrenguard.util.JSONUtil;
import eden.sun.childrenguard.util.RequestURLConstants;
import eden.sun.childrenguard.util.StringUtil;
import eden.sun.childrenguard.util.UIUtil;

public class PasswordResetActivity extends CommonActivity {
	/* UI Components */
	private Button getPasswordBtn;
	private Button backBtn;
	
	private EditText mobileEditText;
	/* END - UI Components */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password_reset);
		
		mobileEditText = (EditText)findViewById(R.id.mobileEditText);
		
		getPasswordBtn = (Button)findViewById(R.id.getPasswordBtn);
		getPasswordBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				boolean isPassed = doValidation();
				
				if( isPassed ){
					String mobile = UIUtil.getEditTextValue(mobileEditText);
					
					Map<String, String> data = new HashMap<String,String>();
					data.put("mobile", mobile);
					
					String url = Config.BASE_URL_MVC + RequestURLConstants.URL_RESET_SEND_PWD_TO_MOBILE;
	  
					String title = "Find Your Password";
					String msg = "Please wait...";
					showProgressDialog(title,msg);
					
					getRequestHelper().doPost(
						url,
						data,
						PasswordResetActivity.this.getClass(),
						new Response.Listener<String>() {
							@Override
							public void onResponse(String response) {
								dismissProgressDialog();
								
								final ViewDTO<Boolean> view = JSONUtil.getSendPwdToMobileView(response);
						    	
								if( view.getMsg().equals(ViewDTO.MSG_SUCCESS) ){
									String title = "Find Your Password";
									String msg = "Password has been sent to your mobile.";
									String btnText = "Ok";
									
									AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
										PasswordResetActivity.this,
										title,
										msg,
										btnText,
										new DialogInterface.OnClickListener() {
								            @Override
								            public void onClick(DialogInterface dialog, int which) {
								            	dialog.dismiss();
								            	
												PasswordResetActivity.this.finish();
								            }
								        }
									);
									
									dialog.show();
									
								}else{
									String title = "Error";
									String msg = view.getInfo();
									String btnText = "OK";
									
									AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
										PasswordResetActivity.this,
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
						new DefaultVolleyErrorHandler(PasswordResetActivity.this)
					);
					
				}
				
			}

        });
		
		backBtn = (Button)findViewById(R.id.backBtn);
		backBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				PasswordResetActivity.this.finish();
			}
        	
        });
	}

	private boolean doValidation() {
		String mobile = UIUtil.getEditTextValue(mobileEditText);
		
		if( StringUtil.isBlank(mobile) ){
			String title = "Reset Password";
			String msg = "Please input your mobile.";
			String btnText = "OK";
			
			AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
				PasswordResetActivity.this,
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
	
}
