package eden.sun.childrenguard.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import eden.sun.childrenguard.server.dto.LoginViewDTO;
import eden.sun.childrenguard.server.dto.RegisterViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;


public class JSONUtil {

	public static ViewDTO<RegisterViewDTO> getRegisterView(String json) {
		return new Gson().fromJson(json, new TypeToken<ViewDTO<RegisterViewDTO>>(){}.getType());
	}

	public static ViewDTO<LoginViewDTO> getLoginView(String json) {
		return new Gson().fromJson(json, new TypeToken<ViewDTO<LoginViewDTO>>(){}.getType());
	}

	public static ViewDTO<String> getPasswordResetView(String json) {
		return new Gson().fromJson(json, new TypeToken<ViewDTO<String>>(){}.getType());
	}

}
