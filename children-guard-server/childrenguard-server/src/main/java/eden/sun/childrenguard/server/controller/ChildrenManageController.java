package eden.sun.childrenguard.server.controller;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.cometd.annotation.Listener;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eden.sun.childrenguard.server.dto.AppViewDTO;
import eden.sun.childrenguard.server.dto.ChildBasicInfoViewDTO;
import eden.sun.childrenguard.server.dto.ChildSettingViewDTO;
import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.ChildAddParam;
import eden.sun.childrenguard.server.service.IChildDetailService;
import eden.sun.childrenguard.server.service.IChildrenManageService;
import eden.sun.childrenguard.server.util.CometdChannel;
import eden.sun.childrenguard.server.util.NumberUtil;

@Controller
@RequestMapping("/childrenManage")
public class ChildrenManageController extends BaseController{

	@Inject
	private IChildrenManageService childrenManageService;
	@Inject
	private IChildDetailService childDetailService;
	
	@RequestMapping("/listMyChildren")
	@ResponseBody
	public ViewDTO<List<ChildViewDTO>> listMyChildren(String accessToken){
		
		ViewDTO<List<ChildViewDTO>> view = childrenManageService.listChildrenByParentAccessToken(accessToken);
		
		return view;
	}
	
	
	@RequestMapping("/addChild")
	@ResponseBody
	public ViewDTO<ChildViewDTO> addChild(String mobile,String firstName,String lastName,String nickname,String relationshipId,String parentAccessToken){
		ChildAddParam param = new ChildAddParam();
		param.setMobile(mobile);
		param.setFirstName(firstName);
		param.setLastName(lastName);
		param.setNickname(nickname);
		param.setRelationshipId(NumberUtil.toInteger(relationshipId));
		param.setParentAccessToken(parentAccessToken);
		
		ViewDTO<ChildViewDTO> view = childrenManageService.addChild(param);
	
		return view;
	}
	
	@RequestMapping("/deleteChild")
	@ResponseBody
	public ViewDTO<ChildViewDTO> deleteChild(Integer childId) {
		ViewDTO<ChildViewDTO> view = childrenManageService.deleteChild(childId);
		return view;
	}

	
	@RequestMapping("/basicInfo")
	@ResponseBody
	public ViewDTO<ChildBasicInfoViewDTO> basicInfo(Integer childId) {
		logger.info("basicInfo called. childId:" + childId);
		ViewDTO<ChildBasicInfoViewDTO> view = childDetailService.getChildBasicInfo(childId);
		
		return view;
	}
	
	@RequestMapping("/listChildApp")
	@ResponseBody
	public ViewDTO<List<AppViewDTO>> listChildApp(Integer childId) {
		logger.info("listChildApp called. childId:" + childId);
		ViewDTO<List<AppViewDTO>> view = childDetailService.listChildApp(childId);
		
		return view;
	}
	
	@RequestMapping("/loadChildSetting")
	@ResponseBody
	public ViewDTO<ChildSettingViewDTO> loadChildSetting(Integer childId) {
		logger.info("loadChildSetting called. childId:" + childId );
		ViewDTO<ChildSettingViewDTO> view = childDetailService.loadChildSetting(childId);
		
		return view;
	}
	
	
	@RequestMapping("/modifyLockPassword")
	@ResponseBody
	public ViewDTO<Boolean> modifyLockPassword(Integer childId,String password) {
		logger.info("modifyLockPassword called. childId:" + childId + ",password:" + password);
		ViewDTO<Boolean> view = childDetailService.modifyLockPassword(childId,password);
		
		return view;
	}
	
	@RequestMapping("/modifySpeedLimit")
	@ResponseBody
	public ViewDTO<Boolean> modifySpeedLimit(Integer childId,Integer speed) {
		logger.info("modifySpeedLimit called. childId:" + childId + ",speed:" + speed);
		ViewDTO<Boolean> view = childDetailService.modifySpeedLimit(childId,speed);
		
		return view;
	}
	
}
