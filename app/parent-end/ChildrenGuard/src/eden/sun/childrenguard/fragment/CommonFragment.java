package eden.sun.childrenguard.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import eden.sun.childrenguard.helper.RequestHelper;
import eden.sun.childrenguard.util.ShareDataKey;

public class CommonFragment extends Fragment {
	protected ProgressDialog progress;
	protected Runtime runtime;
	private static final String PREFS_NAME = "share-data";
	private SharedPreferences settings ;  
	protected RequestHelper requestHelper;
	
	public CommonFragment() {
		super();
	}
	
	@Override
	public void onStart() {
		super.onStart();
		requestHelper = this.getRequestHelper();
	}


	public void showProgressDialog(String title, String msg){
		if( this.progress != null ){
			this.progress.setTitle(title);
			this.progress.setMessage(msg);
			if( !this.progress.isShowing() ){
				this.progress.show();
			}
		}else{
			this.progress = ProgressDialog.show(this.getActivity(), title,
					msg, true);
		}
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
		if( requestHelper == null ){
			requestHelper = RequestHelper.getInstance(this.getActivity());
		}
		return requestHelper;
	}

	@Override
	public void onStop() {
		super.onStop();
		if( requestHelper != null ){
			requestHelper.cancelAll(this.getClass());
		}
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
