package com.soloappinfo.client.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.soloappinfo.client.service.LocationMonitorService;

public class BootBroadCast extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		 /*  
         * ������������*  
         */  
		Intent service=new Intent(context, LocationMonitorService.class);  
        context.startService(service);  
        
        /*  
         * ����������Activity*  
         * Intent activity=new Intent(context,MyActivity.class);  
         * activity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );//���Ӵ˾�ᱨ��  
         * context.startActivity(activity);  
         */  
  
        /* ����������Ӧ�� */  
        /*Intent appli = context.getPackageManager().getLaunchIntentForPackage("com.test");  
        context.startActivity(appli);  */
	}

}
