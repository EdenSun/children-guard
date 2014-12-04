package eden.sun.childrenguard.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jraf.android.backport.switchwidget.Switch;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import eden.sun.childrenguard.R;
import eden.sun.childrenguard.errhandler.DefaultVolleyErrorHandler;
import eden.sun.childrenguard.fragment.PresetLockAppDialogFragment;
import eden.sun.childrenguard.fragment.TimePickerDialogFragment;
import eden.sun.childrenguard.fragment.WeekdayChooserDialogFragment;
import eden.sun.childrenguard.server.dto.AppViewDTO;
import eden.sun.childrenguard.server.dto.PresetLockViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.ApplyPresetLockParam;
import eden.sun.childrenguard.util.Callback;
import eden.sun.childrenguard.util.Config;
import eden.sun.childrenguard.util.JSONUtil;
import eden.sun.childrenguard.util.RequestURLConstants;
import eden.sun.childrenguard.util.UIUtil;
import eden.sun.childrenguard.util.WeekConstants;

public class PresetLockActivity extends CommonActivity {
	protected static final String TAG = "PresetLockActivity";
	/******* Component ******/
	private Switch presetOnOffSwitch;
	private View startTimeItem;
	private TextView startTimeTextView;
	private View endTimeItem;
	private TextView endTimeTextView;
	private View repeatItem;
	private TextView repeatTextView;
	private View lockAppSettingItem;
	private TextView lockAppSettingTextView;
	private Switch lockCallSwitch;
	/********************/
	
	/******* data *******/
	private PresetLockViewDTO presetLockView;
	private Integer childId;
	private Date startTime;
	private Date endTime;
	private List<Boolean> reapeat;
	private List<Integer> checkedAppIdList;
	
	
	
