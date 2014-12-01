package eden.sun.childrenguard;

import org.jraf.android.backport.switchwidget.Switch;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import eden.sun.childrenguard.fragment.TimePickerFragment;
import eden.sun.childrenguard.fragment.WeekdayChooserFragment;
import eden.sun.childrenguard.util.Callback;

public class PresetLockActivity extends Activity {
	protected static final String TAG = "PresetLockActivity";
	private View startTimeItem;
	private TextView startTimeTextView;
	private View endTimeItem;
	private TextView endTimeTextView;
	private View repeatItem;
	private TextView repeatTextView;
	private View lockAppSettingItem;
	private Switch lockCallSwitch;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preset_lock);
		
		initComponent();
	}

	private void initComponent() {
		startTimeItem = (View)findViewById(R.id.startTimeItem);
		startTimeItem.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Log.d(TAG, "start time item click.");
				showSetStartTimeDialog();
			}
			
		});
		endTimeItem = (View)findViewById(R.id.endTimeItem);
		endTimeItem.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Log.d(TAG, "end time item click.");
				showSetEndTimeDialog();
			}
			
		});
		
		repeatItem = (View)findViewById(R.id.repeatItem);
		repeatItem.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Log.d(TAG, "repeat item click.");
				showWeekdayChooserDialog();
			}

		});
		
		lockAppSettingItem = (View)findViewById(R.id.lockAppSettingItem);
		lockCallSwitch = (Switch)findViewById(R.id.lockCallSwitch);
		startTimeTextView = (TextView)findViewById(R.id.startTimeTextView);
		endTimeTextView = (TextView)findViewById(R.id.endTimeTextView);
		repeatTextView = (TextView)findViewById(R.id.repeatTextView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.preset_lock, menu);
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

	
	private void showSetStartTimeDialog() {  
        FragmentTransaction ft = getFragmentManager().beginTransaction();  
        // Create and show the dialog.  
        TimePickerFragment newFragment  = new TimePickerFragment(new Callback(){

			@Override
			public void execute(CallbackResult result) {
				if( result != null && result.isSuccess() == true ){
					startTimeTextView.setText(result.getInfo());
					Toast.makeText(PresetLockActivity.this, "Set time:"+ result.getInfo(), Toast.LENGTH_SHORT).show();
				}
			}
        		
        });
        
        /*Bundle args = new Bundle();  
        int mStackLevel = 0;  
        args.putInt("num", mStackLevel);  
        //传递参数才用到  
        newFragment.setArguments(args);;  */
        newFragment.show(ft, "startTimePickerDialog");  
    }
	
	private void showSetEndTimeDialog() {  
        FragmentTransaction ft = getFragmentManager().beginTransaction();  
        // Create and show the dialog.  
        TimePickerFragment newFragment  = new TimePickerFragment(new Callback(){

			@Override
			public void execute(CallbackResult result) {
				if( result != null && result.isSuccess() == true ){
					endTimeTextView.setText(result.getInfo());
					Toast.makeText(PresetLockActivity.this, "Set time:"+ result.getInfo(), Toast.LENGTH_SHORT).show();
				}
			}
        		
        });
        
        newFragment.show(ft, "endTimePickerDialog");  
    }
	
	private void showWeekdayChooserDialog() {
		FragmentTransaction ft = getFragmentManager().beginTransaction();  
        // Create and show the dialog.  
		WeekdayChooserFragment newFragment  = new WeekdayChooserFragment(new Callback(){

			@Override
			public void execute(CallbackResult result) {
				if( result != null && result.isSuccess() == true ){
					repeatTextView.setText(result.getInfo());
					Toast.makeText(PresetLockActivity.this, "Set repeat:"+ result.getInfo(), Toast.LENGTH_SHORT).show();
				}
			}
        		
        });
        
        newFragment.show(ft, "weekdayChooserDialog");  		
	}
}
