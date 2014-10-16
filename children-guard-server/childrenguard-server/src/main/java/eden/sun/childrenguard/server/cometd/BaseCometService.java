package eden.sun.childrenguard.server.cometd;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import eden.sun.childrenguard.server.dto.ViewDTO;

public class BaseCometService {
	protected Logger logger = Logger.getLogger(this.getClass());
	
	protected String toJson(ViewDTO view) {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString(view);
			logger.info(json);
			
		} catch (Exception e) {
			logger.error("convert json error",e);
		}
		return json;
	}
}
