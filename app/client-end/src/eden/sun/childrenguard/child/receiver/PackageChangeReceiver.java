package eden.sun.childrenguard.child.receiver;

import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import eden.sun.childrenguard.child.db.dao.AppDao;
import eden.sun.childrenguard.child.util.ApplicationHelper;
import eden.sun.childrenguard.child.util.Config;
import eden.sun.childrenguard.child.util.DeviceHelper;
import eden.sun.childrenguard.child.util.JSONUtil;
import eden.sun.childrenguard.child.util.RequestHelper;
import eden.sun.childrenguard.child.util.RequestURLConstants;
import eden.sun.childrenguard.child.util.UIUtil;
import eden.sun.childrenguard.server.dto.AppViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;

public class PackageChangeReceiver extends BroadcastReceiver{
	private static final String TAG = "PackageChangeReceiver";
	private static final Object ACTION_PACKAGE_ADDED = "android.intent.action.PACKAGE_ADDED";
	private static final Object ACTION_PACKAGE_REMOVED = "android.intent.action.PACKAGE_REMOVED";
	
	
	@Override
	public void onReceive(Context context, Intent intent) {
		boolean replacing = intent.getBooleanExtra(Intent.EXTRA_REPLACING, false);

		Uri data = intent.getData();
		
		/*Log.d(TAG, "Action: " + intent.getAction());
		Log.d(TAG, "The DATA: " + data);	
		Log.d(TAG, "Is Replacing: " + replacing);	*/
		String packageName = data.toString().substring("package:".length());
		String appName = ApplicationHelper.getAppNameByPackage(context, packageName);
		
		if( intent.getAction().equals(ACTION_PACKAGE_ADDED) ){
			// submit added package 
			
			onAppInstalled(context,appName,packageName);
		}else if( intent.getAction().equals(ACTION_PACKAGE_REMOVED) ){
			// submit removed package
			
			onAppUnistalled(context,appName,packageName);
		}
	}

	private void onAppInstalled(final Context context,String appName,String packageName) {
		RequestHelper helper = RequestHelper.getInstance(context);	
		String url = Config.BASE_URL_MVC + RequestURLConstants.URL_INSTALL_APP;

		String imei = DeviceHelper.getIMEI(context);
		Map<String,String> param = new HashMap<String,String>();
		param.put("imei", imei);
		param.put("appName", appName);
		param.put("packageName", packageName);
		helper.doPost(
			url,
			param,
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					ViewDTO<AppViewDTO> view = JSONUtil.getInstallAppView(response);
			    	
			    	if( view.getMsg().equals(ViewDTO.MSG_SUCCESS)){
			    		AppDao appDao = new AppDao(context);
			    		appDao.addOrUpdate(view.getData());
			    	}
				}

			}, 
			new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					Log.e(TAG, error.getMessage(), error);
			}
		});
		
	}

	private void onAppUnistalled(final Context context, String appName, String packageName) {
		RequestHelper helper = RequestHelper.getInstance(context);	
		String url = Config.BASE_URL_MVC + RequestURLConstants.URL_UNINSTALL_APP;

		String imei = DeviceHelper.getIMEI(context);
		Map<String,String> param = new HashMap<String,String>();
		param.put("imei", imei);
		param.put("appName", appName);
		param.put("packageName", packageName);
		helper.doPost(
			url,
			param,
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					Log.d(TAG, "Uninstall App Response:" + response);
					ViewDTO<AppViewDTO> view = JSONUtil.getInstallAppView(response);
			    	
			    	if( view.getMsg().equals(ViewDTO.MSG_SUCCESS)){
			    		AppDao appDao = new AppDao(context);
			    		appDao.delete(view.getData().getId());
			    	}
				}

			}, 
			new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					Log.e(TAG, error.getMessage(), error);
			}
		});
	}


	
	

}
