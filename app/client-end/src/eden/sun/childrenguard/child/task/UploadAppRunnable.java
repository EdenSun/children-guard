package eden.sun.childrenguard.child.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import eden.sun.childrenguard.child.dto.AppInfo;
import eden.sun.childrenguard.child.util.ApplicationHelper;
import eden.sun.childrenguard.child.util.Config;
import eden.sun.childrenguard.child.util.JSONUtil;
import eden.sun.childrenguard.child.util.RequestHelper;
import eden.sun.childrenguard.child.util.RequestURLConstants;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.UploadApplicationInfoParam;


public class UploadAppRunnable implements Runnable{
	private static final String TAG = "UploadAppRunnable";
	private Context context;
	private Handler handler;
	
	public UploadAppRunnable(Context context, Handler handler) {
		Log.i(TAG, "create upload app runnable");
		this.context = context;
		this.handler = handler;
	}

	@Override
	public void run() {
		List<AppInfo> appList = ApplicationHelper.listAllApplication(context.getPackageManager());
		if( appList != null ){
			Log.i(TAG, "Get application list number:" + appList.size());
			StringBuffer apps = new StringBuffer();
			List<UploadApplicationInfoParam> requestParam = new ArrayList<UploadApplicationInfoParam>();
			UploadApplicationInfoParam appInfoParam = null;
			for(AppInfo app: appList){
				appInfoParam = new UploadApplicationInfoParam();
				appInfoParam.setAppName(app.getAppName());
				appInfoParam.setPackageName(app.getPackageName());
				
				requestParam.add(appInfoParam);
			}
			
			postAppInfoToServer(requestParam);
			
			Log.i(TAG, apps.toString());
		}
		
		/*Message message=new Message();  
        message.what=1;  
        handler.sendMessage(message);  */
	}

	private void postAppInfoToServer(List<UploadApplicationInfoParam> requestParam) {
		RequestHelper helper = RequestHelper.getInstance(context);
		String url = Config.BASE_URL_MVC + RequestURLConstants.URL_UPLOAD_ALL_APP_INFO;  

		Map<String,String> params = new HashMap<String,String>();
		params.put("appList", JSONUtil.transUploadApplicationInfoParamList2String(requestParam));
		params.put("childAccessToken", "");
		helper.doPost(
			url,
			params,
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					ViewDTO<Boolean> view = JSONUtil.getUploadAllAppInfoView(response);
			    	
			    	if( view.getMsg().equals(ViewDTO.MSG_SUCCESS)){
			    		
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
