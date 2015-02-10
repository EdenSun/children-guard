package eden.sun.childrenguard.server.controller.childend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eden.sun.childrenguard.server.controller.BaseController;
import eden.sun.childrenguard.server.dto.PresetLockViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.service.IChildScheduleLockService;

@Controller
@RequestMapping("/app/child/scheduleLock")
public class ChildScheduleLockController extends BaseController{
	@Autowired
	private IChildScheduleLockService childScheduleLockService;
	
	@RequestMapping("/listAllByImei")
	@ResponseBody
	public ViewDTO<List<PresetLockViewDTO>> listAllByImei(String imei){
		logger.info("listAllByImei. imei:" + imei );
		ViewDTO<List<PresetLockViewDTO>> view = childScheduleLockService.listAllByImei(imei);
		
		return view;
	}
}
