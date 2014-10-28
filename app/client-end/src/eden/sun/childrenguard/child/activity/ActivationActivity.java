package eden.sun.childrenguard.child.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import eden.sun.childrenguard.child.R;
import eden.sun.childrenguard.child.util.Config;
import eden.sun.childrenguard.child.util.JSONUtil;
import eden.sun.childrenguard.child.util.RequestHelper;
import eden.sun.childrenguard.child.util.RequestURLConstants;
import eden.sun.childrenguard.child.util.ShareDataKey;
import eden.sun.childrenguard.child.util.UIUtil;
import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;

public class ActivationActivity extends CommonActivity {
	/*private Button startServiceBtn;
	private Button killServiceBtn;
	private Button appStatusListBtn;*/
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activation);

		String childMobile = getChildMobile();
		if( childMobile == null ){
			// can not get child mobile
			
		}else{
			// can get child mobile
			
			doInitData(childMobile);
		}
	}

	private void doInitData(String childMobile) {
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
	}
	

}
