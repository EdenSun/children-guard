package com.soloappinfo.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;

import com.soloappinfo.R;
import com.soloappinfo.util.Callback;
import com.soloappinfo.util.DataTypeUtil;

public class WeekdayChooserDialogFragment extends DialogFragment {
	private Callback<List<Boolean>> onWeekdayChoosed;
	
	private CheckBox mondayCheckbox;
	private CheckBox tuesdayCheckbox;
	private CheckBox wednesdayCheckbox;
	private CheckBox thurdayCheckbox;
	private CheckBox fridayCheckbox;
	private CheckBox saturdayCheckbox;
	private CheckBox sundayCheckbox;
	
	private List<Boolean> repeat;
	
	public WeekdayChooserDialogFragment(List<Boolean> repeat,Callback<List<Boolean>> onWeekdayChoosed) {
		super();
		this.onWeekdayChoosed = onWeekdayChoosed;
		
		this.repeat = repeat;
	}

    
	private void initWeekdayCheckbox(List<Boolean> repeat) {
		if( repeat != null && repeat.size() == 7 ){
			mondayCheckbox.setChecked(DataTypeUtil.getNonNullBoolean(repeat.get(0)));
			tuesdayCheckbox.setChecked(DataTypeUtil.getNonNullBoolean(repeat.get(1)));
			wednesdayCheckbox.setChecked(DataTypeUtil.getNonNullBoolean(repeat.get(2)));
			thurdayCheckbox.setChecked(DataTypeUtil.getNonNullBoolean(repeat.get(3)));
			fridayCheckbox.setChecked(DataTypeUtil.getNonNullBoolean(repeat.get(4)));
			saturdayCheckbox.setChecked(DataTypeUtil.getNonNullBoolean(repeat.get(5)));
			sundayCheckbox.setChecked(DataTypeUtil.getNonNullBoolean(repeat.get(6)));
		}
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}


	private void initComponent(View v) {
		mondayCheckbox = (CheckBox)v.findViewById(R.id.mondayCheckbox);
		tuesdayCheckbox = (CheckBox)v.findViewById(R.id.tuesdayCheckbox);
		wednesdayCheckbox = (CheckBox)v.findViewById(R.id.wednesdayCheckbox);
		thurdayCheckbox = (CheckBox)v.findViewById(R.id.thurdayCheckbox);
		fridayCheckbox = (CheckBox)v.findViewById(R.id.fridayCheckbox);
		saturdayCheckbox = (CheckBox)v.findViewById(R.id.saturdayCheckbox);
		sundayCheckbox = (CheckBox)v.findViewById(R.id.sundayCheckbox);
	}


	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater mInflater = LayoutInflater.from(getActivity());  
        View v = mInflater.inflate(R.layout.fragment_weekday_chooser,null);  
        
        initComponent(v);
        initWeekdayCheckbox(repeat);
        
        return new AlertDialog.Builder(getActivity())  
                .setTitle("Repeat")  
                .setView(v)  
                .setPositiveButton("Confirm",  
                    new DialogInterface.OnClickListener() {  
                        public void onClick(DialogInterface dialog, int whichButton) { 
                        	Callback.CallbackResult<List<Boolean>> result = new Callback.CallbackResult<List<Boolean>>();
                        	result.setSuccess(true);
                        	List<Boolean> repeat = getRepeat();
                        	result.setData(repeat);
                        	onWeekdayChoosed.execute(result);
                        }

                    }  
                )  
                .setNegativeButton("Cancel",  
                    new DialogInterface.OnClickListener() {  
                        public void onClick(DialogInterface dialog, int whichButton) {  
                        	dialog.dismiss();
                        }  
                    }  
                )  
                .create();  
        
	}
	
	private List<Boolean> getRepeat() {
		List<Boolean> weekList = new ArrayList<Boolean>();
		if( mondayCheckbox.isChecked() ){
			weekList.add(true);
		}else{
			weekList.add(false);
		}
		
		if( tuesdayCheckbox.isChecked() ){
			weekList.add(true);
		}else{
			weekList.add(false);
		}
		
		if( wednesdayCheckbox.isChecked() ){
			weekList.add(true);
		}else{
			weekList.add(false);
		}
		
		if( thurdayCheckbox.isChecked() ){
			weekList.add(true);
		}else{
			weekList.add(false);
		}
		
		if( fridayCheckbox.isChecked() ){
			weekList.add(true);
		}else{
			weekList.add(false);
		}
		
		if( saturdayCheckbox.isChecked() ){
			weekList.add(true);
		}else{
			weekList.add(false);
		}
		
		if( sundayCheckbox.isChecked() ){
			weekList.add(true);
		}else{
			weekList.add(false);
		}

		return weekList;
	}  

}
