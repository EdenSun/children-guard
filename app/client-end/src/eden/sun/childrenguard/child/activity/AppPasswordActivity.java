package eden.sun.childrenguard.child.activity;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import eden.sun.childrenguard.child.R;
import eden.sun.childrenguard.child.db.dao.ChildSettingDao;
import eden.sun.childrenguard.child.util.UIUtil;

public class AppPasswordActivity extends Activity {
    private EditText lockPasswordEditText;  
    private Button confirmBtn;
    private Button cancelBtn;
    private String password;  
    private ChildSettingDao childSettingDao;
    private ImageView appIconImageView;
    private TextView appNameTextView;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_password);
		childSettingDao = new ChildSettingDao(this);
		
		initComponent();
		
		// get password 
		password = childSettingDao.getAppLockPassword();
				
		// set app icon and app name
		setAppInfo();
		
		bindEvent();
	}

	private void bindEvent() {
		confirmBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				String inputPassword = UIUtil.getEditTextValue(lockPasswordEditText);  
		        
				if(TextUtils.isEmpty(inputPassword)){  
		            Toast.makeText(AppPasswordActivity.this, "Password can not be blank.", Toast.LENGTH_SHORT).show();  
		        }
		        else if( password.equals(inputPassword) ){  
		            finish();
		        }  
		        else{
		            Toast.makeText(AppPasswordActivity.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();  
		        }
			}
			
		});
		
		cancelBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});		
	}
	
    @Override  
    public boolean onKeyDown(int keyCode, KeyEvent event)  
    {  
        if( KeyEvent.KEYCODE_BACK == event.getKeyCode() ){  
            return true;
        }
        return super.onKeyDown(keyCode, event);  
    } 
    

	private void setAppInfo() {
		try  
        {  
            String packageName = getIntent().getStringExtra("packageName");  
            ApplicationInfo appInfo = getPackageManager().getPackageInfo(packageName, 0).applicationInfo;  
            Drawable appIcon = appInfo.loadIcon(getPackageManager());  
            String appName = appInfo.loadLabel(getPackageManager()).toString();  
              
            appIconImageView.setImageDrawable(appIcon);  
            appNameTextView.setText(appName);  
        }  
        catch (Exception e)  
        {  
            e.printStackTrace();  
        }		
	}

	private void initComponent() {
		lockPasswordEditText = (EditText)findViewById(R.id.lockPasswordEditText);
		confirmBtn = (Button)findViewById(R.id.confirmBtn);
		cancelBtn = (Button)findViewById(R.id.cancelBtn);
		appIconImageView = (ImageView)findViewById(R.id.appIconImageView);
		appNameTextView = (TextView)findViewById(R.id.appNameTextView);
	}

}
