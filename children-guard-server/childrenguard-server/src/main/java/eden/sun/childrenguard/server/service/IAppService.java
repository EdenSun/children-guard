package eden.sun.childrenguard.server.service;

import java.util.List;

import eden.sun.childrenguard.server.dto.AppViewDTO;
import eden.sun.childrenguard.server.dto.param.AppManageSettingParam;
import eden.sun.childrenguard.server.dto.param.UploadApplicationInfoParam;
import eden.sun.childrenguard.server.exception.ServiceException;

public interface IAppService {

	List<AppViewDTO> listViewByChildId(Integer childId)throws ServiceException;

	boolean addOrUpdate(Integer childId, UploadApplicationInfoParam appInfo)throws ServiceException;

	void deleteApp(Integer childId, UploadApplicationInfoParam appInfo)throws ServiceException;

	void clearAppInfoByChildId(Integer childId)throws ServiceException;

	void saveOrUpdateAll(Integer childId,List<UploadApplicationInfoParam> appList)throws ServiceException;

	void updateApp(Integer childId,
			List<AppManageSettingParam> appManageSettingList)throws ServiceException;

}
