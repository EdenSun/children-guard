package eden.sun.childrenguard.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.ControlSettingApplyParam;
import eden.sun.childrenguard.server.service.IPersonSettingService;
import eden.sun.childrenguard.server.util.JSONUtil;

@Controller
@RequestMapping("/parent/personControl")
public class PersonControlController extends BaseController{

	@Autowired
	private IPersonSettingService personSettingService;
	
	@RequestMapping("/applySetting")
	@ResponseBody
	public ViewDTO<Boolean> applySetting(String settingJson){
		logger.info("applySetting called. settingJson:" + settingJson );
		
		ControlSettingApplyParam applyParam = JSONUtil.getApplyPersonControlParam(settingJson);
		
		ViewDTO<Boolean> view = personSettingService.doApplyControlSetting(applyParam);
		return view;
	}
}
