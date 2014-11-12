package eden.sun.childrenguard.child.receiver;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import eden.sun.childrenguard.child.activity.AbortCallActivity;
import eden.sun.childrenguard.child.db.dao.ChildSettingDao;
import eden.sun.childrenguard.child.db.dao.EmergencyContactsDao;
import eden.sun.childrenguard.child.db.model.EmergencyContact;

public class OutgoingCallReceiver extends BroadcastReceiver {
	private List<EmergencyContact> emergencyContactList;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		String PhoneNum = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);

		
		
		if ( !isOutgoingCallAllowed(context) ) {
			EmergencyContactsDao emergencyContactsDao = new EmergencyContactsDao(context);
			emergencyContactList = emergencyContactsDao.listAll();
			
			setResultData(null);
			intent.setClass(context, AbortCallActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra("num", PhoneNum);
			context.startActivity(intent);
		}
		settings.edit().putBoolean("IsAllowed", false).commit();
		this.clearAbortBroadcast();
	}

	private boolean isOutgoingCallAllowed(Context context) {
		ChildSettingDao settingDao = new ChildSettingDao(context);
		settingDao.getLockCallSwitch();
		
		return false;
	}

}
