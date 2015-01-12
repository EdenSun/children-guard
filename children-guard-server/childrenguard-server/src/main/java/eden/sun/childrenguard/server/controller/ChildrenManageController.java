package eden.sun.childrenguard.server.controller;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import eden.sun.childrenguard.server.dto.AppViewDTO;
import eden.sun.childrenguard.server.dto.ChildBasicInfoViewDTO;
import eden.sun.childrenguard.server.dto.ChildSettingViewDTO;
import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.dto.PresetLockViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.AppManageSettingParam;
import eden.sun.childrenguard.server.dto.param.ApplyPresetLockParam;
import eden.sun.childrenguard.server.dto.param.ChildAddParam;
import eden.sun.childrenguard.server.dto.param.MoreSettingParam;
import eden.sun.childrenguard.server.service.IChildDetailService;
import eden.sun.childrenguard.server.service.IChildrenManageService;
import eden.sun.childrenguard.server.util.JSONUtil;
import eden.sun.childrenguard.server.util.NumberUtil;

@Controller
@RequestMapping("/parent/childrenManage")
public class ChildrenManageController extends BaseController{

	@Autowired
	private IChildrenManageService childrenManageService;
	@Autowired
	private IChildDetailService childDetailService;
	
	/**
	 * /parent/childrenManage/listMyChildren
	 * 查询 Person 列表
	 * @param accessToken 登录获取的 parent的access token
	 * @return 返回ChildViewDTO列表
	 */
	@RequestMapping("/listMyChildren")
	@ResponseBody
	public ViewDTO<List<ChildViewDTO>> listMyChildren(String accessToken){
		logger.info("listMyChildren called. accessToken:" + accessToken);
		ViewDTO<List<ChildViewDTO>> view = childrenManageService.listChildrenByParentAccessToken(accessToken);
		
		return view;
	}
	
	
	/**
	 * /parent/childrenManage/addChild
	 * 添加 person
	 * @param mobile 移动电话
	 * @param name name
	 * @param password 锁定密码
	 * @param parentAccessToken parent的 access token
	 * @param photoImage 头像(optional)
	 * @return 返回添加的ChildViewDTO对象
	 */
	@RequestMapping("/addChild")
	@ResponseBody
	public ViewDTO<ChildViewDTO> addChild(
			String mobile,String name,
			String password,
			String parentAccessToken,
			String photoImage){
		ChildAddParam param = new ChildAddParam();
		param.setMobile(mobile);
		param.setName(name);
		param.setPassword(password);
		param.setParentAccessToken(parentAccessToken);
		param.setPhotoImage(photoImage);
		
		ViewDTO<ChildViewDTO> view = childrenManageService.addChild(param);
	
		return view;
	}
	
	/**
	 * /parent/childrenManage/addChild
	 * 添加 person
	 * @param mobile 移动电话
	 * @param firstName first name
	 * @param lastName last name
	 * @param nickname 昵称
	 * @param relationshipId 关系对象ID
	 * @param parentAccessToken parent的 access token
	 * @param photoImage 头像(optional)
	 * @return 返回添加的ChildViewDTO对象
	 *//*
	@RequestMapping("/addChild")
	@ResponseBody
	public ViewDTO<ChildViewDTO> addChild(
			String mobile,String firstName,String lastName,
			String nickname,String relationshipId,String parentAccessToken,
			String photoImage){
		ChildAddParam param = new ChildAddParam();
		param.setMobile(mobile);
		param.setFirstName(firstName);
		param.setLastName(lastName);
		param.setNickname(nickname);
		param.setRelationshipId(NumberUtil.toInteger(relationshipId));
		param.setParentAccessToken(parentAccessToken);
		param.setPhotoImage(photoImage);
		
		ViewDTO<ChildViewDTO> view = childrenManageService.addChild(param);
	
		return view;
	}*/
	
	/**
	 * /parent/childrenManage/deleteChild
	 * 删除 person 
	 * @param childId person id
	 * @return 返回被删除的person对象ChildViewDTO
	 */
	@RequestMapping("/deleteChild")
	@ResponseBody
	public ViewDTO<ChildViewDTO> deleteChild(Integer childId) {
		ViewDTO<ChildViewDTO> view = childrenManageService.deleteChild(childId);
		return view;
	}

