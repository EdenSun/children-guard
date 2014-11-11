package eden.sun.childrenguard.child.service;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import eden.sun.childrenguard.child.activity.AppPasswordActivity;
import eden.sun.childrenguard.child.db.dao.AppDao;
import eden.sun.childrenguard.child.db.model.App;

public class WatchDogService extends Service{
	private AppDao dao;  
	private List<App> appList; 
    private ActivityManager activityManager;  
    private Intent intent;  
    private boolean flag = true;  
  
    @Override  
    public IBinder onBind(Intent intent)  
    {  
        return null;  
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
                        if( appList.contains(new App(packageName)) )  
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
  
    private void initAppData() {
    	dao = new AppDao(this);  
        
        appList = dao.listLockedApp();
	}

	@Override  
    public void onDestroy()  
    {  
        super.onDestroy();  
        flag = false;  
    }  
}
