package eden.sun.childrenguard.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import eden.sun.childrenguard.R;
import eden.sun.childrenguard.comet.PasswordResetListener;
import eden.sun.childrenguard.util.CometdConfig;
import eden.sun.childrenguard.util.StringUtil;
import eden.sun.childrenguard.util.UIUtil;

public class PasswordResetActivity extends CommonActivity {
	/* UI Components */
	private Button resetBtn;
	private Button backBtn;
	
	private EditText emailEditText;
	/* END - UI Components */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password_reset);
		
		emailEditText = (EditText)findViewById(R.id.emailEditText);
		
		resetBtn = (Button)findViewById(R.id.resetBtn);
		resetBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				boolean isPassed = doValidation();
				
				if( isPassed ){
					String email = UIUtil.getEditTextValue(emailEditText);
					
					Map<String, Object> data = new HashMap<String,Object>();
					data.put("email", email);
					
					AsyncTask<Map<String, Object>,Integer,Boolean> task = new PasswordResetTask(PasswordResetActivity.this);
					task.execute(data);
					
				}
				
			}

        });
		
		backBtn = (Button)findViewById(R.id.backBtn);
		backBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				PasswordResetActivity.this.finish();
			}
        	
        });
	}

	private boolean doValidation() {
		String email = emailEditText.getText().toString().trim();
		
		if( StringUtil.isBlank(email) ){
			String title = "Reset Password";
			String msg = "Please input your email.";
			String btnText = "OK";
			
			AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
				PasswordResetActivity.this,
				title,
				msg,
				btnText,
				new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int which) {
		            	dialog.dismiss();
		            }
		        }
			);
			
			dialog.show();
			return false;
		}
		
		return true;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.password_reset, menu);
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
		
		runtime.subscribe(CometdConfig.PASSWORD_RESET_CHANNEL,new PasswordResetListener(PasswordResetActivity.this));
	}
	
	class PasswordResetTask extends AsyncTask<Map<String, Object>,Integer,Boolean>{
		private Activity context;
		
		public PasswordResetTask(Activity context) {
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
		protected Boolean doInBackground(
				Map<String, Object>... params) {
			Map<String, Object> data = params[0];
			
			runtime.publish(data, CometdConfig.PASSWORD_RESET_CHANNEL,new PasswordResetListener(PasswordResetActivity.this));
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			
			dismissProgressDialog();
		}
		
	}
}
