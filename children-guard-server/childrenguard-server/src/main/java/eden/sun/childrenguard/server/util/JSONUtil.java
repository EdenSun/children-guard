package eden.sun.childrenguard.server.util;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import eden.sun.childrenguard.server.dto.ViewDTO;

public class JSONUtil {
	private static Logger logger = Logger.getLogger(JSONUtil.class);

	public static String toJson(ViewDTO view) {
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

	public static String getErrorViewDTO(String info) {
		ViewDTO<String> view = new ViewDTO<String>();
		view.setData("");
		view.setInfo(info);
		view.setMsg(ViewDTO.MSG_ERROR);
		return toJson(view);
	}
	
}
