package eden.sun.childrenguard.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dto.AppViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.UploadApplicationInfoParam;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.Child;
import eden.sun.childrenguard.server.service.IAppService;
import eden.sun.childrenguard.server.service.IChildAppService;
import eden.sun.childrenguard.server.service.IChildService;

@Service
public class ChildAppServiceImpl implements IChildAppService {
	
	@Autowired
	private IChildService childService;
	
	@Autowired
	private IAppService appService;
	
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
		return view;
	}

	@Override
	public ViewDTO<Boolean> uninstallApp(String imei,
			UploadApplicationInfoParam appInfo) throws ServiceException {
		ViewDTO<Boolean> view = new ViewDTO<Boolean>();
		if( imei == null || appInfo == null ){
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
		
		appService.deleteApp(child.getId(),appInfo);

		view.setData(true);
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
