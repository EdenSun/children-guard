package eden.sun.childrenguard.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.service.IAuthService;

@Controller
@RequestMapping("/parent/reset")
public class ResetPasswordController extends BaseController{

	@Autowired
	private IAuthService authService;
	
	@RequestMapping("/passwordReset")
	@ResponseBody
	public ViewDTO<String> passwordReset(String email){
		logger.info("password reset email:" + email );
		
		ViewDTO<String> view = authService.resetPasswordByMail(email);
		
		return view;
	}
	
}
