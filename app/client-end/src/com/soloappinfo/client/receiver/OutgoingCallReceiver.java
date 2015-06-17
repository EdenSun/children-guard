package com.soloappinfo.client.receiver;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.soloappinfo.client.activity.AbortCallActivity;
import com.soloappinfo.client.db.dao.ChildSettingDao;
import com.soloappinfo.client.db.dao.EmergencyContactsDao;
import com.soloappinfo.client.db.dao.PresetLockDao;
import com.soloappinfo.client.db.model.EmergencyContact;
import com.soloappinfo.client.db.model.PresetLock;
import com.soloappinfo.client.util.DataTypeUtil;

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
		PresetLockDao presetLockDao = new PresetLockDao(context);
		
		//PresetLock presetLock = presetLockDao.getPresetLock();
		List<PresetLock> presetLockList = presetLockDao.listAll();
		Boolean lockCallSwitch = settingDao.getLockCallSwitch();
		
		if( isOutgoingCallAllowed(lockCallSwitch,presetLockList) ){
			return true;
		}
		/*if( ( lockCallSwitch != null && lockCallSwitch.booleanValue() == true ) 
				|| ( DataTypeUtil.getNonNullBoolean(presetLock.getPresetOnOff()) == true && inPresetLockPeriod(presetLock) && DataTypeUtil.getNonNullBoolean(presetLock.getLockCallStatus()) == true) ){
			return false;
		}*/
		
		return false;
	}
	
	private boolean isOutgoingCallAllowed(Boolean lockCallSwitch,
			List<PresetLock> presetLockList) {
		boolean isOutgoingCallBlockInPresetLock = false;
		for(PresetLock presetLock : presetLockList){
			if( presetLock.getPresetOnOff() != null && presetLock.getPresetOnOff().equals(true) && inPresetLockPeriod(presetLock) ){
				if( presetLock.getLockCallStatus() != null && presetLock.getLockCallStatus().equals(true) ){
					isOutgoingCallBlockInPresetLock = true;
					break;
				}
			}
		}
		
		if( ( lockCallSwitch != null && lockCallSwitch.booleanValue() == true ) 
				|| isOutgoingCallBlockInPresetLock ){
			return false;
		}else{
			return true;
		}
	}

	private boolean inPresetLockPeriod(PresetLock presetLock) {
    	if( presetLock.getStartTime() == null || presetLock.getEndTime() == null ){
    		return false;
    	}
    	boolean[] repeat = new boolean[]{
			DataTypeUtil.getNonNullBoolean(presetLock.getRepeatMonday()),
			DataTypeUtil.getNonNullBoolean(presetLock.getRepeatTuesday()),
			DataTypeUtil.getNonNullBoolean(presetLock.getRepeatWednesday()),
			DataTypeUtil.getNonNullBoolean(presetLock.getRepeatThurday()),
			DataTypeUtil.getNonNullBoolean(presetLock.getRepeatFriday()),
			DataTypeUtil.getNonNullBoolean(presetLock.getRepeatSaturday()),
			DataTypeUtil.getNonNullBoolean(presetLock.getRepeatSunday())	
    	};
    	Date now = new Date();
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(now);
  
    	int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
    	
    	if( inRepeatWeekdays(dayOfWeek,repeat) && betweenStartTimeAndEndTime(now,presetLock.getStartTime(),presetLock.getEndTime()) ){
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
}
