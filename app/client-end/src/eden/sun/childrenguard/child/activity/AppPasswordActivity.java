package eden.sun.childrenguard.child.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import eden.sun.childrenguard.child.R;
import eden.sun.childrenguard.child.db.dao.ChildSettingDao;

public class AppPasswordActivity extends Activity {
    private EditText lockPasswordEditText;  
    private Button unlockBtn;
    private Button cancelBtn;
    private String password;  
    private ChildSettingDao childSettingDao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_password);
		childSettingDao = new ChildSettingDao(this);
		
		initComponent();
		
		// get password 
		password = childSettingDao.getAppLockPassword();
				
		unlockBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		cancelBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}

	private void initComponent() {
		lockPasswordEditText = (EditText)findViewById(R.id.lockPasswordEditText);
		unlockBtn = (Button)findViewById(R.id.unlockBtn);
		cancelBtn = (Button)findViewById(R.id.cancelBtn);
	}

}
