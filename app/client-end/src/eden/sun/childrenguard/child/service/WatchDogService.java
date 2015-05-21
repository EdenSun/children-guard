package eden.sun.childrenguard.child.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import eden.sun.childrenguard.child.activity.AppPasswordActivity;
import eden.sun.childrenguard.child.db.dao.AppDao;
import eden.sun.childrenguard.child.db.dao.PresetLockAppDao;
import eden.sun.childrenguard.child.db.dao.PresetLockDao;
import eden.sun.childrenguard.child.db.model.App;
import eden.sun.childrenguard.child.db.model.PresetLock;
import eden.sun.childrenguard.child.db.model.PresetLockApp;
import eden.sun.childrenguard.child.util.Callback;
import eden.sun.childrenguard.child.util.Config;
import eden.sun.childrenguard.child.util.DataTypeUtil;
import eden.sun.childrenguard.child.util.DeviceHelper;
import eden.sun.childrenguard.child.util.JSONUtil;
import eden.sun.childrenguard.child.util.RequestHelper;
import eden.sun.childrenguard.child.util.RequestURLConstants;
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
  
    private Date presetLockStartTime;
    private Date presetLockEndTime;
    private boolean presetLockOnOff;
    private List<Integer> presetLockApp;
    private boolean[] repeat;
    
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
        initPresetlockData();
        
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
                    	String packageName = null;
						/*if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
							final Set<String> activePackages = new HashSet<String>();
							final List<ActivityManager.RunningAppProcessInfo> processInfos = activityManager
									.getRunningAppProcesses();
							for (ActivityManager.RunningAppProcessInfo processInfo : processInfos) {
								if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
									activePackages.addAll(Arrays.asList(processInfo.pkgList));
								}
							}
							activePackages.toArray(new String[activePackages.size()]);
							
						} else {
							List<RunningTaskInfo> runningTaskInfos = activityManager  
	                                .getRunningTasks(1);  
	                        RunningTaskInfo runningTaskInfo = runningTaskInfos  
	                                .get(0);  
	                        packageName = runningTaskInfo.topActivity  
	                                .getPackageName();
						}*/
                    	String[] activePackages = null;
						if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
							activePackages = getActivePackages();
						} else {
							activePackages = getActivePackagesCompat();
						}
                    	if(activePackages != null && activePackages.length > 0 ){
                    		packageName = activePackages[0];
                    	}
						if (isAppLocked(packageName)
								&& !unlockedAppList.contains(new App(
										packageName))) {
							intent.putExtra("packageName", packageName);
							startActivity(intent);
						} else {

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
  
    public void initPresetlockData() {
    	PresetLockDao presetLockDao = new PresetLockDao(this);
    	PresetLockAppDao presetLockAppDao = new PresetLockAppDao(this);
    	
    	PresetLock presetLock = presetLockDao.getPresetLock();
    	if( presetLock != null ){
    		presetLockStartTime = presetLock.getStartTime();
    		presetLockEndTime = presetLock.getEndTime();
    		presetLockOnOff = presetLock.getPresetOnOff();
    		
    		repeat = new boolean[]{
    				DataTypeUtil.getNonNullBoolean(presetLock.getRepeatMonday()),
    				DataTypeUtil.getNonNullBoolean(presetLock.getRepeatTuesday()),
    				DataTypeUtil.getNonNullBoolean(presetLock.getRepeatWednesday()),
    				DataTypeUtil.getNonNullBoolean(presetLock.getRepeatThurday()),
    				DataTypeUtil.getNonNullBoolean(presetLock.getRepeatFriday()),
    				DataTypeUtil.getNonNullBoolean(presetLock.getRepeatSaturday()),
    				DataTypeUtil.getNonNullBoolean(presetLock.getRepeatSunday())	
    		};
    		presetLockApp = presetLockAppDao.getPresetLockAppIdList();		
    	}
	}

	/*private boolean isAppLocked(String packageName) {
		App app = getAppDao().getByPackageName(packageName);
		
		if( app == null ){
			return false;
		}
		
    	if( appList.contains(new App(packageName)) || 
    			( presetLockOnOff == true && inPresetLockPeriod() &&  presetLockApp.contains(app.getId()) )){
			return true;
		}
		return false;
	}*/
    
    private boolean isAppLocked(String packageName) {
		App app = getAppDao().getByPackageName(packageName);
		
		if( app == null ){
			return false;
		}
		
    	if( appList.contains(new App(packageName)) || isLockedInPresetLock(app.getId()) ){
			return true;
		}
		return false;
	}
    
    private boolean isLockedInPresetLock(Integer appId) {
    	PresetLockDao presetLockDao = new PresetLockDao(this);
    	PresetLockAppDao presetLockAppDao = new PresetLockAppDao(this);
    	List<PresetLock> presetLockList = presetLockDao.listAll();
    	if( presetLockList != null ){
    		for(PresetLock presetLock:presetLockList){
    			
    			if( presetLock.getPresetOnOff().equals(true) && inPresetLockPeriod(presetLock) ){
    				List<PresetLockApp> appList = presetLockAppDao.listByPresetLockId(presetLock.getId());
        			
        			if( appList != null ){
        				for(PresetLockApp app:appList){
        					if( app.getAppId() != null && app.getAppId().equals(appId) ){
        						return true;
        					}
        				}
        			}
    			}
    			
    		}
    	}
    	
		return false;
	}
    
    private boolean inPresetLockPeriod(PresetLock presetLock) {
    	Date startTime = presetLock.getStartTime();
    	Date endTime = presetLock.getEndTime();
    	if( startTime == null || endTime == null ){
    		return false;
    	}
    	
    	Date now = new Date();
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(now);
  
    	int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
    	
    	if( inRepeatWeekdays(dayOfWeek,repeat) && betweenStartTimeAndEndTime(now,startTime,endTime) ){
    		return true;
    	}
    	
		return false;
	}

	private boolean inPresetLockPeriod() {
    	if( presetLockStartTime == null || presetLockEndTime == null ){
    		return false;
    	}
    	Date now = new Date();
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(now);
  
    	int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
    	
    	if( inRepeatWeekdays(dayOfWeek,repeat) && betweenStartTimeAndEndTime(now,presetLockStartTime,presetLockEndTime) ){
    		return true;
    	}
    	
		return false;
	}

	private boolean betweenStartTimeAndEndTime(Date now,
			Date startTime, Date endTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.set(Calendar.YEAR, 2000);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date _now = cal.getTime();
		
		cal.setTime(startTime);
		cal.set(Calendar.YEAR, 2000);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date _startTime = cal.getTime();
		
		cal.setTime(endTime);
		cal.set(Calendar.YEAR, 2000);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date _endTime = cal.getTime();
		
		if( _now.after(_startTime)&& _now.before(_endTime) ){
			return true;
		}
		
		return false;
	}

	private boolean inRepeatWeekdays(int dayOfWeek, boolean[] repeat) {
		switch(dayOfWeek){
		case Calendar.MONDAY:
			if( repeat[0] == true ){
				return true;
			}
			break;
		case Calendar.TUESDAY:
			if( repeat[1] == true ){
				return true;
			}
			break;
		case Calendar.WEDNESDAY:
			if( repeat[2] == true ){
				return true;
			}
			break;
		case Calendar.THURSDAY:
			if( repeat[3] == true ){
				return true;
			}
			break;
		case Calendar.FRIDAY:
			if( repeat[4] == true ){
				return true;
			}
			break;
		case Calendar.SATURDAY:
			if( repeat[5] == true ){
				return true;
			}
			break;
		case Calendar.SUNDAY:
			if( repeat[6] == true ){
				return true;
			}
			break;
		}
		
		return false;
	}

	private AppDao getAppDao() {
    	if( dao == null){
    		dao = new AppDao(this);
    		return dao;
    	}
    	
		return dao;
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
	
	
	String[] getActivePackagesCompat() {
		final List<ActivityManager.RunningTaskInfo> taskInfo = activityManager
				.getRunningTasks(1);
		final ComponentName componentName = taskInfo.get(0).topActivity;
		final String[] activePackages = new String[1];
		activePackages[0] = componentName.getPackageName();
		return activePackages;
	}

	String[] getActivePackages() {
		final List<String> activePackages = new ArrayList<String>();
		final List<ActivityManager.RunningAppProcessInfo> processInfos = activityManager
				.getRunningAppProcesses();
		for (ActivityManager.RunningAppProcessInfo processInfo : processInfos) {
			if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
				activePackages.addAll(Arrays.asList(processInfo.pkgList));
			}
		}
		return activePackages.toArray(new String[activePackages.size()]);
	}

}
