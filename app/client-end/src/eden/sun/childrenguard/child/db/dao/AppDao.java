package eden.sun.childrenguard.child.db.dao;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import eden.sun.childrenguard.child.db.model.App;
import eden.sun.childrenguard.server.dto.AppViewDTO;

public class AppDao extends BaseDao{

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
				db.execSQL("insert into TBL_APP (ID,NAME,PACKAGE_NAME,LOCK_STATUS) values (?,?,?,?)",
							new Object[] { appView.getId(),appView.getName(),appView.getPackageName(),appView.getLockStatus() });
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
	
}
