package eden.sun.childrenguard.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.SettingApplyParam;
import eden.sun.childrenguard.server.service.IPersonSettingService;
import eden.sun.childrenguard.server.util.JSONUtil;

@Controller
@RequestMapping("/parent/personSetting")
public class PersonSettingController extends BaseController{

	@Autowired
	private IPersonSettingService personSettingService;
	
	@RequestMapping("/applySetting")
	@ResponseBody
	public ViewDTO<Boolean> applySetting(String paramJson){
		logger.info("applySetting called. paramJson:" + paramJson );
		
		SettingApplyParam applyParam = JSONUtil.getApplyPersonSettingParam(paramJson);
		
		ViewDTO<Boolean> view = personSettingService.doApply(applyParam);
		return view;
	}
}
