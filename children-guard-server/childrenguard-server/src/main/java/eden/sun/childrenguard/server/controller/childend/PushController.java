package eden.sun.childrenguard.server.controller.childend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eden.sun.childrenguard.server.controller.BaseController;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.service.IChildService;

@Controller("childPushController")
@RequestMapping("/child/push")
public class PushController extends BaseController{
	@Autowired
	private IChildService childService;
	
	/**
	 * /child/push/saveRegistionId
	 * ±£´æpersonµÄ registion id
	 * @param imei
	 * @param registionId
	 * @return
	 */
	/*@RequestMapping("/saveRegistionId")
	@ResponseBody
	public ViewDTO<Boolean> saveRegistionId(String imei ,String registionId){
		logger.info("child saveRegistionId. imei:" + imei + ",registionId:"  + registionId);
		ViewDTO<Boolean> view = childService.saveOrUpdateRegistionId(imei,registionId);
		
		return view;
	}*/
	
	
	@RequestMapping("/updateRegistrationId")
	@ResponseBody
	public ViewDTO<Boolean> updateRegistrationId(Integer childId ,String registrationId){
		logger.info("child updateRegistrationId. childId:" + childId + ",registrationId:"  + registrationId);
		ViewDTO<Boolean> view = childService.saveOrUpdateRegistionId(childId,registrationId);
		
		return view;
	}
	
	
}
