package eden.sun.childrenguard.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import eden.sun.childrenguard.R;
import eden.sun.childrenguard.util.Runtime;

public class ChangePasswordActivity extends CommonActivity  {
	/* UI Components */
	private Button changePasswordBtn;
	private Button cancelBtn;
	private EditText newPasswordEditText;
	/* END - UI Components */
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
		
		changePasswordBtn = (Button)findViewById(R.id.changePasswordBtn);
		changePasswordBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				 
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
