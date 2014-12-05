package eden.sun.childrenguard.fragment;

import java.util.Calendar;
import java.util.Date;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;
import eden.sun.childrenguard.util.Callback;

public class TimePickerDialogFragment extends DialogFragment /*implements
		DatePickerDialog.OnDateSetListener*/ {
	private int pHour;
    private int pMinute;
	
	private Callback<Date> onTimeSelected;
	
	public TimePickerDialogFragment(Date time,Callback<Date> onTimeSelected) {
		super();
		this.onTimeSelected = onTimeSelected;
		
		if( time != null ){
			Calendar cal = Calendar.getInstance();
			cal.setTime(time);
			pHour = cal.get(Calendar.HOUR_OF_DAY);
			pMinute = cal.get(Calendar.MINUTE);
		}
	}

	/** Callback received when the user "picks" a time in the dialog */
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
        new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            	if (view.isShown()) {
            		pHour = hourOfDay;
                    pMinute = minute;
                    updateDisplay();
                }
            }
        };
        
        
    /** Updates the time in the TextView */
    private void updateDisplay() {
    	Callback.CallbackResult<Date> result = new Callback.CallbackResult<Date>();
    	result.setSuccess(true);
    	
    	Calendar cal = Calendar.getInstance();
    	cal.set(Calendar.HOUR_OF_DAY, pHour);
    	cal.set(Calendar.MINUTE, pMinute);
    	result.setData(cal.getTime());
    	result.setInfo(new StringBuilder()
				        .append(pad(pHour)).append(":")
				        .append(pad(pMinute)).toString());
    	
    	onTimeSelected.execute(result);
    }
     

    /** Add padding to numbers less than ten */
    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }
    
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Create a new instance of DatePickerDialog and return it
		return new TimePickerDialog(getActivity(),
                mTimeSetListener, pHour, pMinute, false);
	}

	/*@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		Calendar c = Calendar.getInstance();
		c.set(year, month, day);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = sdf.format(c.getTime());
	}*/
}