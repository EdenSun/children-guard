package eden.sun.childrenguard.util;

public class DataTypeUtil {

	public static Boolean getNonNullBoolean(Boolean booleanVal) {
		if( booleanVal == null ){
			return false;
		}
		
		return booleanVal.booleanValue();
	}

	public static String int2String(Integer intVal, String defaultVal) {
		if( intVal == null ){
			return defaultVal;
		}
		
		try {
			return String.valueOf(intVal);
		} catch (Exception e) {
			return defaultVal;
		}
	}

}
