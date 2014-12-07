package eden.sun.childrenguard.child.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import eden.sun.childrenguard.child.db.model.PresetLock;
import eden.sun.childrenguard.child.util.DataTypeUtil;
import eden.sun.childrenguard.child.util.DateUtil;
import eden.sun.childrenguard.server.dto.PresetLockViewDTO;

public class PresetLockDao extends BaseDao{
	private static final String TAG = "PresetLockDao";
	private static final String TABLE_NAME = "TBL_PRESET_LOCK";

	public PresetLockDao(Context context) {
		super(context);
	}
	
	public boolean add(PresetLockViewDTO presetLockView){
		if( presetLockView == null ){
			return false;
		}
		List<Boolean> repeat = presetLockView.getRepeat();
		if( repeat == null ){
			Log.e(TAG, "add error,repeat is null.");
			return false;
		}
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if ( db != null && db.isOpen() ) {
			
			db.execSQL("insert into " + TABLE_NAME + "(ID,PRESET_ON_OFF,START_TIME,END_TIME,REPEAT_MONDAY,REPEAT_TUESDAY,REPEAT_WEDNESDAY,REPEAT_THURDAY,REPEAT_FRIDAY,REPEAT_SATURDAY,REPEAT_SUNDAY,LOCK_CALL_STATUS) values (?,?,?,?,?,?,?,?,?,?,?,?)",
						new Object[] { 
					presetLockView.getId(),
					DataTypeUtil.getNonNullBoolean(presetLockView.getPresetOnOff()),
					DateUtil.dateToString(presetLockView.getStartTime()),
					DateUtil.dateToString(presetLockView.getEndTime()),
					DataTypeUtil.getNonNullBoolean(repeat.get(0)),
					DataTypeUtil.getNonNullBoolean(repeat.get(1)),
					DataTypeUtil.getNonNullBoolean(repeat.get(2)),
					DataTypeUtil.getNonNullBoolean(repeat.get(3)),
					DataTypeUtil.getNonNullBoolean(repeat.get(4)),
					DataTypeUtil.getNonNullBoolean(repeat.get(5)),
					DataTypeUtil.getNonNullBoolean(repeat.get(6)),
					DataTypeUtil.getNonNullBoolean(presetLockView.getLockCallStatus())
					});
			
			db.close();
			return true;
		}
		
		return false;
	}
	
	public boolean delete(Integer presetLockId){
		if( presetLockId == null ){
			return false;
		}
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if ( db != null && db.isOpen() ) {
			
			db.execSQL("delete from " + TABLE_NAME + " where ID = ?",
						new Object[] { presetLockId });
			
			db.close();
			return true;
		}
		
		return false;
	}
	
	public boolean update(PresetLockViewDTO presetLockView){
		if( presetLockView == null ){
			return false;
		}
		
		List<Boolean> repeat = presetLockView.getRepeat();
		if( repeat == null ){
			Log.e(TAG, "add error,repeat is null.");
			return false;
		}
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if ( db != null && db.isOpen() ) {
			
			db.execSQL("update " + TABLE_NAME + " set PRESET_ON_OFF=?,START_TIME=?,END_TIME=?,REPEAT_MONDAY=?,REPEAT_TUESDAY=?,REPEAT_WEDNESDAY=?,REPEAT_THURDAY=?,REPEAT_FRIDAY=?,REPEAT_SATURDAY=?,REPEAT_SUNDAY=?,LOCK_CALL_STATUS=? where ID = ?",
						new Object[] { 
							DataTypeUtil.getNonNullBoolean(presetLockView.getPresetOnOff()),
							DateUtil.dateToString(presetLockView.getStartTime()),
							DateUtil.dateToString(presetLockView.getEndTime()),
							DataTypeUtil.getNonNullBoolean(repeat.get(0)),
							DataTypeUtil.getNonNullBoolean(repeat.get(1)),
							DataTypeUtil.getNonNullBoolean(repeat.get(2)),
							DataTypeUtil.getNonNullBoolean(repeat.get(3)),
							DataTypeUtil.getNonNullBoolean(repeat.get(4)),
							DataTypeUtil.getNonNullBoolean(repeat.get(5)),
							DataTypeUtil.getNonNullBoolean(repeat.get(6)),
							DataTypeUtil.getNonNullBoolean(presetLockView.getLockCallStatus()), 
							presetLockView.getId().toString()});
			
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

	public List<PresetLock> listAll() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if ( db != null && db.isOpen() ) {
			PresetLock presetLock = null;
			Cursor cursor = db.rawQuery("select ID,PRESET_ON_OFF,START_TIME,END_TIME,REPEAT_MONDAY,REPEAT_TUESDAY,REPEAT_WEDNESDAY,REPEAT_THURDAY,REPEAT_FRIDAY,REPEAT_SATURDAY,REPEAT_SUNDAY,LOCK_CALL_STATUS from " + TABLE_NAME,
					new String[] {});
			List<PresetLock> presetLockList = new ArrayList<PresetLock>();
			
			while(cursor.moveToNext()) {
				presetLock = new PresetLock();
				
				int id = cursor.getInt(0);
				String presetOnOff = cursor.getString(1);
				String startTime = cursor.getString(2);
				String endTime = cursor.getString(3);
				String repeatMonday = cursor.getString(4);
				String repeatTuesday = cursor.getString(5);
				String repeatWednesday = cursor.getString(6);
				String repeatThurday = cursor.getString(7);
				String repeatFriday = cursor.getString(8);
				String repeatSaturday = cursor.getString(9);
				String repeatSunday = cursor.getString(10);
				String lockCallStatus = cursor.getString(11);
				
				presetLock.setId(id);
				presetLock.setPresetOnOff(DataTypeUtil.str2NonNullBoolean(presetOnOff));
				presetLock.setStartTime(DateUtil.str2Date(startTime));
				presetLock.setEndTime(DateUtil.str2Date(endTime));
				presetLock.setRepeatMonday(DataTypeUtil.str2NonNullBoolean(repeatMonday));
				presetLock.setRepeatTuesday(DataTypeUtil.str2NonNullBoolean(repeatTuesday));
				presetLock.setRepeatWednesday(DataTypeUtil.str2NonNullBoolean(repeatWednesday));
				presetLock.setRepeatThurday(DataTypeUtil.str2NonNullBoolean(repeatThurday));
				presetLock.setRepeatFriday(DataTypeUtil.str2NonNullBoolean(repeatFriday));
				presetLock.setRepeatSaturday(DataTypeUtil.str2NonNullBoolean(repeatSaturday));
				presetLock.setRepeatSunday(DataTypeUtil.str2NonNullBoolean(repeatSunday));
				presetLock.setLockCallStatus(DataTypeUtil.str2NonNullBoolean(lockCallStatus));
				
				presetLockList.add(presetLock);
			} 
			
			cursor.close();
			db.close();
			return presetLockList;
		}
		return null;
	}

	public PresetLock getPresetLock(){
		List<PresetLock> presetLockList = this.listAll();
		
		if( presetLockList != null && presetLockList.size() > 0 ){
			return presetLockList.get(0);
		}
		return null;
	}
	
	public void addOrUpdate(PresetLockViewDTO presetLockView) {
		PresetLock presetLock = this.getPresetLock();
		if( presetLock == null ){
			// insert
			this.add(presetLockView);
		}else{
			// update
			this.update(presetLockView);
		}
	}

}
