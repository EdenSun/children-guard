package eden.sun.childrenguard.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eden.sun.childrenguard.server.dto.RegisterViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.service.IAuthService;

@Controller
@RequestMapping("/parent/reg")
public class RegisterController extends BaseController{

	@Autowired
	private IAuthService authService;
	
	
	/**
	 * /parent/reg/register
	 * parent ע��
	 * @param imei parent�ֻ���imei
	 * @param firstName first name
	 * @param lastName last name
	 * @param email parent ����
	 * @param password ����
	 * @return ����ע�����RegisterViewDTO
	 */
	@RequestMapping("/register")
	@ResponseBody
	public ViewDTO<RegisterViewDTO> register(String imei,String mobile,String password){
		logger.info("Register(" + imei + ",mobile:" + mobile + ",pwd:" + password + ")");
		
		ViewDTO<RegisterViewDTO> view = authService.register(imei,mobile,password);
		return view;
	}
	
	
	/**
	 * /parent/reg/register
	 * parent ע��
	 * @param imei parent�ֻ���imei
	 * @param firstName first name
	 * @param lastName last name
	 * @param email parent ����
	 * @param password ����
	 * @return ����ע�����RegisterViewDTO
	 *//*
	@RequestMapping("/register")
	@ResponseBody
	public ViewDTO<RegisterViewDTO> register(String imei,String firstName,String lastName,String email,String password){
		logger.info("Register(" + imei + "," + firstName + "," + lastName + "," + email + "," + password + ")");
		
		ViewDTO<RegisterViewDTO> view = authService.register(imei,firstName,lastName,email,password);
		return view;
	}*/
	
}
