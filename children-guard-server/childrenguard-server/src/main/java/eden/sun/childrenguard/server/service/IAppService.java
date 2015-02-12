package eden.sun.childrenguard.server.service;

import java.util.List;

import eden.sun.childrenguard.server.dto.AppViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.AppManageSettingParam;
import eden.sun.childrenguard.server.dto.param.UploadApplicationInfoParam;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.App;

public interface IAppService {

	List<AppViewDTO> listViewByChildId(Integer childId)throws ServiceException;

	boolean addOrUpdate(Integer childId, UploadApplicationInfoParam appInfo)throws ServiceException;

	void deleteApp(Integer childId, UploadApplicationInfoParam appInfo)throws ServiceException;

	void clearAppInfoByChildId(Integer childId)throws ServiceException;

	void saveOrUpdateAll(Integer childId,List<UploadApplicationInfoParam> appList)throws ServiceException;

	void updateApp(Integer childId,
			List<AppManageSettingParam> appManageSettingList)throws ServiceException;

	AppViewDTO getViewByPackageName(String packageName)throws ServiceException;

	boolean lockAllAppByChild(Integer childId)throws ServiceException;

	boolean unlockAllAppByChild(Integer childId)throws ServiceException;

	List<AppViewDTO> getApps(List<Integer> appIdList)throws ServiceException;

	List<App> listByChildId(Integer childId)throws ServiceException;

	App getById(Integer appId)throws ServiceException;

	ViewDTO<Boolean> updateAppLockStatus(Integer appId, Boolean lockStatus)throws ServiceException;

}
