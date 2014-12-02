package eden.sun.childrenguard.server.util;

import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.AppManageSettingParam;
import eden.sun.childrenguard.server.dto.param.ApplyPresetLockParam;
import eden.sun.childrenguard.server.dto.param.MoreSettingParam;
import eden.sun.childrenguard.server.dto.param.UploadApplicationInfoParam;

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

	public static List<UploadApplicationInfoParam> getUploadApplicationInfoParamList(
			String appListJson) {
		return new Gson().fromJson(appListJson, new TypeToken<List<UploadApplicationInfoParam>>(){}.getType());
	}

	public static List<MoreSettingParam> getMoreSettingParamList(
			String settingInfoJson) {
		return new Gson().fromJson(settingInfoJson, new TypeToken<List<MoreSettingParam>>(){}.getType());
	}

	public static List<AppManageSettingParam> getAppManageSettingParamList(
			String settingInfoJson) {
		return new Gson().fromJson(settingInfoJson, new TypeToken<List<AppManageSettingParam>>(){}.getType());
	}

	public static ApplyPresetLockParam getApplyPresetLockParam(
			String json) {
		return new Gson().fromJson(json, new TypeToken<ApplyPresetLockParam>(){}.getType());
	}
	
}
