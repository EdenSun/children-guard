package eden.sun.childrenguard.child.db.dao;

import android.content.Context;
import eden.sun.childrenguard.child.db.util.LocalDatabaseHelper;

public class BaseDao {
	protected LocalDatabaseHelper dbHelper;

	public BaseDao(Context context) {
		dbHelper = new LocalDatabaseHelper(context);
	}
	
	
}
