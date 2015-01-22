package eden.sun.childrenguard.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.ControlSettingApplyParam;
import eden.sun.childrenguard.server.dto.param.SettingApplyParam;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.ChildSetting;
import eden.sun.childrenguard.server.service.IChildSettingService;
import eden.sun.childrenguard.server.service.IPersonSettingService;

@Service
public class PersonSettingServiceServiceImpl implements IPersonSettingService {
	@Autowired
	private IChildSettingService childSettingService;
	
	
	@Override
	public ViewDTO<Boolean> doApply(SettingApplyParam applyParam)
			throws ServiceException {
		if( applyParam == null ){
			throw new ServiceException("Parameter apply param is not exists.");
		}
		Integer childId = applyParam.getChildId();
		Integer settingId = childId;
		ChildSetting setting = childSettingService.getById(settingId);
		if( setting == null ){
			throw new ServiceException("Setting is not exists.");
		}
		
		if( applyParam.getAppLockUnlockNotificationSwitch() != null ){
			setting.setAppLockUnlockNotificationSwitch(applyParam.getAppLockUnlockNotificationSwitch());
		}
		
		if( applyParam.getUninstallAppNotificationSwitch() != null ){
			setting.setUninstallAppNotificationSwitch(applyParam.getUninstallAppNotificationSwitch());
		}
		
		if( applyParam.getNewAppNotificationSwitch() != null ){
			setting.setNewAppNotificationSwitch(applyParam.getNewAppNotificationSwitch());
		}
		
		if( applyParam.getSpeedingNotificationSwitch() != null ){
			setting.setSpeedingNotificationSwitch(applyParam.getSpeedingNotificationSwitch());
		}
	
		childSettingService.update(setting);
		
		ViewDTO<Boolean> view = new ViewDTO<Boolean>();
		view.setData(true);
		return view;
	}


	@Override
	public ViewDTO<Boolean> doApplyControlSetting(
			ControlSettingApplyParam applyParam) throws ServiceException {
		if( applyParam == null ){
			throw new ServiceException("Parameter apply param is not exists.");
		}
		Integer childId = applyParam.getChildId();
		Integer settingId = childId;
		ChildSetting setting = childSettingService.getById(settingId);
		if( setting == null ){
			throw new ServiceException("Setting is not exists.");
		}
		
		if( applyParam.getLockCallsSwitch() != null ){
			setting.setLockCallsSwitch(applyParam.getLockCallsSwitch());
		}
		
		if( applyParam.getWifiOnlySwitch() != null ){
			setting.setWifiOnlySwitch(applyParam.getWifiOnlySwitch());
		}
		
		if( applyParam.getSpeedingLimit() != null){
			setting.setSpeedingLimit(applyParam.getSpeedingLimit());
		}
		
		childSettingService.update(setting);
		
		ViewDTO<Boolean> view = new ViewDTO<Boolean>();
		view.setData(true);
		return view;
	}
	
	

}
