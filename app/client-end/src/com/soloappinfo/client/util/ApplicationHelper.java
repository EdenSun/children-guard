package com.soloappinfo.client.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;

import com.soloappinfo.client.dto.AppInfo;

public class ApplicationHelper {

	private PackageManager packageManager;  
    
    public ApplicationHelper(Context context)  
    {  
        packageManager = context.getPackageManager();  
    }  
  
	public static List<AppInfo> listAllApplication(PackageManager packageManager){
		List<AppInfo> dtoList = new ArrayList<AppInfo>();
		List<ApplicationInfo> apps = packageManager.getInstalledApplications(0);
		List<PackageInfo> packages = packageManager.getInstalledPackages(0);
		
		for(PackageInfo packageInfo :packages ){
			if( !isSysApp(packageInfo.applicationInfo) ){
				// ONLY add non-system app
				AppInfo appdto =new AppInfo(); 
				String appName = packageInfo.applicationInfo.loadLabel(packageManager).toString(); 
				String packageName = packageInfo.packageName; 
				String versionName = packageInfo.versionName; 
				int versionCode = packageInfo.versionCode; 
				
				appdto.setAppName(appName);
				appdto.setPackageName(packageName);
				appdto.setVersionName(versionName);
				appdto.setVersonCode(versionCode);
				//appdto.setSysApp(isSysApp(packageInfo.applicationInfo));
				
				dtoList.add(appdto);
			}
		}
		return dtoList;
	}
	
	
	public static boolean isSysApp(ApplicationInfo info)  
    {  
		int flags = info.flags;
		
		if((flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) == 1) {
			//it's a system app, not interested
			return true;
	    } else if ((flags & ApplicationInfo.FLAG_SYSTEM) == 1) {
	        //Discard this one
	    	//in this case, it should be a user-installed app
	    	return true;
	    } else {
	    	return false;
	    }
    }

	public static String getPhoneNumber(Context context) {
		TelephonyManager phoneMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		String childMobile = phoneMgr.getLine1Number();
		if( childMobile == null ){
			return null;
		}else{
			return childMobile;
		}
	}  
	
	
	public static String getAppNameByPackage(Context context,String packageName){
		final PackageManager pm = context.getPackageManager();
		ApplicationInfo ai;
		try {
		    ai = pm.getApplicationInfo( packageName, 0);
		} catch (final NameNotFoundException e) {
		    ai = null;
		}
		String applicationName = (String) (ai != null ? pm.getApplicationLabel(ai) : "(unknown)");
		return applicationName;
	}
}
