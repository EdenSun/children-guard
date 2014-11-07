package eden.sun.childrenguard.server.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dto.AppViewDTO;
import eden.sun.childrenguard.server.dto.ChildBasicInfoViewDTO;
import eden.sun.childrenguard.server.dto.ChildExtraInfoViewDTO;
import eden.sun.childrenguard.server.dto.ChildSettingViewDTO;
import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.AppManageSettingParam;
import eden.sun.childrenguard.server.dto.param.MoreSettingParam;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.Child;
import eden.sun.childrenguard.server.model.generated.ChildExtraInfo;
import eden.sun.childrenguard.server.model.generated.ChildSetting;
import eden.sun.childrenguard.server.service.IAppService;
import eden.sun.childrenguard.server.service.IChildDetailService;
import eden.sun.childrenguard.server.service.IChildExtraInfoService;
import eden.sun.childrenguard.server.service.IChildService;
import eden.sun.childrenguard.server.service.IChildSettingService;

@Service
public class ChildDetailServiceImpl implements IChildDetailService {
	@Autowired
	private IChildExtraInfoService childExtraInfoService;
	@Autowired
	private IChildService childService;
	@Autowired
	private IAppService appService;
	@Autowired
	private IChildSettingService childSettingService;
	
	@Override
	public ViewDTO<List<AppViewDTO>> listChildApp(Integer childId)
			throws ServiceException {
		if( childId == null ){
			throw new ServiceException("Parameter childId can not be null.");
		}
		ViewDTO<List<AppViewDTO>> view = new ViewDTO<List<AppViewDTO>>();
		
		List<AppViewDTO> appViewDTOList = appService.listViewByChildId(childId);
		
		view.setData(appViewDTOList);
		return view;
	}

	@Override
	public ViewDTO<ChildBasicInfoViewDTO> getChildBasicInfo(Integer childId)
			throws ServiceException {
		ViewDTO<ChildBasicInfoViewDTO> view = new ViewDTO<ChildBasicInfoViewDTO>();
		
		ChildExtraInfo childExtraInfo = childExtraInfoService.getById(childId);
		
		ChildBasicInfoViewDTO childBasicInfoViewDTO = trans2ChildBasicInfoViewDTO(childId,childExtraInfo);
		
		view.setData(childBasicInfoViewDTO);
		return view;
	}

	private ChildBasicInfoViewDTO trans2ChildBasicInfoViewDTO(Integer childId,
			ChildExtraInfo childExtraInfo)throws ServiceException{
		ChildBasicInfoViewDTO childBasicInfoViewDTO = new ChildBasicInfoViewDTO();
		ChildViewDTO childViewDTO = childService.getViewById(childId);
		if( childViewDTO == null ){
			throw new ServiceException("Child is not exists");
		}
		
		childBasicInfoViewDTO.setChild(childViewDTO);
		
		ChildExtraInfoViewDTO childExtraInfoViewDTO = null;
		if( childExtraInfo != null ){
			childExtraInfoViewDTO = trans2ChildExtraInfoViewDTO(childExtraInfo);
		}
		
		childBasicInfoViewDTO.setExtraInfo(childExtraInfoViewDTO);
		return childBasicInfoViewDTO;
	}

	private ChildExtraInfoViewDTO trans2ChildExtraInfoViewDTO(
			ChildExtraInfo childExtraInfo) {
		ChildExtraInfoViewDTO view = new ChildExtraInfoViewDTO();
		BeanUtils.copyProperties(childExtraInfo, view);
		
		//FIXME: not implements
		view.setOnlineStatus("");
		view.setLocation("");
		
		return view;
	}

	@Override
	public ViewDTO<Boolean> modifyLockPassword(Integer childId, String password)
			throws ServiceException {
		ViewDTO<Boolean> view = new ViewDTO<Boolean>();
		if( childId == null || password == null){
			throw new ServiceException("childId or password can not be null.");
		}

		Child child = childService.getById(childId);
		if( child == null ){
			view.setMsg(ViewDTO.MSG_ERROR);
			view.setInfo("Child is not exists.");
			return view;
		}
		
		//update lock password
		ChildSetting childSetting = childSettingService.addIfNotExists(childId);
		if( childSetting != null ){
			childSetting.setAppLockPassword(password);
			childSettingService.update(childSetting);
		}
		
		view.setData(true);
		return view;
	}

	@Override
	public ViewDTO<Boolean> modifySpeedLimit(Integer childId, Integer speed)
			throws ServiceException {
		ViewDTO<Boolean> view = new ViewDTO<Boolean>();
		if( childId == null || speed == null){
			throw new ServiceException("childId or speed can not be null.");
		}

		Child child = childService.getById(childId);
		if( child == null ){
			view.setMsg(ViewDTO.MSG_ERROR);
			view.setInfo("Child is not exists.");
			return view;
		}
		
		//update speed limit
		ChildSetting childSetting = childSettingService.addIfNotExists(childId);
		if( childSetting != null ){
			childSetting.setSpeedingLimit(speed);
			childSettingService.update(childSetting);
		}
		
		view.setData(true);
		return view;
	}

	@Override
	public ViewDTO<ChildSettingViewDTO> loadChildSetting(Integer childId)
			throws ServiceException {
		if( childId == null ){
			throw new ServiceException("Parameter childId can not be null.");
		}
		ViewDTO<ChildSettingViewDTO> view = new ViewDTO<ChildSettingViewDTO>();
		ChildSettingViewDTO childSettingView = childSettingService.getViewById(childId);
		
		view.setData(childSettingView);
		return view;
	}

	@Override
	public ViewDTO<Boolean> applyChildSettingMore(Integer childId,
			List<MoreSettingParam> moreSettingList) throws ServiceException {
		if( childId == null || moreSettingList == null ){
			throw new ServiceException("Parameter childId or moreSettingList can not be null.");
		}
		ViewDTO<Boolean> view = new ViewDTO<Boolean>();
		try {
			Integer settingId = childId;
			childSettingService.updateChildSetting(settingId,moreSettingList);
			
			view.setData(true);
		} catch (Exception e) {
			view.setData(false);
			view.setInfo("Apply child setting error.");
		}
		return view;
	}

	@Override
	public ViewDTO<Boolean> applyChildSettingApp(Integer childId,
			List<AppManageSettingParam> appManageSettingList)
			throws ServiceException {
		if( childId == null || appManageSettingList == null ){
			throw new ServiceException("Parameter childId or appManageSettingList can not be null.");
		}
		ViewDTO<Boolean> view = new ViewDTO<Boolean>();
		try {
			
			appService.updateApp(childId,appManageSettingList);
			
			view.setData(true);
		} catch (Exception e) {
			view.setData(false);
			view.setInfo("Apply child app lock/unlock status error.");
		}
		return view;
	}

}
