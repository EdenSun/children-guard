package eden.sun.childrenguard.helper;

import android.content.Context;
import android.telephony.TelephonyManager;

public class DeviceHelper {
	public static String getIMEI(Context context){
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);  
		String imei =  telephonyManager.getDeviceId();  
		return imei;
	}
}
