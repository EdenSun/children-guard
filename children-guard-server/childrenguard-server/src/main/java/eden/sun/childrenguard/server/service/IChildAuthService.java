package eden.sun.childrenguard.server.service;

import eden.sun.childrenguard.server.dto.ChildLoginViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;

public interface IChildAuthService {

	ViewDTO<ChildLoginViewDTO> login(String mobile)throws ServiceException;

	
}
