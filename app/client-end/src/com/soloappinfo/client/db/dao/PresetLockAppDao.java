package com.soloappinfo.client.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.soloappinfo.client.db.model.PresetLockApp;

public class PresetLockAppDao extends BaseDao{

	private static final String TAG = "PresetLockAppDao";
	private static final String TABLE_NAME = "TBL_PRESET_LOCK_APP";

	public PresetLockAppDao(Context context) {
		super(context);
	}
	
	public boolean batchAdd(List<PresetLockApp> appList){
		if( appList == null || appList.size() == 0 ){
			return true;
		}
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if ( db != null && db.isOpen() ) {
			for(PresetLockApp appView : appList ){
		        ContentValues cv = new ContentValues();  
		        cv.put("ID",  appView.getId());  
		        cv.put("PRESET_LOCK_ID", appView.getPresetLockId());  
		        cv.put("APP_ID", appView.getAppId());  
		        db.insert(TABLE_NAME, null, cv);  
			}
			
			db.close();
			return true;
		}
		return false;
	}
	
	
	public boolean add(PresetLockApp presetLockApp){
		if( presetLockApp == null ){
			return false;
		}
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if ( db != null && db.isOpen() ) {
			db.execSQL("insert into "+ TABLE_NAME + " (ID,PRESET_LOCK_ID,APP_ID) values (?,?,?)",
						new Object[] { presetLockApp.getId(),presetLockApp.getPresetLockId(),presetLockApp.getAppId()});
			
			db.close();
			return true;
		}
		
		return false;
	}
	
	public boolean delete(Integer appId){
		if( appId == null ){
			return false;
		}
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if ( db != null && db.isOpen() ) {
			
			db.execSQL("delete from "  + TABLE_NAME + " where ID = ?",
						new Object[] { appId });
			
			db.close();
			return true;
		}
		
		return false;
	}
	
	public boolean update(PresetLockApp presetLockApp){
		if( presetLockApp == null ){
			return false;
		}
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if ( db != null && db.isOpen() ) {
			
			db.execSQL("update " + TABLE_NAME + " set PRESET_LOCK_ID=?,APP_ID=? where ID = ?",
						new Object[] { presetLockApp.getPresetLockId(),presetLockApp.getAppId(), presetLockApp.getId()});
			
			db.close();
			return true;
		}
		
		return false;
	}
	
	/*public App getById(Integer appId){
		if( appId == null ){
			return null;
		}
		
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if ( db != null && db.isOpen() ) {
			App app = null;
			Cursor cursor = db.rawQuery("select ID,NAME,PACKAGE_NAME,LOCK_STATUS from TBL_APP where ID = ?",
										new String[] { appId.toString() });
			
			if(cursor.moveToNext()) { 
				app = new App();
			    int id = cursor.getInt(0);
			    String name = cursor.getString(1);
			    String packageName = cursor.getString(2);
			    String lockStatus = cursor.getString(3);//获取第三列的值 
			
			    app.setId(id);
			    app.setName(name);
			    app.setPackageName(packageName);
			    if( lockStatus != null && lockStatus.equals("true") ){
			    	app.setLockStatus(true);
			    }else{
			    	app.setLockStatus(false);
			    }
			} 
			
			cursor.close();
			db.close();
			return app;
		}
		
		return null;
	}
*/
	public void clearAll() {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if ( db != null && db.isOpen() ) {
			
			db.execSQL("delete from " + TABLE_NAME);
			
			db.close();
		}
	}

	public List<PresetLockApp> listAll() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if ( db != null && db.isOpen() ) {
			PresetLockApp app = null;
			Cursor cursor = db.rawQuery("select ID,PRESET_LOCK_ID,APP_ID from " + TABLE_NAME ,new String[]{});
			List<PresetLockApp> appList = new ArrayList<PresetLockApp>();
			
			while(cursor.moveToNext()) { 
				app = new PresetLockApp();
			    int id = cursor.getInt(0);
			    int presetLockId = cursor.getInt(1);
			    int appId = cursor.getInt(2);
			
			    app.setId(id);
			    app.setPresetLockId(presetLockId);
			    app.setAppId(appId);
			    appList.add(app);
			} 
			
			cursor.close();
			db.close();
			return appList;
		}
		return null;
	}

	public List<Integer> getPresetLockAppIdList() {
		List<PresetLockApp> list = this.listAll();
		if( list != null ){
			List<Integer> appIdList = new ArrayList<Integer>();
			
			for(PresetLockApp presetLockApp: list){
				appIdList.add(presetLockApp.getAppId());
			}
			
			return appIdList;
		}
		return null;
	}

	public List<PresetLockApp> listByPresetLockId(Integer _presetLockId) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if ( db != null && db.isOpen() ) {
			PresetLockApp app = null;
			Cursor cursor = db.rawQuery(
					"select ID,PRESET_LOCK_ID,APP_ID from " + TABLE_NAME + " where PRESET_LOCK_ID=?",
					new String[]{_presetLockId.toString()});
			List<PresetLockApp> appList = new ArrayList<PresetLockApp>();
			
			while(cursor.moveToNext()) { 
				app = new PresetLockApp();
			    int id = cursor.getInt(0);
			    int presetLockId = cursor.getInt(1);
			    int appId = cursor.getInt(2);
			
			    app.setId(id);
			    app.setPresetLockId(presetLockId);
			    app.setAppId(appId);
			    appList.add(app);
			} 
			
			cursor.close();
			db.close();
			return appList;
		}
		return null;
	}

	/*public void addOrUpdate(AppViewDTO appView) {
		App app = getById(appView.getId());
		if( app == null ){
			// insert
			this.add(appView);
		}else{
			// update
			this.update(appView);
		}
	}*/

}
