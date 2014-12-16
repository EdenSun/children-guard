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
	
	/**
	 * /parent/reset/passwordReset
	 * �������룺 ����parent email��reset code�ᱻ���͵��ֻ��ˣ�reset code ������һ�������ò���
	 * @param email parent��email
	 * @return ���������ش�����Ϣ
	 */
	@RequestMapping("/passwordReset")
	@ResponseBody
	public ViewDTO<String> passwordReset(String email){
		logger.info("password reset email:" + email );
		
		ViewDTO<String> view = authService.resetPasswordByMail(email);
		
		return view;
	}
	
	/**
	 * /parent/reset/doChangePassword
	 * �޸����� 
	 * @param imei parent��imei
	 * @param resetCode reset code
	 * @param password ������
	 * @return �������ش�����Ϣ
	 */
	@RequestMapping("/doChangePassword")
	@ResponseBody
	public ViewDTO<String> doChangePassword(String imei,String resetCode,String password){
		logger.info("doChangePassword called. imei:" + imei + ", resetCode:" + resetCode + ", password:" + password);
		
		ViewDTO<String> view = authService.changePassword(imei,resetCode,password);
		
		return view;
	}
	
}
