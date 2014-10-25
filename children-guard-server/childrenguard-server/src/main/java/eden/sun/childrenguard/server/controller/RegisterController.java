package eden.sun.childrenguard.server.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eden.sun.childrenguard.server.dto.RegisterViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.service.IAuthService;

@Controller
@RequestMapping("/reg")
public class RegisterController extends BaseController{

	@Inject
	private IAuthService authService;
	
	@RequestMapping("/register")
	@ResponseBody
	public ViewDTO<RegisterViewDTO> register(String firstName,String lastName,String email,String password){
		logger.info("Register(" + firstName + "," + lastName + "," + email + "," + password + ")");
		
		ViewDTO<RegisterViewDTO> view = authService.register(firstName,lastName,email,password);
		return view;
	}
	
}
