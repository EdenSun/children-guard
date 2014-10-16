package eden.sun.childrenguard.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import eden.sun.childrenguard.R;
import eden.sun.childrenguard.util.UIUtil;

public class ChangePasswordActivity extends CommonActivity  {
	/* UI Components */
	private Button changePasswordBtn;
	private Button cancelBtn;
	private EditText newPasswordEditText;
	private EditText resetCodeEditText;
	/* END - UI Components */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
		
		resetCodeEditText = (EditText)findViewById(R.id.resetCodeEditText);
		newPasswordEditText = (EditText)findViewById(R.id.newPasswordEditText);
		
		changePasswordBtn = (Button)findViewById(R.id.changePasswordBtn);
		changePasswordBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				String title = "Change Password";
				String msg = "Change password success. Press OK to login.";
				String btnText = "OK";
				
				AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
					ChangePasswordActivity.this,
					title,
					msg,
					btnText,
					new DialogInterface.OnClickListener() {
			            @Override
			            public void onClick(DialogInterface dialog, int which) {
			            	dialog.dismiss();
			            	ChangePasswordActivity.this.finish();
			            }
			        }
				);
				
				dialog.show();
			}
        	
        });
		
		cancelBtn = (Button)findViewById(R.id.cancelBtn);
		cancelBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				ChangePasswordActivity.this.finish();
			}
        	
        });
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.change_password, menu);
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
