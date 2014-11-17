package eden.sun.childrenguard.server.service;

import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.UpdateLocationParam;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.ChildExtraInfo;

public interface IChildExtraInfoService {

	ChildExtraInfo getById(Integer childId)throws ServiceException;

	ViewDTO<Boolean> updateLocation(String imei,
			UpdateLocationParam locationInfo)throws ServiceException;

}
