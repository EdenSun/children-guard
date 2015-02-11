package eden.sun.childrenguard.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.service.IParentService;

@Controller("parentPushController")
@RequestMapping("/parent/push")
public class PushController extends BaseController{
	@Autowired
	private IParentService parentService;
	
	/**
	 * /parent/push/saveRegistionId
	 * ����parent��register id
	 * @param imei parent�ֻ���imei
	 * @param registionId jpush �� registion id
	 * @return �ɹ�����true�����򷵻�false
	 */
	/*@RequestMapping("/saveRegistionId")
	@ResponseBody
	public ViewDTO<Boolean> saveRegistionId(String imei ,String registionId){
		logger.info("parent saveRegistionId. imei:" + imei + ",registionId:"  + registionId);
		ViewDTO<Boolean> view = parentService.saveOrUpdateRegistionId(imei,registionId);
		
		return view;
	}*/
	
	@RequestMapping("/updateRegistrationId")
	@ResponseBody
	public ViewDTO<Boolean> updateRegistrationId(String accessToken ,String registrationId){
		logger.info("parent updateRegistrationId. accessToken:" + accessToken + ",registrationId:"  + registrationId);
		ViewDTO<Boolean> view = parentService.saveOrUpdateRegistionId(accessToken,registrationId);
		
		return view;
	}
	
}
