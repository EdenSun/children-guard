package eden.sun.childrenguard.fragment;

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
import eden.sun.childrenguard.R;
import eden.sun.childrenguard.util.Callback;
import eden.sun.childrenguard.util.WeekConstants;

public class WeekdayChooserFragment extends DialogFragment {
	private Callback<List<String>> onWeekdayChoosed;
	
	private CheckBox mondayCheckbox;
	private CheckBox tuesdayCheckbox;
	private CheckBox wednesdayCheckbox;
	private CheckBox thurdayCheckbox;
	private CheckBox fridayCheckbox;
	private CheckBox saturdayCheckbox;
	private CheckBox sundayCheckbox;
	
	public WeekdayChooserFragment(Callback<List<String>> onWeekdayChoosed) {
		super();
		this.onWeekdayChoosed = onWeekdayChoosed;
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
        return new AlertDialog.Builder(getActivity())  
                .setTitle("Repeat")  
                .setView(v)  
                .setPositiveButton("Confirm",  
                    new DialogInterface.OnClickListener() {  
                        public void onClick(DialogInterface dialog, int whichButton) { 
                        	Callback.CallbackResult<List<String>> result = new Callback.CallbackResult<List<String>>();
                        	result.setSuccess(true);
                        	List<String> weekList = getSelectedWeekList();
                        	result.setData(weekList);
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
	
	private List<String> getSelectedWeekList() {
		List<String> weekList = new ArrayList<String>();
		if( mondayCheckbox.isChecked() ){
			weekList.add(WeekConstants.MONDAY[1]);
		}
		
		if( tuesdayCheckbox.isChecked() ){
			weekList.add(WeekConstants.TUESDAY[1]);
		}
		
		if( wednesdayCheckbox.isChecked() ){
			weekList.add(WeekConstants.WEDNESDAY[1]);
		}
		
		if( thurdayCheckbox.isChecked() ){
			weekList.add(WeekConstants.THURDAY[1]);
		}
		
		if( fridayCheckbox.isChecked() ){
			weekList.add(WeekConstants.FRIDAY[1]);
		}
		
		if( saturdayCheckbox.isChecked() ){
			weekList.add(WeekConstants.SATURDAY[1]);
		}
		
		if( sundayCheckbox.isChecked() ){
			weekList.add(WeekConstants.SUNDAY[1]);
		}

		return weekList;
	}  

}
