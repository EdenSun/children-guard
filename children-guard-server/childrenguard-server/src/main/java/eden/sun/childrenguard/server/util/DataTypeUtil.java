package eden.sun.childrenguard.server.util;

public class DataTypeUtil {

	public static boolean getBooleanValue(Boolean val) {
		if( val != null && val.booleanValue() == true ){
			return true;
		}else {
			return false;
		}
	}

}
