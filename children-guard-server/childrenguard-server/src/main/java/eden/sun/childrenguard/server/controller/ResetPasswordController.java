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
	 * 重置密码： 传入parent email，reset code会被推送到手机端，reset code 用于下一步的重置操作
	 * @param email parent的email
	 * @return 若出错，返回错误信息
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
	 * 修改密码 
	 * @param imei parent的imei
	 * @param resetCode reset code
	 * @param password 新密码
	 * @return 若出错返回错误信息
	 */
	@RequestMapping("/doChangePassword")
	@ResponseBody
	public ViewDTO<String> doChangePassword(String imei,String resetCode,String password){
		logger.info("doChangePassword called. imei:" + imei + ", resetCode:" + resetCode + ", password:" + password);
		
		ViewDTO<String> view = authService.changePassword(imei,resetCode,password);
		
		return view;
	}
	
}
