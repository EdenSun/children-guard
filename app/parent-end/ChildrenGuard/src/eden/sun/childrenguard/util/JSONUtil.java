package eden.sun.childrenguard.util;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import eden.sun.childrenguard.server.dto.AppViewDTO;
import eden.sun.childrenguard.server.dto.ChildBasicInfoViewDTO;
import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.dto.EmergencyContactViewDTO;
import eden.sun.childrenguard.server.dto.IsFirstLoginViewDTO;
import eden.sun.childrenguard.server.dto.LoginViewDTO;
import eden.sun.childrenguard.server.dto.RegisterViewDTO;
import eden.sun.childrenguard.server.dto.RelationshipViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;


public class JSONUtil {
	private static Gson gson = buildGson();
	
	private static Gson buildGson() {
		return new GsonBuilder()
					.setDateFormat("yyyy-MM-dd HH:mm:ss")
					.create();	
	}
	
	public static ViewDTO<RegisterViewDTO> getRegisterView(String json) {
		return gson.fromJson(json, new TypeToken<ViewDTO<RegisterViewDTO>>(){}.getType());
	}

	public static ViewDTO<LoginViewDTO> getLoginView(String json) {
		return gson.fromJson(json, new TypeToken<ViewDTO<LoginViewDTO>>(){}.getType());
	}

	public static ViewDTO<String> getPasswordResetView(String json) {
		return gson.fromJson(json, new TypeToken<ViewDTO<String>>(){}.getType());
	}

	public static ViewDTO<IsFirstLoginViewDTO> getIsFirstLoginView(String json) {
		return gson.fromJson(json, new TypeToken<ViewDTO<IsFirstLoginViewDTO>>(){}.getType());
	}

	public static ViewDTO<List<ChildViewDTO>> getListMyChildrenView(
			String json) {
		return gson.fromJson(json, new TypeToken<ViewDTO<List<ChildViewDTO>>>(){}.getType());
	}

	public static ViewDTO<List<RelationshipViewDTO>> getRelationshipViewDTO(
			String json) {
		return gson.fromJson(json, new TypeToken<ViewDTO<List<RelationshipViewDTO>>>(){}.getType());
	}

	public static ViewDTO<ChildViewDTO> getAddChildViewDTO(
			String json) {
		return gson.fromJson(json, new TypeToken<ViewDTO<ChildViewDTO>>(){}.getType());
	}

	public static ViewDTO<ChildBasicInfoViewDTO> getChildBasicInfoView(
			String json) {
		return gson.fromJson(json, new TypeToken<ViewDTO<ChildBasicInfoViewDTO>>(){}.getType());
	}

	public static ViewDTO<List<AppViewDTO>> getListChildAppView(String json) {
		return gson.fromJson(json, new TypeToken<ViewDTO<List<AppViewDTO>>>(){}.getType());
	}

	public static ViewDTO<Boolean> getChangeLockPasswordView(String json) {
		return gson.fromJson(json, new TypeToken<ViewDTO<Boolean>>(){}.getType());
	}

	public static ViewDTO<Boolean> getSaveRegistionIdView(String json) {
		return gson.fromJson(json, new TypeToken<ViewDTO<Boolean>>(){}.getType());
	}

	public static ViewDTO<List<EmergencyContactViewDTO>> getListEmergencyContactByChildView(
			String json) {
		return gson.fromJson(json, new TypeToken<ViewDTO<List<EmergencyContactViewDTO>>>(){}.getType());
	}

}
