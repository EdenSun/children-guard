package eden.sun.childrenguard.child.receiver;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import eden.sun.childrenguard.child.activity.AbortCallActivity;
import eden.sun.childrenguard.child.db.dao.ChildSettingDao;
import eden.sun.childrenguard.child.db.dao.EmergencyContactsDao;
import eden.sun.childrenguard.child.db.model.EmergencyContact;

public class OutgoingCallReceiver extends BroadcastReceiver {
	private static final String TAG = "OutgoingCallReceiver";
	private List<EmergencyContact> emergencyContactList;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);

		Log.d(TAG, "Outgoing call received:" + phoneNumber);
		if ( !isOutgoingCallAllowed(context) ) {
			EmergencyContactsDao emergencyContactsDao = new EmergencyContactsDao(context);
			emergencyContactList = emergencyContactsDao.listAll();
			
			if( !emergencyContactList.contains(new EmergencyContact(phoneNumber)) ){
				setResultData(null);
				intent.setClass(context, AbortCallActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("phoneNumber", phoneNumber);
				context.startActivity(intent);
			}
			
		}
		this.clearAbortBroadcast();
	}

	private boolean isOutgoingCallAllowed(Context context) {
		ChildSettingDao settingDao = new ChildSettingDao(context);
		
		Boolean lockCallSwitch = settingDao.getLockCallSwitch();
		if( lockCallSwitch != null && lockCallSwitch.booleanValue() == true){
			return false;
		}
		
		return true;
	}

}
