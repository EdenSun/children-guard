package eden.sun.childrenguard.child.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class PackageChangeReceiver extends BroadcastReceiver{
	private static final String TAG = "PackageChangeReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		boolean replacing = intent.getBooleanExtra(Intent.EXTRA_REPLACING, false);
		Uri data = intent.getData();
		Log.d(TAG, "Action: " + intent.getAction());
		Log.d(TAG, "The DATA: " + data);	
		Log.d(TAG, "Is Replacing: " + replacing);	
	}

}