	/**
	 * /parent/childrenManage/basicInfo
	 * 获取 person 基本信息
	 * @param childId person id
	 * @return 返回person基本信息对象ChildBasicInfoViewDTO
	 */
	@RequestMapping("/basicInfo")
	@ResponseBody
	public ViewDTO<ChildBasicInfoViewDTO> basicInfo(Integer childId) {
		logger.info("basicInfo called. childId:" + childId);
		ViewDTO<ChildBasicInfoViewDTO> view = childDetailService.getChildBasicInfo(childId);
		
		return view;
	}
	
	/**
	 * /parent/childrenManage/listChildApp
	 * 查询person的app列表 
	 * @param childId person id
	 * @return 返回person的app列表(AppViewDTO列表) 
	 */
	@RequestMapping("/listChildApp")
	@ResponseBody
	public ViewDTO<List<AppViewDTO>> listChildApp(Integer childId) {
		logger.info("listChildApp called. childId:" + childId);
		ViewDTO<List<AppViewDTO>> view = childDetailService.listChildApp(childId);
		
		return view;
	}
	
	/**
	 * /parent/childrenManage/loadChildSetting
	 * 查询person设置信息
	 * @param childId person id
	 * @return 返回ChildSettingViewDTO对象
	 */
	@RequestMapping("/loadChildSetting")
	@ResponseBody
	public ViewDTO<ChildSettingViewDTO> loadChildSetting(Integer childId) {
		logger.info("loadChildSetting called. childId:" + childId );
		ViewDTO<ChildSettingViewDTO> view = childDetailService.loadChildSetting(childId);
		
		return view;
	}
	
	/**
	 * /parent/childrenManage/modifyLockPassword
	 * 修改person app锁定密码 
	 * @param childId person id
	 * @param password 新密码
	 * @return 成功返回true，否则返回false
	 */
	@RequestMapping("/modifyLockPassword")
	@ResponseBody
	public ViewDTO<Boolean> modifyLockPassword(Integer childId,String password) {
		logger.info("modifyLockPassword called. childId:" + childId + ",password:" + password);
		ViewDTO<Boolean> view = childDetailService.modifyLockPassword(childId,password);
		
		return view;
	}
	
	/**
	 * /parent/childrenManage/modifySpeedLimit
	 * 修改person的speed limit值
	 * @param childId person id
	 * @param speed 速度值
	 * @return 成功返回true，否则返回false
	 */
	@RequestMapping("/modifySpeedLimit")
	@ResponseBody
	public ViewDTO<Boolean> modifySpeedLimit(Integer childId,Integer speed) {
		logger.info("modifySpeedLimit called. childId:" + childId + ",speed:" + speed);
		ViewDTO<Boolean> view = childDetailService.modifySpeedLimit(childId,speed);
		
		return view;
	}
	
	/**
	 * /parent/childrenManage/applySettingChanges
	 * 应用 person 设置
	 * @param childId person id
	 * @param settingListJson 设置json对象(List<MoreSettingParam>)
	 * @return 成功返回true，否则返回false
	 */
	@RequestMapping("/applySettingChanges")
	@ResponseBody
	public ViewDTO<Boolean> applySettingChanges(Integer childId,String settingListJson) {
		logger.info("applySettingChanges called. childId:" + childId + ",settingListJson:" + settingListJson);
		
		List<MoreSettingParam> moreSettingList = JSONUtil.getMoreSettingParamList(settingListJson);
		ViewDTO<Boolean> view = childDetailService.applyChildSettingMoreChanges(childId,moreSettingList);
		
		return view;
	}
	
	/**
	 * /parent/childrenManage/applyAppChanges
	 * 应用person的app设置
	 * @param childId person id
	 * @param appListJson 需要更新的app列表json对象(List<AppManageSettingParam>)
	 * @return 成功返回true，否则返回false
	 */
	@RequestMapping("/applyAppChanges")
	@ResponseBody
	public ViewDTO<Boolean> applyAppChanges(Integer childId,String appListJson) {
		logger.info("applyAppChanges called. childId:" + childId + ",appListJson:" + appListJson );
		
		List<AppManageSettingParam> appList = JSONUtil.getAppManageSettingParamList(appListJson);
		ViewDTO<Boolean> view = childDetailService.applyChildAppChanges(childId,appList);
		
		return view;
	}

