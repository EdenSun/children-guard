package eden.sun.childrenguard.server.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dao.generated.ChildSettingMapper;
import eden.sun.childrenguard.server.dto.ChildSettingViewDTO;
import eden.sun.childrenguard.server.dto.param.MoreSettingParam;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.ChildSetting;
import eden.sun.childrenguard.server.service.IChildSettingService;
import eden.sun.childrenguard.server.util.ChildMoreSettingItemTypeConstants;
import eden.sun.childrenguard.server.util.NumberUtil;

@Service
public class ChildSettingServiceImpl implements IChildSettingService {
	@Inject
	ChildSettingMapper childSettingMapper;
	

	@Override
	public ChildSetting addIfNotExists(Integer childId) throws ServiceException {
		if( childId == null ){
			throw new ServiceException("Parameter child id can not be null.");
		}
		ChildSetting childSetting = childSettingMapper.selectByPrimaryKey(childId);
		if( childSetting == null ){
			Integer childSettingId = childId;
			childSetting = initChildSetting(childSettingId);
			
			childSettingMapper.insert(childSetting);
		}
		
		return childSettingMapper.selectByPrimaryKey(childId);
	}


	private ChildSetting initChildSetting(Integer childSettingId) {
		ChildSetting childSetting = new ChildSetting();
		childSetting.setId(childSettingId);
		childSetting.setAppLockUnlockNotificationSwitch(false);
		childSetting.setLockCallsSwitch(false);
		childSetting.setLockTextMessageSwitch(false);
		childSetting.setNewAppNotificationSwitch(false);
		childSetting.setSpeedingNotificationSwitch(false);
		childSetting.setUninstallAppNotificationSwitch(false);
		childSetting.setWifiOnlySwitch(false);
		
		childSettingMapper.insert(childSetting);
		return childSetting;
	}


	@Override
	public void update(ChildSetting childSetting) throws ServiceException {
		childSettingMapper.updateByPrimaryKey(childSetting);
	}


	@Override
	public ChildSettingViewDTO getViewById(Integer childId)
			throws ServiceException {
		if( childId == null ){
			throw new ServiceException("Parameter child id can not be null.");
		}
		
		ChildSetting childSetting = childSettingMapper.selectByPrimaryKey(childId);
		ChildSettingViewDTO view = trans2ChildSettingViewDTO(childSetting);
		return view;
	}


	private ChildSettingViewDTO trans2ChildSettingViewDTO(
			ChildSetting childSetting) {
		ChildSettingViewDTO view = new ChildSettingViewDTO();
		BeanUtils.copyProperties(childSetting, view);
		return view;
	}


	@Override
	public void updateChildSetting(Integer settingId,
			List<MoreSettingParam> moreSettingList) throws ServiceException {
		if( settingId == null || moreSettingList == null ){
			throw new ServiceException("Parameter settingId or moreSettingList can not be null. ");
		}
		ChildSetting childSetting = childSettingMapper.selectByPrimaryKey(settingId);
		
		for( MoreSettingParam param: moreSettingList ){
			Integer type = param.getType();
			Boolean booleanVal = param.getBooleanVal();
			String strVal = param.getStrVal();
			if( type.equals(ChildMoreSettingItemTypeConstants.APP_LOCK_UNLOCK_NOTIFICATION_SWITCH) ){
				childSetting.setAppLockUnlockNotificationSwitch(booleanVal);
			}else if( type.equals(ChildMoreSettingItemTypeConstants.LOCK_CALLS_SWITCH) ){
				childSetting.setLockCallsSwitch(booleanVal);
			}else if( type.equals(ChildMoreSettingItemTypeConstants.LOCK_TEXT_MESSAGE_SWITCH) ){
				childSetting.setLockTextMessageSwitch(booleanVal);
			}else if( type.equals(ChildMoreSettingItemTypeConstants.NEW_APP_NOTIFICATION_SWITCH) ){
				childSetting.setNewAppNotificationSwitch(booleanVal);
			}else if( type.equals(ChildMoreSettingItemTypeConstants.SPEEDING_LIMIT) ){
				childSetting.setSpeedingLimit(NumberUtil.toInteger(strVal));
			}else if( type.equals(ChildMoreSettingItemTypeConstants.SPEEDING_NOTIFICATION_SWITCH) ){
				childSetting.setSpeedingNotificationSwitch(booleanVal);
			}else if( type.equals(ChildMoreSettingItemTypeConstants.UNISTALL_APP_NOTIFICATION_SWITCH) ){
				childSetting.setUninstallAppNotificationSwitch(booleanVal);
			}else if( type.equals(ChildMoreSettingItemTypeConstants.WIFI_ONLY_SWITCH) ){
				childSetting.setWifiOnlySwitch(booleanVal);
			}
		}
		
		childSettingMapper.updateByPrimaryKey(childSetting);
		
	}

}
