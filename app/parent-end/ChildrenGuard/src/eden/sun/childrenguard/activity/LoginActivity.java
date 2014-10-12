package eden.sun.childrenguard.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import eden.sun.childrenguard.R;
import eden.sun.childrenguard.comet.LoginListener;
import eden.sun.childrenguard.util.CometdConfig;
import eden.sun.childrenguard.util.Runtime;


public class LoginActivity extends CommonActivity {
	private Button loginBtn;
	private Button registerBtn ;
	private Button forgetPasswordBtn;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        loginBtn = (Button)findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new OnClickListener(){
        	
			@Override
			public void onClick(View arg0) {
				
				AsyncTask<Map<String, Object>,Integer,Boolean> task = new LoginTask(LoginActivity.this);
				
				Map<String, Object> data = new HashMap<String,Object>();
				data.put("username", "eden");
				data.put("password", "password");
				
				task.execute(data);
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
		
		runtime.subscribe(CometdConfig.LOGIN_CHANNEL,new LoginListener(LoginActivity.this));
	}
    
	
	class LoginTask extends AsyncTask<Map<String, Object>,Integer,Boolean>{
		private Activity context;
		
		public LoginTask(Activity context) {
			super();
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			String title = "Login";
			String msg = "Please wait...";
			showProgressDialog(title,msg);
		}

		@Override
		protected Boolean doInBackground(Map<String, Object>... params) {
			Map<String, Object> data = params[0];
			
			runtime.publish(data, CometdConfig.LOGIN_CHANNEL);
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			
			dismissProgressDialog();
		}
		
	}
}
