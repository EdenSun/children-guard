package eden.sun.childrenguard.child.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import eden.sun.childrenguard.child.db.model.ChildSetting;
import eden.sun.childrenguard.child.util.DataTypeUtil;
import eden.sun.childrenguard.server.dto.ChildSettingViewDTO;

public class ChildSettingDao extends BaseDao{
	
	private static final String TABLE_NAME = "TBL_CHILD_SETTING";

	public ChildSettingDao(Context context) {
		super(context);
	}
	
	public boolean batchAdd(List<ChildSettingViewDTO> settingList){
		if( settingList == null || settingList.size() == 0 ){
			return true;
		}
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if ( db != null && db.isOpen() ) {
			for(ChildSettingViewDTO settingView : settingList ){
		        ContentValues cv = new ContentValues();  
		        
		        cv.put("ID",  settingView.getId());
		        cv.put("LOCK_CALL_SWITCH",  settingView.getLockCallsSwitch());
		        cv.put("LOCK_TEXT_MESSAGE_SWITCH",  settingView.getLockTextMessageSwitch());
		        cv.put("WIFI_ONLY_SWITCH",  settingView.getWifiOnlySwitch());
		        cv.put("NEW_APP_NOTIFICATION_SWITCH",  settingView.getNewAppNotificationSwitch());
		        cv.put("UNINSTALL_APP_NOTIFICATION_SWITCH",  settingView.getUninstallAppNotificationSwitch());
		        cv.put("SPEEDING_NOTIFICATION_SWITCH",  settingView.getSpeedingNotificationSwitch());
		        cv.put("SPEEDING_LIMIT",  settingView.getSpeedingLimit());
		        cv.put("APP_LOCK_UNLOCK_NOTIFICATION",  settingView.getAppLockUnlockNotificationSwitch());
		        cv.put("APP_LOCK_PASSWORD",  settingView.getAppLockPassword());
		        
		        db.insert(TABLE_NAME, null, cv);  
			}
			
			db.close();
			return true;
		}
		return false;
	}
	

	public String getAppLockPassword() {
		ChildSetting childSetting = getChildSetting();
		if( childSetting != null ){
			return childSetting.getAppLockPassword();
		}
		return null;
	}
	
	/*public ChildSetting getChildSetting(){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if ( db != null && db.isOpen() ) {
			ChildSetting childSetting = null;
			Cursor cursor = db.rawQuery("select ID,LOCK_CALL_SWITCH,LOCK_TEXT_MESSAGE_SWITCH,WIFI_ONLY_SWITCH,NEW_APP_NOTIFICATION_SWITCH,UNINSTALL_APP_NOTIFICATION_SWITCH,SPEEDING_NOTIFICATION_SWITCH,SPEEDING_LIMIT,APP_LOCK_UNLOCK_NOTIFICATION,APP_LOCK_PASSWORD from " + TABLE_NAME,
										new String[] {});
			
			if(cursor.moveToNext()) {
				childSetting = new ChildSetting();
				
				int id = cursor.getInt(0);
				String lockCallsSwitch = cursor.getString(1);
				String lockTextMessageSwitch = cursor.getString(2);
				String wifiOnlySwitch = cursor.getString(3);
				String newAppNotificationSwitch = cursor.getString(4);
				String uninstallAppNotificationSwitch = cursor.getString(5);
				String speedingNotificationSwitch = cursor.getString(6);
				int speedingLimit = cursor.getInt(7);
				String appLockUnlockNotificationSwitch = cursor.getString(8);
				String appLockPassword = cursor.getString(9);
				
				childSetting.setId(id);
				childSetting.setLockCallsSwitch(DataTypeUtil.stringToBoolean(lockCallsSwitch));
				childSetting.setLockTextMessageSwitch(DataTypeUtil.stringToBoolean(lockTextMessageSwitch));
				childSetting.setWifiOnlySwitch(DataTypeUtil.stringToBoolean(wifiOnlySwitch));
				childSetting.setNewAppNotificationSwitch(DataTypeUtil.stringToBoolean(newAppNotificationSwitch));
				childSetting.setUninstallAppNotificationSwitch(DataTypeUtil.stringToBoolean(uninstallAppNotificationSwitch));
				childSetting.setSpeedingNotificationSwitch(DataTypeUtil.stringToBoolean(speedingNotificationSwitch));
				childSetting.setSpeedingLimit(speedingLimit);
				childSetting.setAppLockUnlockNotificationSwitch(DataTypeUtil.stringToBoolean(appLockUnlockNotificationSwitch));
				childSetting.setAppLockPassword(appLockPassword);
			} 
			
			cursor.close();
			db.close();
			return childSetting;
		}
		
		return null;
	}
	*/
	
	public ChildSetting getChildSetting(){
		List<ChildSetting> childSettingList = this.listAll();
		
		if( childSettingList != null && childSettingList.size() > 0 ){
			return childSettingList.get(0);
		}
		return null;
	}
	
