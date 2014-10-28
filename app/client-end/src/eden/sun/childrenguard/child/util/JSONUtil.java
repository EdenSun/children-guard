package eden.sun.childrenguard.child.util;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.UploadApplicationInfoParam;

public class JSONUtil {
	public static ViewDTO<Boolean> getUploadAllAppInfoView(String json) {
		return new Gson().fromJson(json, new TypeToken<ViewDTO<Boolean>>(){}.getType());
	}

	public static String transUploadApplicationInfoParamList2String(
			List<UploadApplicationInfoParam> requestParam) {
		return new Gson().toJson(requestParam);
	}

	public static ViewDTO<ChildViewDTO> getChildLoginView(String json) {
		return new Gson().fromJson(json, new TypeToken<ViewDTO<ChildViewDTO>>(){}.getType());
	}

}
