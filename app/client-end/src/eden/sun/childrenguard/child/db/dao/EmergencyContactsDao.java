package eden.sun.childrenguard.child.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import eden.sun.childrenguard.child.db.model.App;
import eden.sun.childrenguard.child.db.model.EmergencyContact;
import eden.sun.childrenguard.server.dto.AppViewDTO;
import eden.sun.childrenguard.server.dto.EmergencyContactViewDTO;

public class EmergencyContactsDao extends BaseDao{
	private static final String TABLE_NAME = "TBL_EMERGENCY_CONTACTS";
	
	public EmergencyContactsDao(Context context) {
		super(context);
	}

	public boolean batchAdd(List<EmergencyContactViewDTO> emergencyContactList){
		if( emergencyContactList == null || emergencyContactList.size() == 0 ){
			return true;
		}
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if ( db != null && db.isOpen() ) {
			for(EmergencyContactViewDTO emergencyContactView : emergencyContactList ){
		        ContentValues cv = new ContentValues();  
		        cv.put("ID",  emergencyContactView.getId());  
		        cv.put("NAME", emergencyContactView.getName());  
		        cv.put("PHONE", emergencyContactView.getPhone()); 
		        
		        db.insert(TABLE_NAME, null, cv);  
			}
			
			db.close();
			return true;
		}
		return false;
	}
	
	
	public boolean add(EmergencyContactViewDTO emergencyContactView){
		if( emergencyContactView == null ){
			return false;
		}
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if ( db != null && db.isOpen() ) {
			db.execSQL("insert into " + TABLE_NAME + " (ID,NAME,PHONE) values (?,?,?)",
						new Object[] { 
							emergencyContactView.getId(),
							emergencyContactView.getName(),
							emergencyContactView.getPhone()
						});
			
			db.close();
			return true;
		}
		
		return false;
	}
	
	public boolean delete(Integer emergencyContactId){
		if( emergencyContactId == null ){
			return false;
		}
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if ( db != null && db.isOpen() ) {
			
			db.execSQL("delete from " + TABLE_NAME + " where ID = ?",
						new Object[] { emergencyContactId });
			
			db.close();
			return true;
		}
		
		return false;
	}
	
	public boolean update(EmergencyContactViewDTO emergencyContactView){
		if( emergencyContactView == null ){
			return false;
		}
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if ( db != null && db.isOpen() ) {
			
			db.execSQL("update " + TABLE_NAME + " set NAME=?,PHONE=? where ID = ?",
						new Object[] { 
							emergencyContactView.getName() ,
							emergencyContactView.getPhone(), 
							emergencyContactView.getId()
						});
			
			db.close();
			return true;
		}
		
		return false;
	}
	
	public EmergencyContact getById(Integer emergencyContactId){
		if( emergencyContactId == null ){
			return null;
		}
		
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if ( db != null && db.isOpen() ) {
			EmergencyContact emergencyContact = null;
			Cursor cursor = db.rawQuery("select ID,NAME,PHONE from " + TABLE_NAME + " where ID = ?",
										new String[] { emergencyContactId.toString() });
			
			if(cursor.moveToNext()) { 
				emergencyContact = cursorToModel(cursor);
			} 
			
			cursor.close();
			db.close();
			return emergencyContact;
		}
		
		return null;
	}

	private EmergencyContact cursorToModel(Cursor cursor) {
		EmergencyContact emergencyContact = new EmergencyContact();
	    int id = cursor.getInt(0);
	    String name = cursor.getString(1);
	    String phone = cursor.getString(2);
	
	    emergencyContact.setId(id);
	    emergencyContact.setName(name);
	    emergencyContact.setPhone(phone);
		return emergencyContact;
	}

	public void clearAll() {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if ( db != null && db.isOpen() ) {
			
			db.execSQL("delete from " + TABLE_NAME);
			
			db.close();
		}
	}

	public List<EmergencyContact> listAll() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if ( db != null && db.isOpen() ) {
			EmergencyContact emergencyContact = null;
			Cursor cursor = db.rawQuery("select ID,NAME,PHONE from " + TABLE_NAME + " order by NAME asc",new String[]{});
			List<EmergencyContact> emergencyContactList = new ArrayList<EmergencyContact>();
			
			while(cursor.moveToNext()) { 
				emergencyContact = cursorToModel(cursor);
			    emergencyContactList.add(emergencyContact);
			} 
			
			cursor.close();
			db.close();
			return emergencyContactList;
		}
		return null;
	}

	public void addOrUpdate(EmergencyContactViewDTO emergencyContactView) {
		EmergencyContact emergencyContact = getById(emergencyContactView.getId());
		if( emergencyContact == null ){
			// insert
			this.add(emergencyContactView);
		}else{
			// update
			this.update(emergencyContactView);
		}
	}
}
