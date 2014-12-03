package eden.sun.childrenguard.server.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dto.AppViewDTO;
import eden.sun.childrenguard.server.dto.ChildBasicInfoViewDTO;
import eden.sun.childrenguard.server.dto.ChildExtraInfoViewDTO;
import eden.sun.childrenguard.server.dto.ChildSettingViewDTO;
import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.dto.PresetLockViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.AppManageSettingParam;
import eden.sun.childrenguard.server.dto.param.ApplyPresetLockParam;
import eden.sun.childrenguard.server.dto.param.MoreSettingParam;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.Child;
import eden.sun.childrenguard.server.model.generated.ChildExtraInfo;
import eden.sun.childrenguard.server.model.generated.ChildSetting;
import eden.sun.childrenguard.server.model.generated.PresetLock;
import eden.sun.childrenguard.server.service.IAppService;
import eden.sun.childrenguard.server.service.IChildDetailService;
import eden.sun.childrenguard.server.service.IChildExtraInfoService;
import eden.sun.childrenguard.server.service.IChildService;
import eden.sun.childrenguard.server.service.IChildSettingService;
import eden.sun.childrenguard.server.service.IJPushService;
import eden.sun.childrenguard.server.service.IPresetLockAppService;
import eden.sun.childrenguard.server.service.IPresetLockService;
import eden.sun.childrenguard.server.util.PushConstants;

@Service
public class ChildDetailServiceImpl extends BaseServiceImpl implements IChildDetailService {
	@Autowired
	private IChildExtraInfoService childExtraInfoService;
	@Autowired
	private IChildService childService;
	@Autowired
	private IAppService appService;
	@Autowired
	private IChildSettingService childSettingService;
	
	@Autowired
	private IPresetLockService presetLockService;
	
	@Autowired
	private IPresetLockAppService presetLockAppService;

	@Autowired
	private IJPushService pushService;
	
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
	public ViewDTO<Boolean> applyChildSettingMoreChanges(Integer childId,
			List<MoreSettingParam> moreSettingList) throws ServiceException {
		if( childId == null || moreSettingList == null ){
			throw new ServiceException("Parameter childId or moreSettingList can not be null.");
		}
		ViewDTO<Boolean> view = new ViewDTO<Boolean>();
		try {
			Integer settingId = childId;
			childSettingService.updateChildSetting(settingId,moreSettingList);
			
			//push message to child
			Child child = childService.getById(childId);
			if( child != null ){
				String registionId = child.getRegistionId();
				if( registionId != null ){
					Map<String,String> extra = new HashMap<String,String>();
					pushService.pushMessageToChildByRegistionId(registionId, PushConstants.MSG_CONTENT_APPLY_SETTING_CHANGES, extra);
				}
			}

			view.setData(true);
		} catch (Exception e) {
			view.setData(false);
			view.setInfo("Apply child setting error.");
		}
		return view;
	}

	@Override
	public ViewDTO<Boolean> applyChildAppChanges(Integer childId,
			List<AppManageSettingParam> appManageSettingList)
			throws ServiceException {
		if( childId == null || appManageSettingList == null ){
			throw new ServiceException("Parameter childId or appManageSettingList can not be null.");
		}
		ViewDTO<Boolean> view = new ViewDTO<Boolean>();
		try {
			appService.updateApp(childId,appManageSettingList);
			
			
			
			//push message to child
			Child child = childService.getById(childId);
			if( child != null ){
				String registionId = child.getRegistionId();
				if( registionId != null ){
					Map<String,String> extra = new HashMap<String,String>();
					pushService.pushMessageToChildByRegistionId(registionId, PushConstants.MSG_CONTENT_APPLY_APP_CHANGES, extra);
				}
			}
			
			view.setData(true);
		} catch (Exception e) {
			view.setData(false);
			view.setInfo("Apply child app lock/unlock status error.");
		}
		return view;
	}

	@Override
	public ViewDTO<Boolean> lockAllAppByChild(Integer childId)
			throws ServiceException {
		ViewDTO<Boolean> view = new ViewDTO<Boolean>();
		
		boolean success = appService.lockAllAppByChild(childId);
		view.setData(success);
		return view;
	}

	@Override
	public ViewDTO<Boolean> unlockAllAppByChild(Integer childId)
			throws ServiceException {
		ViewDTO<Boolean> view = new ViewDTO<Boolean>();
		
		boolean success = appService.unlockAllAppByChild(childId);
		view.setData(success);
		return view;
	}

	@Override
	public ViewDTO<PresetLockViewDTO> loadPresetLockData(Integer childId)
			throws ServiceException {
		ViewDTO<PresetLockViewDTO> view = new ViewDTO<PresetLockViewDTO>();
		if( childId == null ){
			throw new ServiceException("Parameter childId can not be null.");
		}
		
		view = presetLockService.loadPresetLockData(childId);
		
		return view;
	}

	@Override
	public ViewDTO<Boolean> applyPresetLock(Integer childId,
			ApplyPresetLockParam applyPresetLockParam) throws ServiceException {
		if( childId == null || applyPresetLockParam == null ){
			throw new ServiceException("Parameter childId or applyPresetLockParam can not be null.");
		}
		
		ViewDTO<Boolean> view = new ViewDTO<Boolean>();
		
		try {
			Integer presetLockId = childId;
			
			PresetLock presetLock = new PresetLock(presetLockId);
			fillData(presetLock,applyPresetLockParam);
			presetLockService.saveOrUpdate(presetLock);
			
			// update locked app
			presetLockAppService.updatePresetLockApp(presetLock.getId(),applyPresetLockParam.getAppIdList());
			
			//TODO: if preset lock switch is on , send message to child end app 
			
			view.setData(true);
			return view;
		} catch (Exception e) {
			logger.error("apply preset lock error.",e);
			view.setData(false);
			view.setMsg(ViewDTO.MSG_ERROR);
			return view;
		}
	}

	private void fillData(PresetLock presetLock,
			ApplyPresetLockParam applyPresetLockParam)throws ServiceException {
		if( presetLock == null || applyPresetLockParam == null ){
			return ;
		}
		
		presetLock.setStartTime(applyPresetLockParam.getStartTime());
		presetLock.setEndTime(applyPresetLockParam.getEndTime());
		presetLock.setLockCallStatus(applyPresetLockParam.getLockCallStatus());
		presetLock.setPresetOnOff(applyPresetLockParam.getPresetOnOff());
		
		List<Boolean> repeatList = applyPresetLockParam.getReapeat();
		if( repeatList != null && repeatList.size() == 7 ){
			presetLock.setRepeatMonday(repeatList.get(0));
			presetLock.setRepeatTuesday(repeatList.get(1));
			presetLock.setRepeatWednesday(repeatList.get(2));
			presetLock.setRepeatThurday(repeatList.get(3));
			presetLock.setRepeatFriday(repeatList.get(4));
			presetLock.setRepeatSaturday(repeatList.get(5));
			presetLock.setRepeatSunday(repeatList.get(6));
		}
	}
	
	
	
}
