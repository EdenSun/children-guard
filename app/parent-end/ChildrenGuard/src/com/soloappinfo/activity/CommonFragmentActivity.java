package com.soloappinfo.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;

import com.soloappinfo.helper.DialogHolder;
import com.soloappinfo.helper.RequestHelper;
import com.soloappinfo.util.ShareDataKey;

public class CommonFragmentActivity extends FragmentActivity implements DialogHolder{
	protected ProgressDialog progress;
	protected Runtime runtime;
	private static final String PREFS_NAME = "share-data";
	private SharedPreferences settings ;  
	protected RequestHelper requestHelper;
	
	public CommonFragmentActivity() {
		super();
	}
	
	
	@Override
	protected void onStart() {
		super.onStart();
		requestHelper = this.getRequestHelper();
	}

	@Override
	public void showProgressDialog(String title, String msg){
		this.progress = ProgressDialog.show(this, title,
			    msg, true);
	}
	
	@Override
	public void dismissProgressDialog(){
		if( progress != null ){
			progress.dismiss();
		}
	}
	

	private void initSharedPreferences(){
		if( settings == null ){
			settings = getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
		}
	}
	
	public void putStringShareData(String key, String value) {
		initSharedPreferences();
		SharedPreferences.Editor editor = settings.edit();  
		editor.putString(key, value);  
		editor.commit();  
	}
	
	public String getStringShareData(String key) {
		initSharedPreferences();
		return settings.getString(key, null);
	}

	public String getAccessToken() {
		return this.getStringShareData(ShareDataKey.PARENT_ACCESS_TOKEN);
	}
	
	protected RequestHelper getRequestHelper() {
		if( requestHelper == null ){
			requestHelper = RequestHelper.getInstance(this);
		}
		return requestHelper;	
	}

	@Override
	protected void onStop() {
		super.onStop();
		if( requestHelper != null ){
			requestHelper.cancelAll(this.getClass());
		}
	}
	
	
}
