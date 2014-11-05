package eden.sun.childrenguard.child.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.widget.Toast;
import eden.sun.childrenguard.child.service.MainService;
import eden.sun.childrenguard.child.util.RequestHelper;
import eden.sun.childrenguard.child.util.ShareDataKey;

public class CommonBindServiceActivity extends Activity{
	private boolean mIsBound;
	private MainService mainService; 
	protected ProgressDialog progress;
	private static final String PREFS_NAME = "share-data";
	private SharedPreferences settings ;  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
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
		return this.getStringShareData(ShareDataKey.CHILD_ACCESS_TOKEN);
	}
	
	protected RequestHelper getRequestHelper() {
		return RequestHelper.getInstance(this);		
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
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart() {
		super.onStart();
		startMainService();
		bindMainService();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		unbindMainService();
	}
	
	
	public void startMainService() {
		startService(new Intent(this,    
                MainService.class));		
	}

	public void bindMainService() {
		// Establish a connection with the service.    We use an explicit 
        // class name because we want a specific service implementation that 
        // we know will be running in our own process (and thus won't be 
        // supporting component replacement by other applications). 
        bindService(new Intent(this,    
                        MainService.class), mConnection, Context.BIND_AUTO_CREATE); 
        mIsBound = true; 		
	}

	public void unbindMainService(){
		if (mIsBound) { 
			unbindService(mConnection); 
			mIsBound = false;
		} 
	}
	private ServiceConnection mConnection = new ServiceConnection() {
		Context context = CommonBindServiceActivity.this;
		public void onServiceConnected(ComponentName className, IBinder service) {
			// This is called when the connection with the service has been
			// established, giving us the service object we can use to
			// interact with the service. Because we have bound to a explicit
			// service that we know is running in our own process, we can
			// cast its IBinder to a concrete class and directly access it.
			mainService = ((MainService.LocalBinder) service).getService();

			// Tell the user about this for our demo.
			Toast.makeText(context,
					"local service connected", Toast.LENGTH_SHORT)
					.show();
		}

		public void onServiceDisconnected(ComponentName className) {
			// This is called when the connection with the service has been
			// unexpectedly disconnected -- that is, its process crashed.
			// Because it is running in our same process, we should never
			// see this happen.
			mainService = null;
			Toast.makeText(context,
					"local service disconnected", Toast.LENGTH_SHORT)
					.show();
		}
	};
}
