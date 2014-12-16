package eden.sun.childrenguard.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eden.sun.childrenguard.server.dto.IsFirstLoginViewDTO;
import eden.sun.childrenguard.server.dto.LoginViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.service.IAuthService;

@Controller
@RequestMapping("/parent/auth")
public class LoginController extends BaseController{

	@Autowired
	private IAuthService authService;
	
	/**
	 * /parent/auth/login
	 * parent µÇÂ¼ 
	 * @param email ÓÊÏä
	 * @param password µÇÂ¼ÃÜÂë
	 * @return ·µ»ØµÇÂ¼¶ÔÏóLoginViewDTO
	 */
	@RequestMapping("/login")
	@ResponseBody
	public ViewDTO<LoginViewDTO> login(String email,String password){
		//logger.info("user login:" + email );
		ViewDTO<LoginViewDTO> view = authService.login(email,password);
		
		return view;
	}
	
	/**
	 * /parent/auth/isFirstLogin
	 * @param email µÇÂ¼ÓÊÏä
	 * @param password µÇÂ¼ÃÜÂë
	 * @return ·µ»ØÊÇ·ñÊÇµÚÒ»´ÎµÇÂ¼IsFirstLoginViewDTO
	 */
	@RequestMapping("/isFirstLogin")
	@ResponseBody
	public ViewDTO<IsFirstLoginViewDTO> isFristLogin(String email,String password) {
		//logger.info("user email:" + email );
		
		ViewDTO<IsFirstLoginViewDTO> view = authService.isFirstLogin(email,password);
		
		return view;
	}
}
