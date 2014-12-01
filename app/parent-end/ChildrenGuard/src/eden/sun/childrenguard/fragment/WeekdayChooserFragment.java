package eden.sun.childrenguard.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import eden.sun.childrenguard.R;
import eden.sun.childrenguard.util.Callback;

public class WeekdayChooserFragment extends DialogFragment {
	private Callback onWeekdayChoosed;
	private String[] weekdays = new String[]{
		"Mon.","Tues.","Wed.",
		"Thur.","Fri.","Sat.",
		"Sun."
	};
	public WeekdayChooserFragment(Callback onWeekdayChoosed) {
		super();
		this.onWeekdayChoosed = onWeekdayChoosed;
	}

    
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater mInflater = LayoutInflater.from(getActivity());  
        View v = mInflater.inflate(R.layout.fragment_weekday_chooser,null);  
        return new AlertDialog.Builder(getActivity())  
                .setTitle("Repeat")  
                .setView(v)  
                .setPositiveButton("Confirm",  
                    new DialogInterface.OnClickListener() {  
                        public void onClick(DialogInterface dialog, int whichButton) { 
                        	Callback.CallbackResult result = new Callback.CallbackResult();
                        	result.setSuccess(true);
                        	result.setInfo(weekdays[0] + "," + weekdays[1] + "," + weekdays[2]);
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

}
