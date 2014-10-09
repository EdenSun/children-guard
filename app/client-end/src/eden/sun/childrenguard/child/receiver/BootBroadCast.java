package eden.sun.childrenguard.child.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import eden.sun.childrenguard.child.service.MainService;

public class BootBroadCast extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		 /*  
         * 开机启动服务*  
         */  
		Intent service=new Intent(context, MainService.class);  
        context.startService(service);  
        
        /*  
         * 开机启动的Activity*  
         * Intent activity=new Intent(context,MyActivity.class);  
         * activity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );//不加此句会报错。  
         * context.startActivity(activity);  
         */  
  
        /* 开机启动的应用 */  
        /*Intent appli = context.getPackageManager().getLaunchIntentForPackage("com.test");  
        context.startActivity(appli);  */
	}

}
