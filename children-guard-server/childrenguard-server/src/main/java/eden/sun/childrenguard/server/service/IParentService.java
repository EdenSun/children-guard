package eden.sun.childrenguard.server.service;

import eden.sun.childrenguard.server.dto.ParentViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;


public interface IParentService {

	ParentViewDTO getViewByEmail(String email)throws ServiceException;

	boolean doLogin(String email)throws ServiceException;

	ParentViewDTO save(String firstName, String lastName, String email,
			String password)throws ServiceException;

	
}
