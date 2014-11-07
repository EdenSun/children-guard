package eden.sun.childrenguard.server.controller.childend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eden.sun.childrenguard.server.controller.BaseController;
import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.service.IChildAccountService;

@Controller
@RequestMapping("/app/child/account")
public class ChildAccountController  extends BaseController{
	@Autowired
	private IChildAccountService childAccountService;
	
	@RequestMapping("/isActivate")
	@ResponseBody
	public ViewDTO<ChildViewDTO> isActivate(String imei){
		logger.info("isActivate called. imei:" + imei);
		ViewDTO<ChildViewDTO> view = childAccountService.isActivate(imei);
		
		return view;
	}
	
	@RequestMapping("/doActivate")
	@ResponseBody
	public ViewDTO<Boolean> doActivate(String parentEmail ,String childMobile,String imei){
		logger.info("doActivate called.parentEmail:" + parentEmail + " childMobile:"+ childMobile + " imei:" + imei);
		ViewDTO<Boolean> view = childAccountService.doActivate(parentEmail,childMobile,imei);
		
		return view;
	}
	
}
