package eden.sun.childrenguard.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dto.ViewDTO;
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
		
		setting.setAppLockUnlockNotificationSwitch(applyParam.getAppLockUnlockNotificationSwitch());
		setting.setUninstallAppNotificationSwitch(applyParam.getUninstallAppNotificationSwitch());
		setting.setNewAppNotificationSwitch(applyParam.getNewAppNotificationSwitch());
		setting.setSpeedingNotificationSwitch(applyParam.getSpeedingNotificationSwitch());
	
		save
		
		ViewDTO<Boolean> view = new ViewDTO<Boolean>();
		view.setData(true);
		return view;
	}

}
