package eden.sun.childrenguard.server.controller;

import java.io.File;
import java.io.IOException;
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
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.AppManageSettingParam;
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
	
	@RequestMapping("/listMyChildren")
	@ResponseBody
	public ViewDTO<List<ChildViewDTO>> listMyChildren(String accessToken){
		logger.info("listMyChildren called. accessToken:" + accessToken);
		ViewDTO<List<ChildViewDTO>> view = childrenManageService.listChildrenByParentAccessToken(accessToken);
		
		return view;
	}
	
	
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
	
	@RequestMapping("/applySettingChanges")
	@ResponseBody
	public ViewDTO<Boolean> applySettingChanges(Integer childId,String settingListJson) {
		logger.info("applySettingChanges called. childId:" + childId + ",settingListJson:" + settingListJson);
		
		List<MoreSettingParam> moreSettingList = JSONUtil.getMoreSettingParamList(settingListJson);
		ViewDTO<Boolean> view = childDetailService.applyChildSettingMoreChanges(childId,moreSettingList);
		
		return view;
	}
	
	@RequestMapping("/applyAppChanges")
	@ResponseBody
	public ViewDTO<Boolean> applyAppChanges(Integer childId,String appListJson) {
		logger.info("applyAppChanges called. childId:" + childId + ",appListJson:" + appListJson );
		
		List<AppManageSettingParam> appList = JSONUtil.getAppManageSettingParamList(appListJson);
		ViewDTO<Boolean> view = childDetailService.applyChildAppChanges(childId,appList);
		
		return view;
	}

	
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
}
