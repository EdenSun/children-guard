package eden.sun.childrenguard.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import eden.sun.childrenguard.util.Runtime;

public class CommonActivity extends Activity {
	protected ProgressDialog progress;
	protected Runtime runtime;
	
	public void showProgressDialog(String title, String msg){
		this.progress = ProgressDialog.show(this, title,
			    msg, true);
	}
	
	public void dismissProgressDialog(){
		if( progress != null ){
			progress.dismiss();
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		runtime = Runtime.getInstance(this);
	}
	
	
}
