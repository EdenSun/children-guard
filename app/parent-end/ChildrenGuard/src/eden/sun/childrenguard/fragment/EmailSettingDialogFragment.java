package eden.sun.childrenguard.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;
import eden.sun.childrenguard.R;
import eden.sun.childrenguard.util.RegexHelper;
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
                        	if( doValidate() ){
                        		//TODO: save email
                        		Toast.makeText(getActivity(), "Save email", Toast.LENGTH_SHORT).show();
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
