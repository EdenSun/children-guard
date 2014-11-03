package eden.sun.childrenguard.server.service;

import java.util.List;

import eden.sun.childrenguard.server.dto.AppViewDTO;
import eden.sun.childrenguard.server.dto.ChildBasicInfoViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.SyncAppSettingParam;
import eden.sun.childrenguard.server.exception.ServiceException;

public interface IChildDetailService {

	ViewDTO<List<AppViewDTO>> listChildApp(Integer childId)throws ServiceException;

	ViewDTO<Boolean> syncChildApp(SyncAppSettingParam param)throws ServiceException;

	ViewDTO<ChildBasicInfoViewDTO> getChildBasicInfo(Integer childId)throws ServiceException;

	ViewDTO<Boolean> modifyLockPassword(Integer childId, String password)throws ServiceException;

}
