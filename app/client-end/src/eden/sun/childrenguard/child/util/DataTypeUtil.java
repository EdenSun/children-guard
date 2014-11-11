package eden.sun.childrenguard.child.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.text.format.DateFormat;

public class DataTypeUtil {

	public static Boolean stringToBoolean(String booleanStr) {
		if( booleanStr == null ){
			return null;
		}
		
		return Boolean.valueOf(booleanStr);
	}

	public static Date stringToDate(String dateTimeStr) {
		if( dateTimeStr == null ){
			return null;
		}
		
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

			return df.parse(dateTimeStr);
		} catch (ParseException e) {
			return null;
		}
	}
	
}
