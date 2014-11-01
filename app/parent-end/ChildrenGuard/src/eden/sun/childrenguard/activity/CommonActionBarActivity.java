package eden.sun.childrenguard.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import eden.sun.childrenguard.util.RequestHelper;
import eden.sun.childrenguard.util.ShareDataKey;

public class CommonActionBarActivity extends ActionBarActivity{
	protected ProgressDialog progress;
	protected Runtime runtime;
	private static final String PREFS_NAME = "share-data";
	private SharedPreferences settings ;  
	
	public CommonActionBarActivity() {
		super();
	}
	
	public void showProgressDialog(String title, String msg){
		this.progress = ProgressDialog.show(this, title,
			    msg, true);
	}
	
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
		return RequestHelper.getInstance(this);		
	}
	
}
