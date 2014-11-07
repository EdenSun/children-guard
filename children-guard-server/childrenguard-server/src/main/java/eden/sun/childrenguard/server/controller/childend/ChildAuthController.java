package eden.sun.childrenguard.server.controller.childend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eden.sun.childrenguard.server.controller.BaseController;
import eden.sun.childrenguard.server.dto.ChildLoginViewDTO;
import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.service.IChildAuthService;

@Controller
@RequestMapping("/app/child/auth")
public class ChildAuthController extends BaseController{
	@Autowired
	private IChildAuthService childAuthService;
	
	@RequestMapping("/doLogin")
	@ResponseBody
	public ViewDTO<ChildViewDTO> doLogin(String imei){
		logger.info("child doLogin.imei:" + imei);
		ViewDTO<ChildViewDTO> view = childAuthService.login(imei);
		
		return view;
	}
}
