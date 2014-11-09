package eden.sun.childrenguard.server.service;

import java.util.List;

import eden.sun.childrenguard.server.dto.AppViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.UploadApplicationInfoParam;
import eden.sun.childrenguard.server.exception.ServiceException;

public interface IChildAppService {

	ViewDTO<AppViewDTO> installApp(String imei,
			UploadApplicationInfoParam appInfo)throws ServiceException;

	ViewDTO<Boolean> uninstallApp(String imei,
			UploadApplicationInfoParam appInfo)throws ServiceException;

	ViewDTO<Boolean> uploadAllApp(String imei,
			List<UploadApplicationInfoParam> appList)throws ServiceException;

	ViewDTO<List<AppViewDTO>> listAppByChildImei(String imei)throws ServiceException;

}
