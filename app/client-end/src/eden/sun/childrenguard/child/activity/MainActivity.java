package eden.sun.childrenguard.child.activity;

import java.util.List;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import eden.sun.childrenguard.child.R;
import eden.sun.childrenguard.child.db.dao.AppDao;
import eden.sun.childrenguard.child.util.Config;
import eden.sun.childrenguard.child.util.DeviceHelper;
import eden.sun.childrenguard.child.util.JSONUtil;
import eden.sun.childrenguard.child.util.RequestHelper;
import eden.sun.childrenguard.child.util.RequestURLConstants;
import eden.sun.childrenguard.child.util.UIUtil;
import eden.sun.childrenguard.server.dto.AppViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;

public class MainActivity extends CommonActivity {
	protected static final String TAG = "MainActivity";
	private TextView text;
	private AppDao appDao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		appDao = new AppDao(this);
		
		text = (TextView)findViewById(R.id.text);
		
		syncAppFromServer();
	}

	private void syncAppFromServer() {
		text.setText("Synchronizing application infomations...");
		
		RequestHelper helper = getRequestHelper();
		String imei = DeviceHelper.getIMEI(this);
		String url = String.format(
				Config.BASE_URL_MVC + RequestURLConstants.URL_LIST_CHILD_APP_INFO + "?imei=%1$s",  
				imei);  

		helper.doGet(
			url,
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					ViewDTO<List<AppViewDTO>> view = JSONUtil.getListChildAppInfoView(response);
			    	
			    	if( view.getMsg().equals(ViewDTO.MSG_SUCCESS)){
			    		text.setText("Synchronizing application infomations...Succcess");
			    		if( view.getData() != null ){
			    			List<AppViewDTO> appList = view.getData();

			    			appDao.clearAll();
			    			appDao.batchAdd(appList);
			    		}
			    		
			    	}else{
			    		AlertDialog.Builder dialog = UIUtil.getErrorDialog(MainActivity.this,view.getInfo());
			    		
						dialog.show();
			    	}
			    	
				}

			}, 
			new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					Log.e(TAG, error.getMessage(), error);
					AlertDialog.Builder dialog = UIUtil.getErrorDialog(MainActivity.this,error.getMessage());
		    		
					dialog.show();
			}
		});	
	}

	
}
