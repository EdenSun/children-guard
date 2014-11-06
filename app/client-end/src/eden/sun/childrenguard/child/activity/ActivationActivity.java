package eden.sun.childrenguard.child.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import cn.jpush.android.api.JPushInterface;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import eden.sun.childrenguard.child.R;
import eden.sun.childrenguard.child.util.Config;
import eden.sun.childrenguard.child.util.DeviceHelper;
import eden.sun.childrenguard.child.util.JSONUtil;
import eden.sun.childrenguard.child.util.RequestHelper;
import eden.sun.childrenguard.child.util.RequestURLConstants;
import eden.sun.childrenguard.child.util.ShareDataKey;
import eden.sun.childrenguard.child.util.UIUtil;
import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;

public class ActivationActivity extends CommonActivity {
	private Button activateBtn;
	private EditText parentEmailEditText;
	private EditText childMobileEditText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activation);

		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);

		initComponent();
		
		activateBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				doActivate();
				
			}

		});
	}

	private void initComponent() {
		activateBtn = (Button)this.findViewById(R.id.activateBtn);
		parentEmailEditText = (EditText)this.findViewById(R.id.parentEmailEditText);
		childMobileEditText = (EditText)this.findViewById(R.id.childMobileEditText);
	}

	private void doActivate() {
		String title = "Activate Account";
		String msg = "Please wait...";
		showProgressDialog(title,msg);
		
		RequestHelper helper = getRequestHelper();
		String url = Config.BASE_URL_MVC + RequestURLConstants.URL_ACTIVATE_ACCOUNT;

		String parentEmail = UIUtil.getEditTextValue(parentEmailEditText);
		String childMobile = UIUtil.getEditTextValue(childMobileEditText);
		String imei = DeviceHelper.getIMEI(this);
		Map<String,String> params = new HashMap<String,String>();
		params.put("parentEmail", parentEmail);
		params.put("childMobile", childMobile);
		params.put("imei", imei);
		
		helper.doPost(
			url,
			params,
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					ViewDTO<Boolean> view = JSONUtil.getActivateAccountView(response);
			    	
			    	if( view.getMsg().equals(ViewDTO.MSG_SUCCESS)){
			    		// success
			    		
			    	}else{
			    		dismissProgressDialog();
			    		AlertDialog.Builder dialog = UIUtil.getErrorDialog(ActivationActivity.this,view.getInfo());
			    		
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
	
	/*private void doInitData(String childMobile) {
		String title = "Initializing data";
		String msg = "Please wait...";
		showProgressDialog(title,msg);
		
		RequestHelper helper = getRequestHelper();
		String url = String.format(
				Config.BASE_URL_MVC + RequestURLConstants.URL_CHILD_LOGIN + "?childMobile=%1$s",  
				childMobile);  

		helper.doGet(
			url,
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					ViewDTO<ChildViewDTO> view = JSONUtil.getChildLoginView(response);
			    	
			    	if( view.getMsg().equals(ViewDTO.MSG_SUCCESS)){
			    		String accessToken = view.getData().getAccessToken();
			    		
			    		putStringShareData(ShareDataKey.CHILD_MOBILE,accessToken);
			    		
			    	}else{
			    		dismissProgressDialog();
			    		AlertDialog.Builder dialog = UIUtil.getErrorDialog(ActivationActivity.this,view.getInfo());
			    		
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
	}*/
	

}
