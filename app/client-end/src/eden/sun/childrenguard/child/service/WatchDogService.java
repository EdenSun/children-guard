package eden.sun.childrenguard.child.service;

import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import eden.sun.childrenguard.child.activity.AppPasswordActivity;
import eden.sun.childrenguard.child.activity.MainActivity;
import eden.sun.childrenguard.child.db.dao.AppDao;
import eden.sun.childrenguard.child.db.model.App;
import eden.sun.childrenguard.child.util.Callback;
import eden.sun.childrenguard.child.util.Config;
import eden.sun.childrenguard.child.util.DeviceHelper;
import eden.sun.childrenguard.child.util.JSONUtil;
import eden.sun.childrenguard.child.util.RequestHelper;
import eden.sun.childrenguard.child.util.RequestURLConstants;
import eden.sun.childrenguard.child.util.UIUtil;
import eden.sun.childrenguard.server.dto.AppViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;

public class WatchDogService extends Service{
	protected static final String TAG = "WatchDogService";
	private AppDao dao;  
	private List<App> appList; 
	private List<App> unlockedAppList;
    private ActivityManager activityManager;  
    private Intent intent;  
    private boolean flag = true;  
    private LocalBinder binder = new LocalBinder();
  
    @Override  
    public IBinder onBind(Intent intent)  
    {  
        return binder;  
    }  
  
    @Override  
    public void onCreate()  
    {  
        super.onCreate();  
  
        initAppData();
        
        activityManager = (ActivityManager) getSystemService(Service.ACTIVITY_SERVICE);  
  
        intent = new Intent(this, AppPasswordActivity.class);  
        
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
        
        new Thread()  
        {  
            public void run()  
            {  
                while (flag)  
                {  
                    try  
                    {  
                        List<RunningTaskInfo> runningTaskInfos = activityManager  
                                .getRunningTasks(1);  
                        RunningTaskInfo runningTaskInfo = runningTaskInfos  
                                .get(0);  
                        String packageName = runningTaskInfo.topActivity  
                                .getPackageName();
                        if( appList.contains(new App(packageName)) && !unlockedAppList.contains(new App(packageName)) )  
                        {  
                            intent.putExtra("packageName", packageName);  
                            startActivity(intent);  
                        }  
                        else  
                        {  
  
                        }  
                        sleep(1000);  
                    }  
                    catch (InterruptedException e)  
                    {  
                        e.printStackTrace();  
                    }  
                }  
            }  
        }.start();  
    }  
  
    public void initAppData() {
    	dao = new AppDao(this);  
        appList = dao.listLockedApp();
        
        unlockedAppList = new ArrayList<App>();
	}

    public void unlockApp(String packageName){
    	if( unlockedAppList == null ){
    		unlockedAppList = new ArrayList<App>();
    	}
    	
    	App app = appList.get(appList.indexOf(new App(packageName)));
    	unlockedAppList.add(app);
    }
    
	@Override  
    public void onDestroy()  
    {  
        super.onDestroy();  
        flag = false;  
    }  
	
	public class LocalBinder extends Binder {
		public WatchDogService getService() {
			return WatchDogService.this;
		}
	}

	public void syncAppFromServer(final Callback callback) {
		RequestHelper helper = RequestHelper.getInstance(this);
		String imei = DeviceHelper.getIMEI(this);
		String url = String.format(
				Config.BASE_URL_MVC + RequestURLConstants.URL_LIST_CHILD_APP_INFO + "?imei=%1$s",  
				imei);  

		final AppDao appDao = new AppDao(this);
		helper.doGet(
			url,
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					Log.d(TAG, response);
					ViewDTO<List<AppViewDTO>> view = JSONUtil.getListChildAppInfoView(response);
			    	
			    	if( view.getMsg().equals(ViewDTO.MSG_SUCCESS)){
			    		if( view.getData() != null ){
			    			List<AppViewDTO> appList = view.getData();

			    			appDao.clearAll();
			    			appDao.batchAdd(appList);
			    			
			    			//init locked app list in watch dog service
			    			initAppData();
			    		}
			    		
			    		callback.callback();
			    	}else{
			    		Log.e(TAG, "server error.");
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
