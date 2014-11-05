package eden.sun.childrenguard.child.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import eden.sun.childrenguard.child.dto.AppInfo;

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
			AppInfo appdto =new AppInfo(); 
			String appName = packageInfo.applicationInfo.loadLabel(packageManager).toString(); 
			String packageName = packageInfo.packageName; 
			String versionName = packageInfo.versionName; 
			int versionCode = packageInfo.versionCode; 
			
			appdto.setAppName(appName);
			appdto.setPackageName(packageName);
			appdto.setVersionName(versionName);
			appdto.setVersonCode(versionCode);
			appdto.setSysApp(isSysApp(packageInfo.applicationInfo));
			
			dtoList.add(appdto);
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
}