	public boolean add(ChildSettingViewDTO childSettingView){
		if( childSettingView == null ){
			return false;
		}
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if ( db != null && db.isOpen() ) {
			db.execSQL("insert into " + TABLE_NAME + " (ID,LOCK_CALL_SWITCH,LOCK_TEXT_MESSAGE_SWITCH,WIFI_ONLY_SWITCH,NEW_APP_NOTIFICATION_SWITCH,UNINSTALL_APP_NOTIFICATION_SWITCH,SPEEDING_NOTIFICATION_SWITCH,SPEEDING_LIMIT,APP_LOCK_UNLOCK_NOTIFICATION,APP_LOCK_PASSWORD) values (?,?,?,?,?,?,?,?,?,?)",
						new Object[] { 
					childSettingView.getId(),
					childSettingView.getLockCallsSwitch(),
					childSettingView.getLockTextMessageSwitch(),
					childSettingView.getWifiOnlySwitch(),
					childSettingView.getNewAppNotificationSwitch(),
					childSettingView.getUninstallAppNotificationSwitch(),
					childSettingView.getSpeedingNotificationSwitch(),
					childSettingView.getSpeedingLimit(),
					childSettingView.getAppLockUnlockNotificationSwitch(),
					childSettingView.getAppLockPassword()});
			
			db.close();
			return true;
		}
		
		return false;
	}
	
	public boolean delete(Integer childSettingId){
		if( childSettingId == null ){
			return false;
		}
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if ( db != null && db.isOpen() ) {
			
			db.execSQL("delete from " + TABLE_NAME + " where ID = ?",
						new Object[] { childSettingId });
			
			db.close();
			return true;
		}
		
		return false;
	}
	
	public boolean update(ChildSettingViewDTO childSettingView){
		if( childSettingView == null ){
			return false;
		}
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if ( db != null && db.isOpen() ) {
			
			db.execSQL("update " + TABLE_NAME + " set LOCK_CALL_SWITCH=?,LOCK_TEXT_MESSAGE_SWITCH=?,WIFI_ONLY_SWITCH=?,NEW_APP_NOTIFICATION_SWITCH=?,UNINSTALL_APP_NOTIFICATION_SWITCH=?,SPEEDING_NOTIFICATION_SWITCH=?,SPEEDING_LIMIT=?,APP_LOCK_UNLOCK_NOTIFICATION=?,APP_LOCK_PASSWORD=? where ID = ?",
						new Object[] { 
							childSettingView.getLockCallsSwitch(),
							childSettingView.getLockTextMessageSwitch(),
							childSettingView.getWifiOnlySwitch(),
							childSettingView.getNewAppNotificationSwitch(),
							childSettingView.getUninstallAppNotificationSwitch(),
							childSettingView.getSpeedingNotificationSwitch(),
							childSettingView.getSpeedingLimit(),
							childSettingView.getAppLockUnlockNotificationSwitch(),
							childSettingView.getAppLockPassword(), 
							childSettingView.getId()});
			
			db.close();
			return true;
		}
		
		return false;
	}
	
	
	public void clearAll() {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if ( db != null && db.isOpen() ) {
			
			db.execSQL("delete from " + TABLE_NAME);
			
			db.close();
		}
	}

	public List<ChildSetting> listAll() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if ( db != null && db.isOpen() ) {
			ChildSetting childSetting = null;
			Cursor cursor = db.rawQuery("select ID,LOCK_CALL_SWITCH,LOCK_TEXT_MESSAGE_SWITCH,WIFI_ONLY_SWITCH,NEW_APP_NOTIFICATION_SWITCH,UNINSTALL_APP_NOTIFICATION_SWITCH,SPEEDING_NOTIFICATION_SWITCH,SPEEDING_LIMIT,APP_LOCK_UNLOCK_NOTIFICATION,APP_LOCK_PASSWORD from " + TABLE_NAME,
					new String[] {});
			List<ChildSetting> childSettingList = new ArrayList<ChildSetting>();
			
			while(cursor.moveToNext()) {
				childSetting = new ChildSetting();
				
				int id = cursor.getInt(0);
				String lockCallsSwitch = cursor.getString(1);
				String lockTextMessageSwitch = cursor.getString(2);
				String wifiOnlySwitch = cursor.getString(3);
				String newAppNotificationSwitch = cursor.getString(4);
				String uninstallAppNotificationSwitch = cursor.getString(5);
				String speedingNotificationSwitch = cursor.getString(6);
				int speedingLimit = cursor.getInt(7);
				String appLockUnlockNotificationSwitch = cursor.getString(8);
				String appLockPassword = cursor.getString(9);
				
				childSetting.setId(id);
				childSetting.setLockCallsSwitch(DataTypeUtil.stringToBoolean(lockCallsSwitch));
				childSetting.setLockTextMessageSwitch(DataTypeUtil.stringToBoolean(lockTextMessageSwitch));
				childSetting.setWifiOnlySwitch(DataTypeUtil.stringToBoolean(wifiOnlySwitch));
				childSetting.setNewAppNotificationSwitch(DataTypeUtil.stringToBoolean(newAppNotificationSwitch));
				childSetting.setUninstallAppNotificationSwitch(DataTypeUtil.stringToBoolean(uninstallAppNotificationSwitch));
				childSetting.setSpeedingNotificationSwitch(DataTypeUtil.stringToBoolean(speedingNotificationSwitch));
				childSetting.setSpeedingLimit(speedingLimit);
				childSetting.setAppLockUnlockNotificationSwitch(DataTypeUtil.stringToBoolean(appLockUnlockNotificationSwitch));
				childSetting.setAppLockPassword(appLockPassword);
				childSettingList.add(childSetting);
			} 
			
			cursor.close();
			db.close();
			return childSettingList;
		}
		return null;
	}

	public void addOrUpdate(ChildSettingViewDTO childSettingView) {
		ChildSetting childSetting = this.getChildSetting();
		if( childSetting == null ){
			// insert
			this.add(childSettingView);
		}else{
			// update
			this.update(childSettingView);
		}
	}
}
