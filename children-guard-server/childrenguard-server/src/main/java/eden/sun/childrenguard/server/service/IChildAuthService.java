package eden.sun.childrenguard.server.service;

import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;

public interface IChildAuthService {

	ViewDTO<ChildViewDTO> login(String imei)throws ServiceException;

	
}
