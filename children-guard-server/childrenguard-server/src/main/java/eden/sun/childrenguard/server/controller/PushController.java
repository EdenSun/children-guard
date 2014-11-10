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
	
	@RequestMapping("/saveRegistionId")
	@ResponseBody
	public ViewDTO<Boolean> saveRegistionId(String imei ,String registionId){
		logger.info("parent saveRegistionId. imei:" + imei + ",registionId:"  + registionId);
		ViewDTO<Boolean> view = parentService.saveOrUpdateRegistionId(imei,registionId);
		
		return view;
	}
}
