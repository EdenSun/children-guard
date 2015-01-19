package eden.sun.childrenguard.fragment;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;

import eden.sun.childrenguard.R;
import eden.sun.childrenguard.errhandler.DefaultVolleyErrorHandler;
import eden.sun.childrenguard.helper.DialogHelper;
import eden.sun.childrenguard.helper.RequestHelper;
import eden.sun.childrenguard.helper.SharedPreferencesHelper;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.util.Config;
import eden.sun.childrenguard.util.JSONUtil;
import eden.sun.childrenguard.util.RegexHelper;
import eden.sun.childrenguard.util.RequestURLConstants;
import eden.sun.childrenguard.util.StringUtil;
import eden.sun.childrenguard.util.UIUtil;

public class EmailSettingDialogFragment extends DialogFragment {
	private static final String TAG = "EmailSettingDialogFragment";
	private EditText emailEditText;
	
	public EmailSettingDialogFragment() {
		super();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater mInflater = LayoutInflater.from(getActivity());  
        View v = mInflater.inflate(R.layout.dialog_email_setting,null);  
        
        initComponent(v);
        
        Dialog dialog = new AlertDialog.Builder(getActivity())  
                .setTitle("Email Setting")  
                .setView(v)  
                .setPositiveButton("Confirm",  
                    new DialogInterface.OnClickListener() {  
                        public void onClick(DialogInterface dialog, int whichButton) {
                        	DialogHelper.preventDialogClose(dialog);
                            
                        	if( doValidate() ){
                        		// save email
                        		String email = UIUtil.getEditTextValue(emailEditText);
                        		doSaveEmail(getAccessToken(),email);
                        		
                        		//close dialog
                        		DialogHelper.closeDialog(dialog);
                        	}
                        }

                    }  
                )  
                .setNegativeButton("Cancel",  
                    new DialogInterface.OnClickListener() {  
                        public void onClick(DialogInterface dialog, int whichButton) {
                        	dialog.dismiss();
                        }
                    }
                )  
                .create();  
        dialog.setCancelable(false);
        return dialog;
	}
	
	
	private void doSaveEmail(String accessToken,
			String email) {
		String url = Config.BASE_URL_MVC + RequestURLConstants.URL_EMAIL_SETTING_SAVE;  

		Map<String,String> params = new HashMap<String,String>();
		params.put("accessToken", accessToken);
		params.put("email", email);
		
		final Activity context = getActivity();
		RequestHelper.getInstance(getActivity()).doPost(
			url,
			params,
			getActivity().getClass(),
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					final ViewDTO<Boolean> view = JSONUtil.getSaveEmailView(response);
							
					if( view.getMsg().equals(ViewDTO.MSG_SUCCESS) ){
						Toast.makeText(context, "Email saved", Toast.LENGTH_SHORT).show();
						
					}else{
						String title = "Error";
						String msg = view.getInfo();
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
					}
				}
			}, 
			new DefaultVolleyErrorHandler(context));
	}
	
	
	private String getAccessToken() {
		SharedPreferencesHelper helper = SharedPreferencesHelper.getInstance(EmailSettingDialogFragment.this.getActivity());
		return helper.getAccessToken();
	}
	
	private void initComponent(View v) {
		emailEditText = (EditText)v.findViewById(R.id.emailEditText);
	}
	
	private boolean doValidate() {
		String email = UIUtil.getEditTextValue(emailEditText);
		
		if( StringUtil.isBlank(email) ){
			Toast.makeText(getActivity(), "Email can not be blank", Toast.LENGTH_SHORT).show();
			return false;
			/*String title = "Email Setting";
			String msg = "Email can not be blank.";
			String btnText = "OK";
			
			AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
				getActivity(),
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
			return false;*/
		}else{
			if( !RegexHelper.isEmail(email) ){
				Toast.makeText(getActivity(), "Email format is incorrect", Toast.LENGTH_SHORT).show();
				return false;
				
				/*String title = "Email Setting";
				String msg = "Email format is incorrect.";
				String btnText = "OK";
				
				AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
					getActivity(),
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
				return false;*/
			}
		}
		
		Log.i(TAG, "Validation is pass.");
		return true;
	}
	
}
