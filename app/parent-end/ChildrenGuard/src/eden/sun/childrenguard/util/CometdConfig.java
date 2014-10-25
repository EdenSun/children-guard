package eden.sun.childrenguard.util;

public class CometdConfig {
	//public static final String COMETD_URL = Config.BASE_URL + "cometd";
	//public static final String COMETD_URL = "http://192.168.2.101:8080/childrenguard-server/cometd";
	
	/* service channel */
	public static final String LOGIN_CHANNEL = "/service/login";
	public static final String REGISTER_CHANNEL = "/service/register";
	public static final String IS_FIRST_LOGIN_CHANNEL = "/service/isFirstLogin";
	/* END - service channel */
	
	/* meta channel */
	public static final String DISCONNECT_CHANNEL = "/meta/disconnect";
	/* END - meta channel */
	
	public static final String PASSWORD_RESET_CHANNEL = "/service/passwordReset";


	public static final String SUBSCRIBE_CHANNEL = "/meta/subscribe";


	
	
}
