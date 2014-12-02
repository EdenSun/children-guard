package eden.sun.childrenguard.server.service;

import java.util.List;

import eden.sun.childrenguard.server.dto.AppViewDTO;
import eden.sun.childrenguard.server.dto.ChildBasicInfoViewDTO;
import eden.sun.childrenguard.server.dto.ChildSettingViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.AppManageSettingParam;
import eden.sun.childrenguard.server.dto.param.MoreSettingParam;
import eden.sun.childrenguard.server.dto.param.SyncAppSettingParam;
import eden.sun.childrenguard.server.exception.ServiceException;

public interface IChildDetailService {

	ViewDTO<List<AppViewDTO>> listChildApp(Integer childId)throws ServiceException;

	ViewDTO<ChildBasicInfoViewDTO> getChildBasicInfo(Integer childId)throws ServiceException;

	ViewDTO<Boolean> modifyLockPassword(Integer childId, String password)throws ServiceException;

	ViewDTO<Boolean> modifySpeedLimit(Integer childId, Integer speed)throws ServiceException;

	ViewDTO<ChildSettingViewDTO> loadChildSetting(Integer childId)throws ServiceException;

	ViewDTO<Boolean> applyChildSettingMoreChanges(Integer childId,
			List<MoreSettingParam> moreSettingList)throws ServiceException;

	ViewDTO<Boolean> applyChildAppChanges(Integer childId,
			List<AppManageSettingParam> appManageSettingList)throws ServiceException;

	ViewDTO<Boolean> lockAllAppByChild(Integer childId)throws ServiceException;

	ViewDTO<Boolean> unlockAllAppByChild(Integer childId)throws ServiceException;



}
