package eden.sun.childrenguard.server.controller.childend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eden.sun.childrenguard.server.controller.BaseController;
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
	public ViewDTO<Boolean> installApp(
			String childAccessToken,
			@ModelAttribute("appInfo")UploadApplicationInfoParam appInfo){
		ViewDTO<Boolean> view = childAppService.installApp(childAccessToken,appInfo);
		
		return view;
	}
	
	@RequestMapping("/uninstallApp")
	@ResponseBody
	public ViewDTO<Boolean> uninstallApp(
			String childAccessToken,
			@ModelAttribute("appInfo")UploadApplicationInfoParam appInfo){
		ViewDTO<Boolean> view = childAppService.uninstallApp(childAccessToken,appInfo);
		
		return view;
	}
	
}
