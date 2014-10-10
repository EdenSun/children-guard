package eden.sun.childrenguard.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import eden.sun.childrenguard.R;
import eden.sun.childrenguard.util.StringUtil;
import eden.sun.childrenguard.util.UIUtil;

public class PasswordResetActivity extends CommonActivity {
	/* UI Components */
	private Button resetBtn;
	private Button backBtn;
	
	private EditText emailEditTExt;
	/* END - UI Components */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password_reset);
		
		emailEditTExt = (EditText)findViewById(R.id.emailEditText);
		
		resetBtn = (Button)findViewById(R.id.resetBtn);
		resetBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				boolean isPassed = doValidation();
				
				if( isPassed ){
					Intent it = new Intent(PasswordResetActivity.this, ChangePasswordActivity.class);
					startActivity(it);   
					
					PasswordResetActivity.this.finish();
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
		String email = emailEditTExt.getText().toString().trim();
		
		if( StringUtil.isBlank(email) ){
			String title = "Reset Password";
			String msg = "Email can not be blank.";
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
}
