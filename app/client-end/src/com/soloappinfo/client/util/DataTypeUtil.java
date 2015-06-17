package com.soloappinfo.client.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	
	public static Boolean getNonNullBoolean(Boolean booleanVal) {
		if( booleanVal == null ){
			return false;
		}
		
		return booleanVal.booleanValue();
	}

	public static Boolean str2NonNullBoolean(String booleanStr) {
		try {
			if( booleanStr != null && (Boolean.parseBoolean(booleanStr) == true || "1".equals(booleanStr) )){
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}
	
}
