package eden.sun.childrenguard.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import eden.sun.childrenguard.R;

public class ModifyLockPasswordActivity extends Activity {
	private Button cancelBtn;
	private Button okBtn;
	private ProgressDialog progress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_lock_password);
		
		okBtn = (Button)findViewById(R.id.okBtn);
		okBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				progress = ProgressDialog.show(ModifyLockPasswordActivity.this, "Change Lock Password",
					    "Please wait...", true);

					Timer timer;
					TimerTask task = new TimerTask(){
						public void run(){    
							progress.dismiss();
							
							ModifyLockPasswordActivity.this.runOnUiThread(new Runnable(){

								@Override
								public void run() {
									new AlertDialog.Builder(ModifyLockPasswordActivity.this)
							        .setIcon(android.R.drawable.ic_dialog_alert)
							        .setTitle("Change Lock Password")
							        .setMessage("Change Password Success!")
							        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		
							            @Override
							            public void onClick(DialogInterface dialog, int which) {
							            	ModifyLockPasswordActivity.this.finish();
							            }
		
							        })
							        .show();
								}
								
							});
						}    
					};
					timer = new Timer();  
					timer.schedule(task, 3000);
			}
			
		});
		cancelBtn = (Button)findViewById(R.id.cancelBtn);
		cancelBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				ModifyLockPasswordActivity.this.finish();
			}
		});
		
	}

}
