package eden.sun.childrenguard.child.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;
import eden.sun.childrenguard.child.service.MainService;

public class CommonBindServiceActionBarActivity extends ActionBarActivity{
	private boolean mIsBound;
	private MainService mainService; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
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
		Context context = CommonBindServiceActionBarActivity.this;
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
