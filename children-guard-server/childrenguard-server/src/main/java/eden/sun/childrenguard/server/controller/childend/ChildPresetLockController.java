package eden.sun.childrenguard.server.controller.childend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eden.sun.childrenguard.server.controller.BaseController;
import eden.sun.childrenguard.server.dto.PresetLockViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.service.IPresetLockService;

@Controller
@RequestMapping("/app/child/presetlock")
public class ChildPresetLockController extends BaseController{
	@Autowired
	private IPresetLockService presetLockService;
	
	@RequestMapping("/retrievePresetLockData")
	@ResponseBody
	public ViewDTO<PresetLockViewDTO> retrievePresetLockData(String imei){
		logger.info("retrievePresetLockData. imei:" + imei );
		ViewDTO<PresetLockViewDTO> view = presetLockService.retrievePresetLockData(imei);
		
		return view;
	}
}
