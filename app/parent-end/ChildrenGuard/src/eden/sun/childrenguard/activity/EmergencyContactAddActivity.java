package eden.sun.childrenguard.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;

import eden.sun.childrenguard.R;
import eden.sun.childrenguard.errhandler.DefaultVolleyErrorHandler;
import eden.sun.childrenguard.helper.DeviceHelper;
import eden.sun.childrenguard.helper.RequestHelper;
import eden.sun.childrenguard.server.dto.RegisterViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.util.Config;
import eden.sun.childrenguard.util.JSONUtil;
import eden.sun.childrenguard.util.RequestURLConstants;
import eden.sun.childrenguard.util.StringUtil;
import eden.sun.childrenguard.util.UIUtil;

public class EmergencyContactAddActivity extends CommonActivity {
	private static final String TAG = "EmergencyContactAddActivity";
	private Button addBtn;
	private Button cancelBtn;
	private EditText phoneEditText;
	private EditText nameEditText;
	private Integer childId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exception_phone_add);
		
		setResult();
    	
		childId = getIntent().getIntExtra("childId", 0);
		
		phoneEditText = (EditText)findViewById(R.id.phoneEditText);
		nameEditText = (EditText)findViewById(R.id.nameEditText);
		
		addBtn = (Button)findViewById(R.id.addBtn);
		addBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				boolean valid = doValidation();
				
				if( valid ){
					doAddEmergencyContact();
				}
			}
		});
		cancelBtn = (Button)findViewById(R.id.cancelBtn);
		cancelBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
            	EmergencyContactAddActivity.this.finish();
			}
		});
	}

	private void setResult() {
		Intent intent = new Intent();
    	setResult(0,intent);		
	}

	private boolean doValidation() {
		String phone = UIUtil.getEditTextValue(phoneEditText);
		String name = UIUtil.getEditTextValue(nameEditText);
				
		String title = "Add Emergency Contacts";
		if( StringUtil.isBlank(phone) ){
			String msg = "Phone can not be blank.";
			String btnText = "OK";
			
			AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
				EmergencyContactAddActivity.this,
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

		if( StringUtil.isBlank(name) ){
			String msg = "Last name can not be blank.";
			String btnText = "OK";
			
			AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
				EmergencyContactAddActivity.this,
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
	
	private void doAddEmergencyContact() {
		String url = Config.getInstance().BASE_URL_MVC + RequestURLConstants.URL_ADD_EMERGENCY_CONTACT;  

		String title = "Add Emergency Contacts";
		String msg = "Please wait...";
		showProgressDialog(title,msg);	
		
		Map<String, String> params = new HashMap<String,String>();
		
		String name = UIUtil.getEditTextValue(nameEditText);
		String phone = UIUtil.getEditTextValue(phoneEditText);
		
		params.put("childId", childId.toString());
		params.put("name", name);
		params.put("phone", phone);
		
		getRequestHelper().doPost(
			url,
			params,
			this.getClass(),
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					dismissProgressDialog();
					
					final ViewDTO<RegisterViewDTO> view = JSONUtil.getRegisterView(response);
							
					if( view.getMsg().equals(ViewDTO.MSG_SUCCESS) ){
						String title = "Add Emergency Contacts";
						String msg = "Add Emergency Success.Press OK to return.";
						String btnText = "OK";
						
						AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
							EmergencyContactAddActivity.this,
							title,
							msg,
							btnText,
							new DialogInterface.OnClickListener() {
					            @Override
					            public void onClick(DialogInterface dialog, int which) {
					            	Intent intent = new Intent();
					            	intent.putExtra("result", "success");
					            	EmergencyContactAddActivity.this.setResult(0,intent);
					            	EmergencyContactAddActivity.this.finish();
					            }
					        }
						);
						
						dialog.show();
					}else{
						AlertDialog.Builder dialog = UIUtil.getErrorDialog(EmergencyContactAddActivity.this,view.getInfo());
			    		
						dialog.show();
					}
				}
			}, 
			new DefaultVolleyErrorHandler(EmergencyContactAddActivity.this));
	}
}
