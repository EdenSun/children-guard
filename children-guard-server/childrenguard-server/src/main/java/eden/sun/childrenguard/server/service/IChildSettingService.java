package eden.sun.childrenguard.server.service;

import java.util.List;

import eden.sun.childrenguard.server.dto.ChildSettingViewDTO;
import eden.sun.childrenguard.server.dto.param.MoreSettingParam;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.ChildSetting;

public interface IChildSettingService {

	ChildSetting addIfNotExists(Integer childId)throws ServiceException;

	void update(ChildSetting childSetting)throws ServiceException;

	ChildSettingViewDTO getViewById(Integer childId)throws ServiceException;

	void updateChildSetting(Integer settingId,
			List<MoreSettingParam> moreSettingList)throws ServiceException;

	ChildSetting getById(Integer id)throws ServiceException;

	boolean isInstallSwitchOn(Integer settingId)throws ServiceException;

	boolean isUnInstallSwitchOn(Integer settingId)throws ServiceException;

}
