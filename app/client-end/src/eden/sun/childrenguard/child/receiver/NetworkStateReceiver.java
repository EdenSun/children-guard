package eden.sun.childrenguard.child.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;
import eden.sun.childrenguard.child.db.dao.ChildSettingDao;
import eden.sun.childrenguard.child.db.model.ChildSetting;
import eden.sun.childrenguard.child.util.NetworkManager;

public class NetworkStateReceiver extends BroadcastReceiver {
	@Override  
    public void onReceive(Context context, Intent intent) {  
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);  
        NetworkInfo mobileInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);  
        //NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);  
        //NetworkInfo activeInfo = manager.getActiveNetworkInfo();  
        
        if( mobileInfo.isConnected() ){
        	ChildSettingDao childSettingDao = new ChildSettingDao(context);
        	
        	ChildSetting childSetting = childSettingDao.getChildSetting();
        	if( childSetting != null && childSetting.getWifiOnlySwitch() != null 
        			&& childSetting.getWifiOnlySwitch().booleanValue() == true ){
        		NetworkManager networkManager = new NetworkManager(context);
        		try {
					networkManager.toggleGprs(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
        		Toast.makeText(context, "You can only connect to network using wifi.", 3000).show();
        	}
        	
        }
    }  
}
