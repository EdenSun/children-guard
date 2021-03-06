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
	 * parent ��¼ 
	 * @param mobile ����
	 * @param password ��¼����
	 * @return ���ص�¼����LoginViewDTO
	 */
	@RequestMapping("/login")
	@ResponseBody
	public ViewDTO<LoginViewDTO> login(String mobile,String password){
		//logger.info("user login:" + email );
		ViewDTO<LoginViewDTO> view = authService.login(mobile,password);
		
		return view;
	}
	
	/**
	 * /parent/auth/isFirstLogin
	 * @param mobile ��¼�ֻ�
	 * @param password ��¼����
	 * @return �����Ƿ��ǵ�һ�ε�¼IsFirstLoginViewDTO
	 */
	@RequestMapping("/isFirstLogin")
	@ResponseBody
	public ViewDTO<IsFirstLoginViewDTO> isFristLogin(String mobile,String password) {
		//logger.info("user email:" + email );
		
		ViewDTO<IsFirstLoginViewDTO> view = authService.isFirstLogin(mobile,password);
		
		return view;
	}
}
