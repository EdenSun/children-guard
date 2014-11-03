package eden.sun.childrenguard.server.service;

import eden.sun.childrenguard.server.dto.ChildSettingViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.ChildSetting;

public interface IChildSettingService {

	ChildSetting addIfNotExists(Integer childId)throws ServiceException;

	void update(ChildSetting childSetting)throws ServiceException;

	ChildSettingViewDTO getViewById(Integer childId)throws ServiceException;

}
