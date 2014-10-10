package eden.sun.childrenguard.util;

public class StringUtil {

	public static boolean isBlank(String firstName) {
		if( firstName == null || firstName.trim().length() == 0 ){
			return true;
		}
		return false;
	}

}
