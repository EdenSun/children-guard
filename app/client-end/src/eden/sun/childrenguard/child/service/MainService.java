package eden.sun.childrenguard.child.service;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import eden.sun.childrenguard.child.task.UploadAppRunnable;
import eden.sun.childrenguard.child.util.gps.GPSHelper;

public class MainService extends Service {
	public static final String TAG = "MainService";
	private LocalBinder binder = new LocalBinder();
	public Handler handler;
	
	@Override
	public void onCreate() {
		Log.d(TAG, "onCreate() executed");

        // Display a notification about us starting.    We put an icon in the status bar. 
        //uploadApplicationInfo();
        
        startGps();
	}

	private void startGps() {
		handler = new Handler();
		GPSHelper gpsHelper = new GPSHelper(this,handler);
		gpsHelper.start();
	}

	/*private void uploadApplicationInfo() {
		handler = new Handler();
		
		//upload all applications info to server every 24 hours
		int interval = 1000 * 24 * 3600;	
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

		    @Override
		    public void run() {
		        // Do upload task
		    	Runnable uploadAppRunnable = new UploadAppRunnable(MainService.this,handler);
				handler.post(uploadAppRunnable);
		    }

		}, 0, interval);
	}*/

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStartCommand() executed");
		
		return START_STICKY; 
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy() executed");
		
        // Tell the user we stopped. 
        Toast.makeText(this, "local_service_stopped", Toast.LENGTH_SHORT).show(); 
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, "onBind() executed");
		return binder;
	}

	public class LocalBinder extends Binder {
		public MainService getService() {
			return MainService.this;
		}
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		Log.d(TAG, "onUnBind() executed");
		return super.onUnbind(intent);
	}

	/** 
     * Show a notification while this service is running. 
     */ 
    private void showNotification() { 
    	Log.i(TAG, "show notification");
    } 
}