package eden.sun.childrenguard.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import eden.sun.childrenguard.R;

public class RegisterActivity extends Activity {
	private Button registerBtn;
	private Button backBtn;
	private ProgressDialog progress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		registerBtn = (Button)findViewById(R.id.registerBtn);
		registerBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				progress = ProgressDialog.show(RegisterActivity.this, "Register",
					    "Please wait...", true);

					Timer timer;
					TimerTask task = new TimerTask(){
						public void run(){    
							progress.dismiss();
							
							RegisterActivity.this.runOnUiThread(new Runnable(){

								@Override
								public void run() {
									new AlertDialog.Builder(RegisterActivity.this)
							        .setIcon(android.R.drawable.ic_dialog_alert)
							        .setTitle("Register")
							        .setMessage("Register Success! Click OK to Login.")
							        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		
							            @Override
							            public void onClick(DialogInterface dialog, int which) {
							            	RegisterActivity.this.finish();
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
		
		backBtn = (Button)findViewById(R.id.backBtn);
		backBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				RegisterActivity.this.finish();
			}
        	
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
