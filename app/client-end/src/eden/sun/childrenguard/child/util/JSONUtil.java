package eden.sun.childrenguard.child.util;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import eden.sun.childrenguard.server.dto.AppViewDTO;
import eden.sun.childrenguard.server.dto.ChildInfoViewDTO;
import eden.sun.childrenguard.server.dto.ChildSettingViewDTO;
import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.UploadApplicationInfoParam;

public class JSONUtil {
	private static Gson gson = buildGson();
	
	private static Gson buildGson() {
		return new GsonBuilder()
					.setDateFormat("yyyy-MM-dd HH:mm:ss")
					.create();	
	}
	
	public static ViewDTO<Boolean> getUploadAllAppInfoView(String json) {
		return gson.fromJson(json, new TypeToken<ViewDTO<Boolean>>(){}.getType());
	}

	public static String transUploadApplicationInfoParamList2String(
			List<UploadApplicationInfoParam> requestParam) {
		return gson.toJson(requestParam);
	}

	public static ViewDTO<ChildViewDTO> getChildLoginView(String json) {
		return gson.fromJson(json, new TypeToken<ViewDTO<ChildViewDTO>>(){}.getType());
	}

	public static ViewDTO<ChildViewDTO> getIsActivateView(String json) {
		return gson.fromJson(json, new TypeToken<ViewDTO<ChildViewDTO>>(){}.getType());
	}

	public static ViewDTO<Boolean> getActivateAccountView(String json) {
		return gson.fromJson(json, new TypeToken<ViewDTO<Boolean>>(){}.getType());
	}

	public static ViewDTO<ChildViewDTO> getDoLoginView(String json) {
		return gson.fromJson(json, new TypeToken<ViewDTO<ChildViewDTO>>(){}.getType());
	}

	public static ViewDTO<List<AppViewDTO>> getListChildAppInfoView(
			String json) {
		return gson.fromJson(json, new TypeToken<ViewDTO<List<AppViewDTO>>>(){}.getType());
	}

	public static ViewDTO<AppViewDTO> getInstallAppView(String json) {
		return gson.fromJson(json, new TypeToken<ViewDTO<AppViewDTO>>(){}.getType());
	}

	public static ViewDTO<Boolean> getSaveRegistionIdView(String json) {
		return gson.fromJson(json, new TypeToken<ViewDTO<Boolean>>(){}.getType());
	}

	public static ViewDTO<ChildInfoViewDTO> getRetrieveChildInfoView(
			String json) {
		return gson.fromJson(json, new TypeToken<ViewDTO<ChildInfoViewDTO>>(){}.getType());
	}

	public static ViewDTO<ChildSettingViewDTO> getRetrieveChildSettingView(
			String json) {
		return gson.fromJson(json, new TypeToken<ViewDTO<ChildSettingViewDTO>>(){}.getType());
	}
	
	

}
