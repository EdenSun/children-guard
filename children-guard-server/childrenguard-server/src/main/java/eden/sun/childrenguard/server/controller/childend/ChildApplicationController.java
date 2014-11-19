package eden.sun.childrenguard.server.controller.childend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eden.sun.childrenguard.server.controller.BaseController;
import eden.sun.childrenguard.server.dto.AppViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.UploadApplicationInfoParam;
import eden.sun.childrenguard.server.service.IChildAppService;
import eden.sun.childrenguard.server.util.JSONUtil;

@Controller
@RequestMapping("/app/child/app")
public class ChildApplicationController extends BaseController{

	@Autowired
	private IChildAppService childAppService;
	
	@RequestMapping("/uploadAllApp")
	@ResponseBody
	public ViewDTO<Boolean> uploadAllApp(String imei,String appListJson){
		logger.info("uploadAllApp. imei:" + imei + "appListJson:" + appListJson);
		List<UploadApplicationInfoParam> appList = JSONUtil.getUploadApplicationInfoParamList(appListJson);
		ViewDTO<Boolean> view = childAppService.uploadAllApp(imei,appList);
		
		return view;
	}
	
	@RequestMapping("/installApp")
	@ResponseBody
	public ViewDTO<AppViewDTO> installApp(
			String imei,
			@ModelAttribute("appInfo")UploadApplicationInfoParam appInfo){
		logger.info("installApp. imei:" + imei + "appInfo:" + appInfo);
		ViewDTO<AppViewDTO> view = childAppService.installApp(imei,appInfo);
		
		return view;
	}
	
	@RequestMapping("/uninstallApp")
	@ResponseBody
	public ViewDTO<AppViewDTO> uninstallApp(
			String imei,
			@ModelAttribute("appInfo")UploadApplicationInfoParam appInfo){
		logger.info("uninstallApp. imei:" + imei + "appInfo:" + appInfo);
		ViewDTO<AppViewDTO> view = childAppService.uninstallApp(imei,appInfo);
		
		return view;
	}
	
	
	@RequestMapping("/listChildAppInfo")
	@ResponseBody
	public ViewDTO<List<AppViewDTO>> listChildAppInfo(String imei){
		logger.info("listChildAppInfo. imei:" + imei );
		ViewDTO<List<AppViewDTO>> view = childAppService.listAppByChildImei(imei);
		
		return view;
	}
}
