package eden.sun.childrenguard.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.TextView;
import eden.sun.childrenguard.activity.ChildrenManageActivity;
import eden.sun.childrenguard.util.RequestHelper;
import eden.sun.childrenguard.util.ShareDataKey;
import eden.sun.childrenguard.util.UIUtil;

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
	
	/*protected void initCommonChildBasicInfo(){
		ChildrenManageActivity parentActivity = ((ChildrenManageActivity)this.getActivity());
		if( parentActivity.getChildBasicInfo() != null ){
			String text = UIUtil.formatTextForView(parentActivity.getChildBasicInfo().getChild().getNickname());
			if( nicknameTextView != null ){
				nicknameTextView.setText(text);
			}
		}
	}*/
}
