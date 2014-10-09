package eden.sun.childrenguard.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import eden.sun.childrenguard.R;
import eden.sun.childrenguard.util.Runtime;


public class LoginActivity extends Activity {
	private Button loginBtn;
	private Button registerBtn ;
	private Button forgetPasswordBtn;
	private Runtime runtime;
	
	private ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        loginBtn = (Button)findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				progress = ProgressDialog.show(LoginActivity.this, "Login",
				    "Please wait...", true);

				Timer timer;
				TimerTask task = new TimerTask(){    
					public void run(){    
						progress.dismiss();
						
						Intent it = new Intent(LoginActivity.this, ChildrenListActivity.class);
						startActivity(it);
					}    
				};    
				timer = new Timer();  
				timer.schedule(task, 1000);
				
				
				
			}
        	
        });
        
        registerBtn = (Button)findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent it = new Intent(LoginActivity.this, RegisterActivity.class);
				startActivity(it);   
			}
        	
        });
        
        forgetPasswordBtn = (Button)findViewById(R.id.forgetPasswordBtn);
        forgetPasswordBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent it = new Intent(LoginActivity.this, PasswordResetActivity.class);
				startActivity(it);   
			}
        	
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
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


	@Override
	protected void onStart() {
		super.onStart();
		runtime = Runtime.getInstance();
	}
    
    
}
