package eden.sun.childrenguard.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import eden.sun.childrenguard.util.RequestHelper;
import eden.sun.childrenguard.util.Runtime;
import eden.sun.childrenguard.util.ShareDataKey;

public class CommonFragment extends Fragment {
	protected ProgressDialog progress;
	protected Runtime runtime;
	private static final String PREFS_NAME = "share-data";
	private SharedPreferences settings ;  
	
	public CommonFragment() {
		super();
	}
	
	public void showProgressDialog(String title, String msg){
		this.progress = ProgressDialog.show(this.getActivity(), title,
			    msg, true);
	}
	
	public void dismissProgressDialog(){
		if( progress != null ){
			progress.dismiss();
		}
	}

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		runtime = Runtime.getInstance(this.getActivity());
	}

	private void initSharedPreferences(){
		if( settings == null ){
			settings = this.getActivity().getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
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
		return RequestHelper.getInstance(this.getActivity());		
	}
}