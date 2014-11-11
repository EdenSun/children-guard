package eden.sun.childrenguard.server.controller.childend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eden.sun.childrenguard.server.controller.BaseController;
import eden.sun.childrenguard.server.dto.ChildInfoViewDTO;
import eden.sun.childrenguard.server.dto.ChildSettingViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.service.IChildService;

@Controller
@RequestMapping("/app/child/childinfo")
public class ChildInfoController extends BaseController{
	@Autowired
	private IChildService childService;
	
	@RequestMapping("/retrieveChildInfo")
	@ResponseBody
	public ViewDTO<ChildInfoViewDTO> retrieveChildInfo(String imei){
		logger.info("retrieveChildInfo.imei:" + imei);
		ViewDTO<ChildInfoViewDTO> view = childService.getChildInfo(imei);
		
		return view;
	}
	
	@RequestMapping("/retrieveChildSetting")
	@ResponseBody
	public ViewDTO<ChildSettingViewDTO> retrieveChildSetting(String imei){
		logger.info("retrieveChildSetting.imei:" + imei);
		ViewDTO<ChildSettingViewDTO> view = childService.getChildSetting(imei);
		
		return view;
	}
}
