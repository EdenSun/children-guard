package eden.sun.childrenguard.server.cometd;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.util.JSONUtil;

public class BaseCometService {
	protected Logger logger = Logger.getLogger(this.getClass());
	
	protected String toJson(ViewDTO view) {
		return JSONUtil.toJson(view);
	}
}
