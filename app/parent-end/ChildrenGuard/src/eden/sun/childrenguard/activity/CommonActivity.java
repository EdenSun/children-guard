package eden.sun.childrenguard.activity;

import android.app.Activity;
import android.app.ProgressDialog;

public class CommonActivity extends Activity {
	protected ProgressDialog progress;
	
	public void showProgressDialog(String title, String msg){
		this.progress = ProgressDialog.show(this, "Register",
			    "Please wait...", true);
	}
	
	public void dismissProgressDialog(){
		if( progress != null ){
			progress.dismiss();
		}
	}
}