	/***************************/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preset_lock);
		childId = getIntent().getIntExtra("childId", 0);
	    
		initComponent();
	}
	
	
	private void initPresetLock(PresetLockViewDTO presetLockView) {
		Boolean presetOnOff = presetLockView.getPresetOnOff();
		if( presetOnOff != null && presetOnOff.booleanValue() == true ){
			presetOnOffSwitch.setChecked(true);
		}else{
			presetOnOffSwitch.setChecked(false);
		}
		
		Date startTime = presetLockView.getStartTime();
		String startTimeSummary = presetLockView.getStartTimeSummary();
		if( startTimeSummary != null ){
			startTimeTextView.setText(startTimeSummary);
		}else{
			startTimeTextView.setText(R.string.default_text_perset_lock_start_time);
		}
		
		Date endTime = presetLockView.getEndTime();
		String endTimeSummary = presetLockView.getEndTimeSummary();
		if( endTimeSummary != null ){
			endTimeTextView.setText(endTimeSummary);
		}else{
			endTimeTextView.setText(R.string.default_text_perset_lock_end_time);
		}
		
		List<Boolean> reapeatList = presetLockView.getRepeat();
		String repeatSummary = presetLockView.getRepeatSummary();
		if( repeatSummary != null && !repeatSummary.trim().equals("") ){
			repeatTextView.setText(repeatSummary);
		}else{
			repeatTextView.setText(R.string.default_text_perset_lock_repeat);
		}
		
		Boolean lockCallStatus = presetLockView.getLockCallStatus();
		if( lockCallStatus != null && lockCallStatus.booleanValue() == true ){
			lockCallSwitch.setChecked(true);
		}else{
			lockCallSwitch.setChecked(false);
		}
		
		//List<AppViewDTO> appList = presetLockView.getAppList();
		String appLockSummary = presetLockView.getAppLockSummary();
		if( appLockSummary != null && !appLockSummary.trim().equals("") ){
			lockAppSettingTextView.setText(appLockSummary);
		}else{
			lockAppSettingTextView.setText(R.string.default_text_perset_lock_lock_app_setting);
		}
		
	}

	private void loadPresetLock(final Callback<PresetLockViewDTO> successcallback) {
		String title = "Preset Lock";
		String msg = "Please wait...";
		showProgressDialog(title,msg);
		
		String url = String.format(
				Config.BASE_URL_MVC + RequestURLConstants.URL_LOAD_PRESET_LOCK_DATA + "?childId=%1$s",  
				childId);

		getRequestHelper().doGet(
			url,
			this.getClass(),
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					dismissProgressDialog();
					ViewDTO<PresetLockViewDTO> view = JSONUtil.getLoadPresetLockDataView(response);
					
					if( view.getMsg().equals(ViewDTO.MSG_SUCCESS) ){
						PresetLockViewDTO presetLockView = view.getData();
						
						Callback.CallbackResult<PresetLockViewDTO> result = new Callback.CallbackResult<PresetLockViewDTO>();
						result.setData(presetLockView);
						successcallback.execute(result);
					}else{
						AlertDialog.Builder dialog = UIUtil.getErrorDialog(PresetLockActivity.this,view.getInfo());
			    		
						dialog.show();
					}
					
				}
			}, 
			new DefaultVolleyErrorHandler(PresetLockActivity.this)
		);
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
		lockAppSettingItem.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Log.d(TAG, "app lock setting item click.");
				showPresetLockAppDialog();
			}

		});
		
		lockCallSwitch = (Switch)findViewById(R.id.lockCallSwitch);
		startTimeTextView = (TextView)findViewById(R.id.startTimeTextView);
		endTimeTextView = (TextView)findViewById(R.id.endTimeTextView);
		repeatTextView = (TextView)findViewById(R.id.repeatTextView);
		lockAppSettingTextView = (TextView)findViewById(R.id.lockAppSettingTextView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.preset_lock, menu);
		View actionView = menu.findItem(R.id.presetLockOnOffMenuItem).getActionView();
		
		presetOnOffSwitch = (Switch)actionView.findViewById(R.id.switchForActionBar);
		
		
		loadPresetLock(new Callback<PresetLockViewDTO>(){

			@Override
			public void execute(CallbackResult<PresetLockViewDTO> result) {
				presetLockView = result.getData();
				
				initPresetLock(presetLockView);
			}

			
		});
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
        
        TimePickerDialogFragment newFragment  = new TimePickerDialogFragment(startTime,new Callback<Date>(){

			@Override
			public void execute(CallbackResult<Date> result) {
				if( result != null && result.isSuccess() == true ){
					startTimeTextView.setText(result.getInfo());
					Toast.makeText(PresetLockActivity.this, "Set time:"+ result.getInfo(), Toast.LENGTH_SHORT).show();
					
					startTime = result.getData();
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
        TimePickerDialogFragment newFragment  = new TimePickerDialogFragment(endTime,new Callback<Date>(){

			@Override
			public void execute(CallbackResult<Date> result) {
				if( result != null && result.isSuccess() == true ){
					endTimeTextView.setText(result.getInfo());
					Toast.makeText(PresetLockActivity.this, "Set time:"+ result.getInfo(), Toast.LENGTH_SHORT).show();
					
					endTime = result.getData();
				}
			}
        		
        });
        
        newFragment.show(ft, "endTimePickerDialog");  
    }
	
	PresetLockAppDialogFragment presetLockAppDialogFragment ;
	private void showPresetLockAppDialog() {
		final FragmentTransaction ft = getFragmentManager().beginTransaction();
		if( presetLockAppDialogFragment == null ){
			loadAppList(new Callback<List<AppViewDTO>>(){

				@Override
				public void execute(CallbackResult<List<AppViewDTO>> result) {
					
					if( result.getData() != null && result.getData().size() > 0){
				        // Create and show the dialog.  
						presetLockAppDialogFragment  = new PresetLockAppDialogFragment(result.getData(),new Callback<List<Integer>>(){

							@Override
							public void execute(CallbackResult<List<Integer>> result) {
								checkedAppIdList = result.getData();
								Toast.makeText(PresetLockActivity.this, "App Lock Setting", Toast.LENGTH_SHORT).show();
							}

				        });
				        
				        presetLockAppDialogFragment.show(ft, "presetLockAppDialogFragment");  	
		    			
		    		}else{
		    			String msg = "Person account is not activate,please activate first.";
		    			
		    			AlertDialog.Builder dialog = UIUtil.getErrorDialog(PresetLockActivity.this,msg);
			    		
		    			dialog.show();	
		    		}
					
				}
				
			});
		}else{
			presetLockAppDialogFragment.show(ft, "presetLockAppDialogFragment");  
		}
		
	}
	
	private void showWeekdayChooserDialog() {
		FragmentTransaction ft = getFragmentManager().beginTransaction();  
        // Create and show the dialog.  
		WeekdayChooserDialogFragment newFragment  = new WeekdayChooserDialogFragment(new Callback<List<Boolean>>(){

			@Override
			public void execute(CallbackResult<List<Boolean>> result) {
				if( result != null && result.isSuccess() == true ){
					reapeat = result.getData();
					
					String selectedWeekday = getWeekdays(reapeat);
					repeatTextView.setText(selectedWeekday);
					
					Toast.makeText(PresetLockActivity.this, "Set repeat:"+ selectedWeekday, Toast.LENGTH_SHORT).show();
				}
			}

        });
        
        newFragment.show(ft, "weekdayChooserDialog");  		
	}
	
	private String getWeekdays(List<Boolean> weekdayList) {
		if( weekdayList == null || weekdayList.size() == 0 ){
			return "";
		}
		
		StringBuffer weekdays = new StringBuffer();
		if( weekdayList.get(0).equals(true) ){
			weekdays.append(WeekConstants.MONDAY[0]).append(" ");
		}
		if( weekdayList.get(1).equals(true) ){
			weekdays.append(WeekConstants.TUESDAY[0]).append(" ");
		}
		if( weekdayList.get(2).equals(true) ){
			weekdays.append(WeekConstants.WEDNESDAY[0]).append(" ");
		}
		if( weekdayList.get(3).equals(true) ){
			weekdays.append(WeekConstants.THURDAY[0]).append(" ");
		}
		if( weekdayList.get(4).equals(true) ){
			weekdays.append(WeekConstants.FRIDAY[0]).append(" ");
		}
		if( weekdayList.get(5).equals(true) ){
			weekdays.append(WeekConstants.SATURDAY[0]).append(" ");
		}
		if( weekdayList.get(6).equals(true) ){
			weekdays.append(WeekConstants.SUNDAY[0]).append(" ");
		}
		
		return weekdays.toString().trim();
	}


	@Override
	protected void onStop() {
		super.onStop();
	}


	@Override
	public void onBackPressed() {
		applyPresetLock(new Callback<ViewDTO<Boolean>>(){

			@Override
			public void execute(CallbackResult<ViewDTO<Boolean>> result) {
				if( result.getData().getMsg().equals(ViewDTO.MSG_SUCCESS) ){
					Toast.makeText(PresetLockActivity.this, "Success to apply preset lock.", Toast.LENGTH_SHORT).show();
					
					// TODO: save preset lock setting to local db
					//..................
					
					
					PresetLockActivity.super.onBackPressed();
				}
			}
			
		},
		new Callback(){
			@Override
			public void execute(CallbackResult result) {
				Toast.makeText(PresetLockActivity.this, "Sever error,failure to apply preset lock.", Toast.LENGTH_SHORT).show();
				
				PresetLockActivity.super.onBackPressed();				
			}
			
		});
		
//		super.onBackPressed();
	}


	private void applyPresetLock(final Callback<ViewDTO<Boolean>> successCallback, final Callback errorCallback) {
		String title = "Preset Lock";
		String msg = "Please wait...";
		showProgressDialog(title,msg);
		
		String url = Config.BASE_URL_MVC + RequestURLConstants.URL_APPLY_PRESET_LOCK;  

		Map<String,String> params = this.getApplyPresetLockParams();
		getRequestHelper().doPost(
			url,
			params,
			PresetLockActivity.this.getClass(),
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					final ViewDTO<Boolean> view = JSONUtil.getApplyPresetLockView(response);
					
					Callback.CallbackResult<ViewDTO<Boolean>> result = new Callback.CallbackResult<ViewDTO<Boolean>>();
					result.setData(view);
					successCallback.execute(result);
					/*if( view.getMsg().equals(ViewDTO.MSG_SUCCESS) ){
						
					}else{
						Toast.makeText(PresetLockActivity.this, "Failure to apply preset lock.", Toast.LENGTH_SHORT);
					}*/
				}
			}, 
			new Response.ErrorListener(){

				@Override
				public void onErrorResponse(VolleyError arg0) {
					errorCallback.execute(null);					
				}
				
			});		
	}


	private Map<String, String> getApplyPresetLockParams() {
		Map<String, String> param = new HashMap<String,String>();
		param.put("childId", childId.toString());
		
		ApplyPresetLockParam applyPresetLockParam = new ApplyPresetLockParam();
		
		boolean lockCallStatus = UIUtil.getSwitchValue( lockCallSwitch );
		applyPresetLockParam.setLockCallStatus(lockCallStatus);
		
		boolean presetOnOff = UIUtil.getSwitchValue( presetOnOffSwitch );
		applyPresetLockParam.setPresetOnOff(presetOnOff);
		
		applyPresetLockParam.setAppIdList(checkedAppIdList);
		applyPresetLockParam.setEndTime(endTime);
		applyPresetLockParam.setReapeat(reapeat);
		applyPresetLockParam.setStartTime(startTime);
		
		String applyPresetLockParamJson = JSONUtil.transApplyPresetLockParam2String(applyPresetLockParam);
		param.put("applyPresetLockParamJson", applyPresetLockParamJson);
		return param;
	}
	
	
	private void loadAppList(final Callback<List<AppViewDTO>> successCallback) {
		String title = "Preset Lock";
		String msg = "Loading applications, please wait...";
		showProgressDialog(title,msg);
		
		String url = String.format(
				Config.BASE_URL_MVC + RequestURLConstants.URL_LIST_CHILD_APP + "?childId=%1$s",  
				childId
				);  
		
		getRequestHelper().doGet(
			url,
			PresetLockActivity.this.getClass(),
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					dismissProgressDialog();
					ViewDTO<List<AppViewDTO>> view = JSONUtil.getListChildAppView(response);
			    	
			    	if( view.getMsg().equals(ViewDTO.MSG_SUCCESS)){
			    		Callback.CallbackResult<List<AppViewDTO>> result = new Callback.CallbackResult<List<AppViewDTO>>();
			    		result.setData(view.getData());
			    		successCallback.execute(result);;
			    		
			    	}else{
			    		AlertDialog.Builder dialog = UIUtil.getErrorDialog(PresetLockActivity.this,view.getInfo());
			    		
						dialog.show();
			    	}
			    	
				}
			}, 
			new DefaultVolleyErrorHandler(PresetLockActivity.this)
		);		
	}
	
	
}
