package eden.sun.childrenguard.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexHelper {
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);

		return m.matches();
	}
	
	public static boolean isValidPassword(String password) {
		//String str = "^[A-Za-z0-9]{6,8}$";
		
		String str = "^[A-Za-z0-9]{4}$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(password);

		return m.matches();
	}
	
}
