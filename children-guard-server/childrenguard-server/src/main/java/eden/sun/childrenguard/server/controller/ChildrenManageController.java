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
	 * ��ѯ Person �б�
	 * @param accessToken ��¼��ȡ�� parent��access token
	 * @return ����ChildViewDTO�б�
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
	 * ��� person
	 * @param mobile �ƶ��绰
	 * @param name name
	 * @param password ��������
	 * @param parentAccessToken parent�� access token
	 * @param photoImage ͷ��(optional)
	 * @return ������ӵ�ChildViewDTO����
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
	 * ��� person
	 * @param mobile �ƶ��绰
	 * @param firstName first name
	 * @param lastName last name
	 * @param nickname �ǳ�
	 * @param relationshipId ��ϵ����ID
	 * @param parentAccessToken parent�� access token
	 * @param photoImage ͷ��(optional)
	 * @return ������ӵ�ChildViewDTO����
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
	 * ɾ�� person 
	 * @param childId person id
	 * @return ���ر�ɾ����person����ChildViewDTO
	 */
	@RequestMapping("/deleteChild")
	@ResponseBody
	public ViewDTO<ChildViewDTO> deleteChild(Integer childId) {
		ViewDTO<ChildViewDTO> view = childrenManageService.deleteChild(childId);
		return view;
	}

	/**
	 * /parent/childrenManage/basicInfo
	 * ��ȡ person ������Ϣ
	 * @param childId person id
	 * @return ����person������Ϣ����ChildBasicInfoViewDTO
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
	 * ��ѯperson��app�б� 
	 * @param childId person id
	 * @return ����person��app�б�(AppViewDTO�б�) 
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
	 * ��ѯperson������Ϣ
	 * @param childId person id
	 * @return ����ChildSettingViewDTO����
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
	 * �޸�person app�������� 
	 * @param childId person id
	 * @param password ������
	 * @return �ɹ�����true�����򷵻�false
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
	 * �޸�person��speed limitֵ
	 * @param childId person id
	 * @param speed �ٶ�ֵ
	 * @return �ɹ�����true�����򷵻�false
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
	 * Ӧ�� person ����
	 * @param childId person id
	 * @param settingListJson ����json����(List<MoreSettingParam>)
	 * @return �ɹ�����true�����򷵻�false
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
	 * Ӧ��person��app����
	 * @param childId person id
	 * @param appListJson ��Ҫ���µ�app�б�json����(List<AppManageSettingParam>)
	 * @return �ɹ�����true�����򷵻�false
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
	 * �ϴ�personͷ��
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
            System.out.println("�ļ�δ�ϴ�");  
        }else{  
            logger.info("file size: " + file.getSize());  
            logger.info("file type: " + file.getContentType());  
            logger.info("file name: " + file.getName());  
            logger.info("file original name: " + file.getOriginalFilename());  
            //����õ���Tomcat�����������ļ����ϴ���\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\�ļ�����  
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
    		//���ﲻ�ش���IO���رյ����⣬��ΪFileUtils.copyInputStreamToFile()�����ڲ����Զ����õ���IO���ص������ǿ�����Դ���֪����  
    		
    		File uploadFile = new File(uplaodDir.getAbsolutePath(), photoFileName);
    		FileUtils.copyInputStreamToFile(file.getInputStream(), uploadFile);  
    		view.setData(preDir + "/" +photoFileName);
        }
		
        return view;  
    }  
	
	
	/**
	 * /parent/childrenManage/lockAllAppByChild
	 * ����person ������app
	 * @param childId person id
	 * @return �ɹ�����true�����򷵻�false
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
	 * ����person������app
	 * @param childId person id
	 * @return �ɹ�����true�����򷵻�false
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
	 * ��ȡperson Ԥ������������
	 * @param childId person id
	 * @return ����Ԥ���������� PresetLockViewDTO
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
	 * Ӧ��Ԥ������
	 * @param childId person id
	 * @param applyPresetLockParamJson Ԥ����������json(ApplyPresetLockParam)
	 * @return �ɹ�����true�����򷵻�false
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
	 * ��ȡperson��Ԥ�������е�app�б�()
	 * @param childId person id
	 * @return ����Ԥ������app�б�List<AppViewDTO>��
	 */
	@RequestMapping("/listChildPresetLockApp")
	@ResponseBody
	public ViewDTO<List<AppViewDTO>> listChildPresetLockApp(Integer childId) {
		logger.info("listChildPresetLockApp called. childId:" + childId);
		ViewDTO<List<AppViewDTO>> view = childDetailService.listChildPresetLockApp(childId);
		
		return view;
	}
	
}
