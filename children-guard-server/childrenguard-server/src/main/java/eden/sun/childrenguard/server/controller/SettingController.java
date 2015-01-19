package eden.sun.childrenguard.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.service.ISettingService;

@Controller
@RequestMapping("/parent/setting")
public class SettingController extends BaseController{
	@Autowired
	private ISettingService settingService;
	
	@RequestMapping("/saveEmail")
	@ResponseBody
	public ViewDTO<Boolean> saveEmail(String accessToken,String email){
		logger.info("saveEmail called. accessToken:" + accessToken + ",email:" + email);
		
		ViewDTO<Boolean> view = settingService.saveEmail(accessToken,email);
		return view;
	}
	
}
