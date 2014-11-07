package eden.sun.childrenguard.child.db.util;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class LocalDatabaseHelper extends SQLiteAssetHelper{
	private static final String DATABASE_NAME = "childrenguardian-child.db";
    private static final int DATABASE_VERSION = 1;

    public LocalDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
}