	/**
	 * /parent/childrenManage/uploadPhoto
	 * 上传person头像
	 * @param file
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/uploadPhoto", method=RequestMethod.POST)  
	@ResponseBody
    public ViewDTO<String> uploadPhoto(@RequestParam MultipartFile file, HttpServletRequest request) throws Exception{  
		logger.info("uploadPhoto called. ");
		
		ViewDTO<String> view = new ViewDTO<String>();
		if( file.isEmpty() ){  
            System.out.println("文件未上传");  
        }else{  
            logger.info("file size: " + file.getSize());  
            logger.info("file type: " + file.getContentType());  
            logger.info("file name: " + file.getName());  
            logger.info("file original name: " + file.getOriginalFilename());  
            //如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\文件夹中  
            String realPath = request.getSession().getServletContext().getRealPath("/");  
            DateFormat df = new SimpleDateFormat("yyMMdd");
            String preDir = "upload/" + df.format(new Date());
            File uplaodDir = new File(realPath + "/" + preDir);
            if( !uplaodDir.exists() ){
            	boolean isSuccess = uplaodDir.mkdirs();
            	if( !isSuccess ){
            		view.setMsg(ViewDTO.MSG_ERROR);
            		view.setData(null);
            		view.setInfo("Server error , upload fail.");
            		return view;
            	}
            }
            
            String photoFileName = "photo" + new Date().getTime();
    		//这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的  
    		
    		File uploadFile = new File(uplaodDir.getAbsolutePath(), photoFileName);
    		FileUtils.copyInputStreamToFile(file.getInputStream(), uploadFile);  
    		view.setData(preDir + "/" +photoFileName);
        }
		
        return view;  
    }  
	
	
	/**
	 * /parent/childrenManage/lockAllAppByChild
	 * 锁定person 的所有app
	 * @param childId person id
	 * @return 成功返回true，否则返回false
	 */
	@RequestMapping("/lockAllAppByChild")
	@ResponseBody
	public ViewDTO<Boolean> lockAllAppByChild(Integer childId) {
		logger.info("lockAllAppByChild called. childId:" + childId );
		
		ViewDTO<Boolean> view = childDetailService.lockAllAppByChild(childId);
		
		return view;
	}
	
	/**
	 * /parent/childrenManage/unlockAllAppByChild
	 * 解锁person的所有app
	 * @param childId person id
	 * @return 成功返回true，否则返回false
	 */
	@RequestMapping("/unlockAllAppByChild")
	@ResponseBody
	public ViewDTO<Boolean> unlockAllAppByChild(Integer childId) {
		logger.info("unlockAllAppByChild called. childId:" + childId );
		
		ViewDTO<Boolean> view = childDetailService.unlockAllAppByChild(childId);
		
		return view;
	}
	
	/**
	 * /parent/childrenManage/loadPresetLockData
	 * 获取person 预设锁定的数据
	 * @param childId person id
	 * @return 返回预设锁定对象 PresetLockViewDTO
	 */
	@RequestMapping("/loadPresetLockData")
	@ResponseBody
	public ViewDTO<PresetLockViewDTO> loadPresetLockData(Integer childId){
		logger.info("loadPresetLockData called. childId:" + childId );
		
		ViewDTO<PresetLockViewDTO> view = childDetailService.loadPresetLockData(childId);
		
		return view;
		
	}
	
	/**
	 * /parent/childrenManage/applyPresetLock
	 * 应用预设锁定
	 * @param childId person id
	 * @param applyPresetLockParamJson 预设锁定设置json(ApplyPresetLockParam)
	 * @return 成功返回true，否则返回false
	 */
	@RequestMapping("/applyPresetLock")
	@ResponseBody
	public ViewDTO<Boolean> applyPresetLock(Integer childId,String applyPresetLockParamJson){
		logger.info("applyPresetLock called. childId:" + childId + " ,applyPresetLockParam:" + applyPresetLockParamJson );
		
		ApplyPresetLockParam applyPresetLockParam = JSONUtil.getApplyPresetLockParam(applyPresetLockParamJson);
		ViewDTO<Boolean> view = childDetailService.applyPresetLock(childId,applyPresetLockParam);
		
		return view;
		
	}
	
	/**
	 * /parent/childrenManage/listChildPresetLockApp
	 * 获取person的预设锁定中的app列表()
	 * @param childId person id
	 * @return 返回预设锁定app列表（List<AppViewDTO>）
	 */
	@RequestMapping("/listChildPresetLockApp")
	@ResponseBody
	public ViewDTO<List<AppViewDTO>> listChildPresetLockApp(Integer childId) {
		logger.info("listChildPresetLockApp called. childId:" + childId);
		ViewDTO<List<AppViewDTO>> view = childDetailService.listChildPresetLockApp(childId);
		
		return view;
	}
	
}
