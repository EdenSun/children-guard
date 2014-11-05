package eden.sun.childrenguard.server.service;

import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;

public interface IChildAccountService {

	ViewDTO<ChildViewDTO> isActivate(String imei)throws ServiceException;

}
