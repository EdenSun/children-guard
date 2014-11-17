package eden.sun.childrenguard.child.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import eden.sun.childrenguard.child.db.model.ChildInfo;
import eden.sun.childrenguard.child.util.DataTypeUtil;
import eden.sun.childrenguard.server.dto.ChildInfoViewDTO;

public class ChildInfoDao extends BaseDao{
	private static final String TAG = "ChildInfoDao";
	private static final String TABLE_NAME = "TBL_CHILD_INFO";
	
	public ChildInfoDao(Context context) {
		super(context);
	}
	
	public ChildInfo getChildInfo(){
		List<ChildInfo> childInfoList = this.listAll();
		
		if( childInfoList != null && childInfoList.size() > 0 ){
			return childInfoList.get(0);
		}
		return null;
	}
	
	public boolean add(ChildInfoViewDTO childInfoView){
		if( childInfoView == null ){
			return false;
		}
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if ( db != null && db.isOpen() ) {
			db.execSQL("insert into " + TABLE_NAME + " (ID,MOBILE,FIRST_NAME,LAST_NAME,NICKNAME,ACTIVATE_TIME,LONGITUDE,LATITUDE,LOCATION_UPDATE_TIME,SPEED,SPEED_UPDATE_TIME,NETWORK_TRAFFIC_USED,NETWORK_TRAFFIC_UPDATE_TIME) values (?,?,?,?,?,?,?,?,?,?,?,?,?)",
						new Object[] { 
					childInfoView.getId(),
					childInfoView.getMobile(),
					childInfoView.getFirstName(),
					childInfoView.getLastName(),
					childInfoView.getNickname(),
					childInfoView.getActivateTime(),
					childInfoView.getLongitude(),
					childInfoView.getLatitude(),
					childInfoView.getLocationUpdateTime(),
					childInfoView.getSpeed(),
					childInfoView.getSpeedUpdateTime(),
					childInfoView.getNetworkTrafficUsed(),
					childInfoView.getNetworkTrafficUpdateTime()});
			
			db.close();
			return true;
		}
		
		return false;
	}
	
	public boolean delete(Integer childInfoId){
		if( childInfoId == null ){
			return false;
		}
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if ( db != null && db.isOpen() ) {
			
			db.execSQL("delete from " + TABLE_NAME + " where ID = ?",
						new Object[] { childInfoId });
			
			db.close();
			return true;
		}
		
		return false;
	}
	
	public boolean update(ChildInfoViewDTO childInfoView){
		if( childInfoView == null ){
			return false;
		}
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if ( db != null && db.isOpen() ) {
			
			db.execSQL("update " + TABLE_NAME + " set MOBILE=?,FIRST_NAME=?,LAST_NAME=?,NICKNAME=?,ACTIVATE_TIME=?,LONGITUDE=?,LATITUDE=?,LOCATION_UPDATE_TIME=?,SPEED=?,SPEED_UPDATE_TIME=?,NETWORK_TRAFFIC_USED=?,NETWORK_TRAFFIC_UPDATE_TIME=? where ID = ?",
						new Object[] { 
							childInfoView.getMobile(),
							childInfoView.getFirstName(),
							childInfoView.getLastName(),
							childInfoView.getNickname(),
							childInfoView.getActivateTime(),
							childInfoView.getLongitude(),
							childInfoView.getLatitude(),
							childInfoView.getLocationUpdateTime(),
							childInfoView.getSpeed(),
							childInfoView.getSpeedUpdateTime(),
							childInfoView.getNetworkTrafficUsed(),
							childInfoView.getNetworkTrafficUpdateTime(),
							childInfoView.getId()});
			
			db.close();
			return true;
		}
		
		return false;
	}
	
