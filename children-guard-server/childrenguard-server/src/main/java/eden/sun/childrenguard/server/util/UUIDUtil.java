package eden.sun.childrenguard.server.util;

import java.util.UUID;

public class UUIDUtil {

	public static String generateUUID() {
		UUID uuid = java.util.UUID.randomUUID();
		return uuid.toString();
	}
	

}
