package eden.sun.childrenguard.child.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import eden.sun.childrenguard.child.util.RequestHelper;
import eden.sun.childrenguard.child.util.ShareDataKey;

public class CommonActivity extends Activity {
	protected ProgressDialog progress;
	//protected Runtime runtime;
	private static final String PREFS_NAME = "share-data";
	private SharedPreferences settings ;  
	
	public CommonActivity() {
		super();
	}
	
	public void showProgressDialog(String title, String msg){
		this.progress = ProgressDialog.show(this, title,
			    msg, true);
	}
	
	public void setProgressDialogText(String title, String msg){
		if( this.progress != null ){
			if( title != null ){
				this.progress.setTitle(title);
			}
			if( msg != null ){
				this.progress.setMessage(msg);
			}
		}
	}
	
	public void dismissProgressDialog(){
		if( progress != null ){
			progress.dismiss();
		}
	}
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//runtime = Runtime.getInstance(this);
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

	public String getChildMobile() {
		String childMobile = this.getStringShareData(ShareDataKey.CHILD_MOBILE);
		if( childMobile == null ){
			TelephonyManager phoneMgr=(TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
			childMobile = phoneMgr.getLine1Number();
			if( childMobile == null ){
				return null;
			}else{
				this.putStringShareData(ShareDataKey.CHILD_MOBILE, childMobile);
				return childMobile;
			}
		}else{
			return childMobile;
		}
	}
	
	public String getAccessToken() {
		return this.getStringShareData(ShareDataKey.CHILD_ACCESS_TOKEN);
	}
	
	protected RequestHelper getRequestHelper() {
		return RequestHelper.getInstance(this);		
	}
}
