package eden.sun.childrenguard.server.controller.childend;

import javax.inject.Inject;

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
	@Inject
	private IChildAccountService childAccountService;
	
	@RequestMapping("/isActivate")
	@ResponseBody
	public ViewDTO<ChildViewDTO> isActivate(String imei){
		logger.info("isActivate called. imei:" + imei);
		ViewDTO<ChildViewDTO> view = childAccountService.isActivate(imei);
		
		return view;
	}
}
