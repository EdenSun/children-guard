package eden.sun.childrenguard.server.controller.childend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eden.sun.childrenguard.server.controller.BaseController;
import eden.sun.childrenguard.server.dto.ChildInfoViewDTO;
import eden.sun.childrenguard.server.dto.ChildSettingViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.UpdateLocationParam;
import eden.sun.childrenguard.server.service.IChildExtraInfoService;
import eden.sun.childrenguard.server.service.IChildService;

@Controller
@RequestMapping("/app/child/childinfo")
public class ChildInfoController extends BaseController{
	@Autowired
	private IChildService childService;
	@Autowired
	private IChildExtraInfoService childExtraInfoService;
	
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
	
	@RequestMapping("/uploadLocation")
	@ResponseBody
	public ViewDTO<Boolean> uploadLocation(
			String imei,
			@ModelAttribute("locationInfo") UpdateLocationParam locationInfo){
		logger.info("uploadLocation.imei:" + imei + " location info: " + locationInfo);
		
		ViewDTO<Boolean> view = childExtraInfoService.updateLocation(imei,locationInfo);
		
		return view;
	}
	
}
