package eden.sun.childrenguard.server.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.UploadApplicationInfoParam;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.Child;
import eden.sun.childrenguard.server.service.IAppService;
import eden.sun.childrenguard.server.service.IChildAppService;
import eden.sun.childrenguard.server.service.IChildService;

@Service
public class ChildAppServiceImpl implements IChildAppService {
	
	@Inject
	private IChildService childService;
	
	@Inject
	private IAppService appService;
	
	@Override
	public ViewDTO<Boolean> installApp(String childAccessToken,
			UploadApplicationInfoParam appInfo) throws ServiceException {
		ViewDTO<Boolean> view = new ViewDTO<Boolean>();
		if( childAccessToken == null || appInfo == null ){
			view.setMsg(ViewDTO.MSG_ERROR);
			view.setInfo("Parameter is incorrect.");
			view.setData(false);
		}
		
		Child child = childService.getChildByAccessToken(childAccessToken);
		if( child == null ){
			view.setMsg(ViewDTO.MSG_ERROR);
			view.setInfo("upload app failure.child is not exits.");
			view.setData(false);
		}
		
		boolean isSuccess = appService.addOrUpdate(child.getId(),appInfo);
		if( !isSuccess ){
			view.setMsg(ViewDTO.MSG_ERROR);
			view.setInfo("add or update application failure.");
			view.setData(false);
		}
		
		view.setData(true);
		return view;
	}

	@Override
	public ViewDTO<Boolean> uninstallApp(String childAccessToken,
			UploadApplicationInfoParam appInfo) throws ServiceException {
		ViewDTO<Boolean> view = new ViewDTO<Boolean>();
		if( childAccessToken == null || appInfo == null ){
			view.setMsg(ViewDTO.MSG_ERROR);
			view.setInfo("Parameter is incorrect.");
			view.setData(false);
		}
		
		Child child = childService.getChildByAccessToken(childAccessToken);
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
	public ViewDTO<Boolean> uploadAllApp(String childAccessToken,
			List<UploadApplicationInfoParam> appList) throws ServiceException {
		ViewDTO<Boolean> view = new ViewDTO<Boolean>();
		if( childAccessToken == null || appList == null ){
			view.setMsg(ViewDTO.MSG_ERROR);
			view.setInfo("Parameter is incorrect.");
			view.setData(false);
		}

		Child child = childService.getChildByAccessToken(childAccessToken);
		if( child == null ){
			view.setMsg(ViewDTO.MSG_ERROR);
			view.setInfo("upload app failure.child is not exits.");
			view.setData(false);
		}
		Integer childId = child.getId();
		
		appService.clearAppInfoByChildId(childId);
		
		appService.saveAll(childId,appList);
		
		view.setData(true);
		return view;
	}

}
