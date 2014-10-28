package eden.sun.childrenguard.child.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import eden.sun.childrenguard.child.R;

public class AppPasswordActivity extends Activity {
    private EditText lockPasswordEditText;  
    private Button okBtn;
    private Button cancelBtn;
    private String password;  
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_password);
		
		initComponent();
		
		okBtn.setOnClickListener(new OnClickListener(){

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
		okBtn = (Button)findViewById(R.id.okBtn);
		cancelBtn = (Button)findViewById(R.id.cancelBtn);
	}

}
