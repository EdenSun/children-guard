package com.soloappinfo.client.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.soloappinfo.client.db.model.App;

import eden.sun.childrenguard.server.dto.AppViewDTO;

public class AppDao extends BaseDao{

	private static final String TAG = "AppDao";

	public AppDao(Context context) {
		super(context);
	}
	
	public boolean batchAdd(List<AppViewDTO> appList){
		if( appList == null || appList.size() == 0 ){
			return true;
		}
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if ( db != null && db.isOpen() ) {
			for(AppViewDTO appView : appList ){
		        ContentValues cv = new ContentValues();  
		        cv.put("ID",  appView.getId());  
		        cv.put("NAME", appView.getName());  
		        cv.put("PACKAGE_NAME", appView.getPackageName());  
		        cv.put("LOCK_STATUS", appView.getLockStatus()==null? "false": appView.getLockStatus().toString());  
		        db.insert("TBL_APP", null, cv);  
			}
			
			db.close();
			return true;
		}
		return false;
	}
	
	
	public boolean add(AppViewDTO appView){
		if( appView == null ){
			return false;
		}
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if ( db != null && db.isOpen() ) {
			db.execSQL("insert into TBL_APP (ID,NAME,PACKAGE_NAME,LOCK_STATUS) values (?,?,?,?)",
						new Object[] { appView.getId(),appView.getName(),appView.getPackageName(),appView.getLockStatus() });
			
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
			
			db.execSQL("delete from TBL_APP where ID = ?",
						new Object[] { appId });
			
			db.close();
			return true;
		}
		
		return false;
	}
	
	public boolean update(AppViewDTO appView){
		if( appView == null ){
			return false;
		}
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if ( db != null && db.isOpen() ) {
			
			db.execSQL("update TBL_APP set NAME=?,PACKAGE_NAME=?,LOCK_STATUS=? where ID = ?",
						new Object[] { appView.getName() ,appView.getPackageName(), appView.getLockStatus(), appView.getId()});
			
			db.close();
			return true;
		}
		
		return false;
	}
	
	public App getById(Integer appId){
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
	
	
	public App getByPackageName(String packageName){
		if( packageName == null ){
			return null;
		}
		
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if ( db != null && db.isOpen() ) {
			App app = null;
			Cursor cursor = db.rawQuery("select ID,NAME,PACKAGE_NAME,LOCK_STATUS from TBL_APP where PACKAGE_NAME = ?",
										new String[] { packageName });
			
			if(cursor.moveToNext()) { 
				app = new App();
			    int id = cursor.getInt(0);
			    String name = cursor.getString(1);
			    //String packageName = cursor.getString(2);
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
	

	public void clearAll() {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if ( db != null && db.isOpen() ) {
			
			db.execSQL("delete from TBL_APP");
			
			db.close();
		}
	}

	public List<App> listAll() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if ( db != null && db.isOpen() ) {
			App app = null;
			Cursor cursor = db.rawQuery("select ID,NAME,PACKAGE_NAME,LOCK_STATUS from TBL_APP order by NAME asc",new String[]{});
			List<App> appList = new ArrayList<App>();
			
			while(cursor.moveToNext()) { 
				app = new App();
			    int id = cursor.getInt(0);
			    String name = cursor.getString(1);
			    String packageName = cursor.getString(2);
			    String lockStatus = cursor.getString(3);//获取第三列的值 
			
			    app.setId(id);
			    app.setName(name);
			    app.setPackageName(packageName);
			    if( lockStatus != null && lockStatus.equals("true")){
			    	app.setLockStatus(true);
			    }else{
			    	app.setLockStatus(false);
			    }
			    appList.add(app);
			} 
			
			cursor.close();
			db.close();
			return appList;
		}
		return null;
	}

	public void addOrUpdate(AppViewDTO appView) {
		App app = getById(appView.getId());
		if( app == null ){
			// insert
			this.add(appView);
		}else{
			// update
			this.update(appView);
		}
	}

	public List<App> listLockedApp() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if ( db != null && db.isOpen() ) {
			App app = null;
			Cursor cursor = db.rawQuery("select ID,NAME,PACKAGE_NAME,LOCK_STATUS from TBL_APP where LOCK_STATUS='true' order by NAME asc",new String[]{});
			List<App> appList = new ArrayList<App>();
			
			while(cursor.moveToNext()) { 
				app = new App();
			    int id = cursor.getInt(0);
			    String name = cursor.getString(1);
			    String packageName = cursor.getString(2);
			    String lockStatus = cursor.getString(3);//获取第三列的值 
			
			    app.setId(id);
			    app.setName(name);
			    app.setPackageName(packageName);
			    if( lockStatus != null && lockStatus.equals("true")){
			    	app.setLockStatus(true);
			    }else{
			    	app.setLockStatus(false);
			    }
			    
			    Log.d(TAG, app.toString());
			    appList.add(app);
			} 
			
			cursor.close();
			db.close();
			return appList;
		}
		return null;
	}

}
