package eden.sun.childrenguard.util;

public class CometdConfig {
	public static final String COMETD_URL = "http://169.7.231.122:8080/childrenguard-server/cometd";
//	public static final String COMETD_URL = "http://192.168.2.110:8080/childrenguard-server/cometd";
	
	
	public static final String LOGIN_CHANNEL = "/service/login";
	public static final String REGISTER_CHANNEL = "/service/register";

	/* meta channel */
	public static final String DISCONNECT_CHANNEL = "/meta/disconnect";
	/* END - meta channel */
	
	public static final String PASSWORD_RESET_CHANNEL = "/service/passwordReset";


	
	
}
