package eden.sun.childrenguard.server.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eden.sun.childrenguard.server.dto.IsFirstLoginViewDTO;
import eden.sun.childrenguard.server.dto.LoginViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.service.IAuthService;

@Controller
@RequestMapping("/auth")
public class LoginController extends BaseController{

	@Inject
	private IAuthService authService;
	
	@RequestMapping("/login")
	@ResponseBody
	public ViewDTO<LoginViewDTO> login(String email,String password){
		//logger.info("user login:" + email );
		ViewDTO<LoginViewDTO> view = authService.login(email,password);
		
		return view;
	}
	
	@RequestMapping("/isFirstLogin")
	@ResponseBody
	public ViewDTO<IsFirstLoginViewDTO> isFristLogin(String email,String password) {
		//logger.info("user email:" + email );
		
		ViewDTO<IsFirstLoginViewDTO> view = authService.isFirstLogin(email,password);
		
		return view;
	}
}
