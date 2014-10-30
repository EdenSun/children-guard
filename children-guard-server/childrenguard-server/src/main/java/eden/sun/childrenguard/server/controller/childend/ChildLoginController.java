package eden.sun.childrenguard.server.controller.childend;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eden.sun.childrenguard.server.controller.BaseController;
import eden.sun.childrenguard.server.dto.ChildLoginViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.service.IChildAuthService;

@Controller
@RequestMapping("/app/child")
public class ChildLoginController extends BaseController{
	@Inject
	private IChildAuthService childAuthService;
	
	@RequestMapping("/login")
	@ResponseBody
	public ViewDTO<ChildLoginViewDTO> login(String mobile){
		ViewDTO<ChildLoginViewDTO> view = childAuthService.login(mobile);
		
		return view;
	}
}
