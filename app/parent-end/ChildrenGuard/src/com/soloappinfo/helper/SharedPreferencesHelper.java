package com.soloappinfo.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.soloappinfo.util.ShareDataKey;

public class SharedPreferencesHelper {
	private static final String TAG = "SharedPreferencesHelper";
	private volatile static SharedPreferences sharedPreferences ;  
	private volatile static  SharedPreferencesHelper helper;
	
	private SharedPreferencesHelper() {
		Log.i(TAG, "Init SharedPreferencesHelper.");
	}
	
	public static SharedPreferencesHelper getInstance(Context context) {
		if (sharedPreferences == null) {
			synchronized (SharedPreferencesHelper.class) {
				if (sharedPreferences == null) {
					helper = new SharedPreferencesHelper();
					
					helper.sharedPreferences = context.getSharedPreferences(ShareDataKey.PREFS_NAME, Activity.MODE_PRIVATE);
				}
			}
		}

		return helper;
	}
	
	
	public String getAccessToken() {
		return sharedPreferences.getString(ShareDataKey.PARENT_ACCESS_TOKEN, null);
	}
	
	
}
