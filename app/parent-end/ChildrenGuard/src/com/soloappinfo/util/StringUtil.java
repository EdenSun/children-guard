package com.soloappinfo.util;

public class StringUtil {

	public static boolean isBlank(String string) {
		if( string == null || string.trim().length() == 0 ){
			return true;
		}
		return false;
	}

}
