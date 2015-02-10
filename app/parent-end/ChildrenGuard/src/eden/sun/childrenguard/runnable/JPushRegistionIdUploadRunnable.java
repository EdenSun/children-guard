package eden.sun.childrenguard.runnable;


import android.content.Context;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;

public class JPushRegistionIdUploadRunnable implements Runnable{
	private static final String TAG = "JPushRegistionIdUploadRunnable";
	private Context context;
	
	public JPushRegistionIdUploadRunnable(Context context) {
		super();
		this.context = context;
	}


	@Override
	public void run() {
		boolean isLoop = true;
		
		while( isLoop ){
			String registionId = JPushInterface.getRegistrationID(context);
			if( registionId == null || registionId.trim().length() == 0 ){
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					Log.e(TAG, "thread interrupted.", e);
				}
			}else{
				Log.d(TAG, "***********:" + registionId);
				isLoop = false;
			}
		}
		
	}

}
