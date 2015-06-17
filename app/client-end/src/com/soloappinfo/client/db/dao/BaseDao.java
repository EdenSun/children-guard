package com.soloappinfo.client.db.dao;

import android.content.Context;

import com.soloappinfo.client.db.util.LocalDatabaseHelper;

public class BaseDao {
	protected LocalDatabaseHelper dbHelper;

	public BaseDao(Context context) {
		dbHelper = new LocalDatabaseHelper(context);
	}
	
	
}
