package com.soloappinfo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static String dateToString(Date date) {
		if( date == null ){
			return null;
		}
		
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return df.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static Date str2Date(String dateStr) {
		if( dateStr == null ){
			return null;
		}
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			return df.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
	}

}
