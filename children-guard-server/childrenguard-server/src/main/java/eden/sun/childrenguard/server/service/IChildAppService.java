package eden.sun.childrenguard.server.service;

import java.util.List;

import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.UploadApplicationInfoParam;
import eden.sun.childrenguard.server.exception.ServiceException;

public interface IChildAppService {

	ViewDTO<Boolean> installApp(String childAccessToken,
			UploadApplicationInfoParam appInfo)throws ServiceException;

	ViewDTO<Boolean> uninstallApp(String childAccessToken,
			UploadApplicationInfoParam appInfo)throws ServiceException;

	ViewDTO<Boolean> uploadAllApp(String childAccessToken,
			List<UploadApplicationInfoParam> appList)throws ServiceException;

}
