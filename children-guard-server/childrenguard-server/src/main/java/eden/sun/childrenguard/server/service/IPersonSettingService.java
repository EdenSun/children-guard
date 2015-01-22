package eden.sun.childrenguard.server.service;

import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.ControlSettingApplyParam;
import eden.sun.childrenguard.server.dto.param.SettingApplyParam;
import eden.sun.childrenguard.server.exception.ServiceException;

public interface IPersonSettingService {

	ViewDTO<Boolean> doApply(SettingApplyParam applyParam)throws ServiceException;

	ViewDTO<Boolean> doApplyControlSetting(ControlSettingApplyParam applyParam)throws ServiceException;

}