	public boolean update(ChildInfo childInfo){
		if( childInfo == null && childInfo.getId() == null ){
			Log.i(TAG, "Update child info fail.");
			return false;
		}
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if ( db != null && db.isOpen() ) {
			
			db.execSQL("update " + TABLE_NAME + " set MOBILE=?,FIRST_NAME=?,LAST_NAME=?,NICKNAME=?,ACTIVATE_TIME=?,LONGITUDE=?,LATITUDE=?,LOCATION_UPDATE_TIME=?,SPEED=?,SPEED_UPDATE_TIME=?,NETWORK_TRAFFIC_USED=?,NETWORK_TRAFFIC_UPDATE_TIME=? where ID = ?",
						new Object[] { 
							childInfo.getMobile(),
							childInfo.getFirstName(),
							childInfo.getLastName(),
							childInfo.getNickname(),
							childInfo.getActivateTime(),
							childInfo.getLongitude(),
							childInfo.getLatitude(),
							childInfo.getLocationUpdateTime(),
							childInfo.getSpeed(),
							childInfo.getSpeedUpdateTime(),
							childInfo.getNetworkTrafficUsed(),
							childInfo.getNetworkTrafficUpdateTime(),
							childInfo.getId()});
			
			db.close();
			
			Log.i(TAG, "Update child info success.");
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

	public List<ChildInfo> listAll() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if ( db != null && db.isOpen() ) {
			ChildInfo childInfo = null;
			Cursor cursor = db.rawQuery("select ID,MOBILE,FIRST_NAME,LAST_NAME,NICKNAME,ACTIVATE_TIME,LONGITUDE,LATITUDE,LOCATION_UPDATE_TIME,SPEED,SPEED_UPDATE_TIME,NETWORK_TRAFFIC_USED,NETWORK_TRAFFIC_UPDATE_TIME from " + TABLE_NAME,
					new String[] {});
			List<ChildInfo> childInfoList = new ArrayList<ChildInfo>();
			
			while(cursor.moveToNext()) {
				childInfo = cursorToModel(cursor);
				
				childInfoList.add(childInfo);
			} 
			
			cursor.close();
			db.close();
			return childInfoList;
		}
		return null;
	}

	private ChildInfo cursorToModel(Cursor cursor) {
		ChildInfo childInfo = new ChildInfo();
		
		int id = cursor.getInt(0);
		String mobile = cursor.getString(1);
		String firstName = cursor.getString(2);
		String lastName = cursor.getString(3);
		String nickname = cursor.getString(4);
		String activateTime = cursor.getString(5);
		double longitude = cursor.getDouble(6);
		double latitude = cursor.getDouble(7);
		String locationUpdateTime = cursor.getString(8);
		double speed = cursor.getDouble(9);
		String speedUpdateTime = cursor.getString(10);
		double networkTrafficUsed = cursor.getDouble(11);
		String networkTrafficUpdateTime = cursor.getString(12);
		
		
		childInfo.setId(id);
		childInfo.setMobile(mobile);
		childInfo.setFirstName(firstName);
		childInfo.setLastName(lastName);
		childInfo.setNickname(nickname);
		childInfo.setActivateTime(DataTypeUtil.stringToDate(activateTime));
		childInfo.setLongitude(longitude);
		childInfo.setLatitude(latitude);
		childInfo.setLocationUpdateTime(DataTypeUtil.stringToDate(locationUpdateTime));
		childInfo.setSpeed(speed);
		childInfo.setSpeedUpdateTime(DataTypeUtil.stringToDate(speedUpdateTime));
		childInfo.setNetworkTrafficUsed(networkTrafficUsed);
		childInfo.setNetworkTrafficUpdateTime(DataTypeUtil.stringToDate(networkTrafficUpdateTime));
		
		return childInfo;
	}

	public void addOrUpdate(ChildInfoViewDTO childInfoView) {
		ChildInfo childInfo = this.getChildInfo();
		if( childInfo == null ){
			// insert
			this.add(childInfoView);
		}else{
			// update
			this.update(childInfoView);
		}
	}
}
