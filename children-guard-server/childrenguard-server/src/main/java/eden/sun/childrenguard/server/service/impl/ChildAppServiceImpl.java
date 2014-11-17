package eden.sun.childrenguard.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dto.AppViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.UploadApplicationInfoParam;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.Child;
import eden.sun.childrenguard.server.model.generated.Parent;
import eden.sun.childrenguard.server.service.IAppService;
import eden.sun.childrenguard.server.service.IChildAppService;
import eden.sun.childrenguard.server.service.IChildOfParentsService;
import eden.sun.childrenguard.server.service.IChildService;
import eden.sun.childrenguard.server.service.IChildSettingService;
import eden.sun.childrenguard.server.service.IJPushService;

@Service
public class ChildAppServiceImpl implements IChildAppService {
	
	@Autowired
	private IChildService childService;
	
	@Autowired
	private IAppService appService;
	
	@Autowired
	private IJPushService jpushService;
	
	@Autowired
	private IChildOfParentsService childOfParentsService;
	
	@Autowired
	private IChildSettingService childSettingService;
	
	@Override
	public ViewDTO<AppViewDTO> installApp(String imei,
			UploadApplicationInfoParam appInfo) throws ServiceException {
		ViewDTO<AppViewDTO> view = new ViewDTO<AppViewDTO>();
		if( imei == null || appInfo == null ){
			throw new ServiceException("Parameter is incorrect.");
		}
		
		Child child = childService.getChildByImei(imei);
		if( child == null ){
			throw new ServiceException("upload app failure.child is not exits.");
		}
		
		// insert app
		boolean isSuccess = appService.addOrUpdate(child.getId(),appInfo);
		if( !isSuccess ){
			view.setMsg(ViewDTO.MSG_ERROR);
			view.setInfo("add or update application failure.");
			return view;
		}
		
		view.setData(appService.getViewByPackageName(appInfo.getPackageName()));
		
		//if push install app switch is on , push install notification messages to parent 
		boolean isInstallSwitchOn = childSettingService.isInstallSwitchOn(child.getId());
		if( isInstallSwitchOn ){
			String title = "App installed notification";
			String content = child.getNickname() + " installed app:" + appInfo.getAppName();
			List<Parent> parentList = childService.getParentsByChildId(child.getId());
			jpushService.pushMessageToParent(parentList,title,content);
		}
		return view;
	}

	@Override
	public ViewDTO<AppViewDTO> uninstallApp(String imei,
			UploadApplicationInfoParam appInfo) throws ServiceException {
		ViewDTO<AppViewDTO> view = new ViewDTO<AppViewDTO>();
		if( imei == null || appInfo == null ){
			view.setMsg(ViewDTO.MSG_ERROR);
			view.setInfo("Parameter is incorrect.");
			view.setData(null);
		}
		
		Child child = childService.getChildByImei(imei);
		if( child == null ){
			view.setMsg(ViewDTO.MSG_ERROR);
			view.setInfo("upload app failure.child is not exits.");
			view.setData(null);
		}
		
		appService.deleteApp(child.getId(),appInfo);

		view.setData(appService.getViewByPackageName(appInfo.getPackageName()));

		// if push uninstall app switch is on ,push uninstall notification messages to parent 
		boolean isUnInstallSwitchOn = childSettingService.isUnInstallSwitchOn(child.getId());
		if( isUnInstallSwitchOn ){
			String title = "App uninstalled notification";
			String message = child.getNickname() + " uninstalled app:" + appInfo.getAppName();
			List<Parent> parentList = childService.getParentsByChildId(child.getId());
			jpushService.pushMessageToParent(parentList,title,message);
		}
		
		return view;
	}

	@Override
	public ViewDTO<Boolean> uploadAllApp(String imei,
			List<UploadApplicationInfoParam> appList) throws ServiceException {
		ViewDTO<Boolean> view = new ViewDTO<Boolean>();
		if( imei == null || appList == null ){
			view.setMsg(ViewDTO.MSG_ERROR);
			view.setInfo("Parameter is incorrect.");
			view.setData(false);
		}

		Child child = childService.getChildByImei(imei);
		if( child == null ){
			view.setMsg(ViewDTO.MSG_ERROR);
			view.setInfo("upload app failure.child is not exits.");
			view.setData(false);
		}
		Integer childId = child.getId();
		
		appService.clearAppInfoByChildId(childId);
		
		appService.saveOrUpdateAll(childId,appList);
		
		view.setData(true);
		return view;
	}

	@Override
	public ViewDTO<List<AppViewDTO>> listAppByChildImei(String imei)
			throws ServiceException {
		if( imei == null ){
			throw new ServiceException("Parameter imei can not be null.");
		}
		
		Child child = childService.getChildByImei(imei);
		if( child == null ){
			throw new ServiceException("Error,child is not exists.");
		}
		
		
		ViewDTO<List<AppViewDTO>> view = new ViewDTO<List<AppViewDTO>>();
		List<AppViewDTO> viewList = appService.listViewByChildId(child.getId());
	
		view.setData(viewList);
		return view;
	}
	
}
