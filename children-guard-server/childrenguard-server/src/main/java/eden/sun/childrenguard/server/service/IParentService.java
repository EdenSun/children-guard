package eden.sun.childrenguard.server.service;

import eden.sun.childrenguard.server.dto.ParentViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.Parent;


public interface IParentService {

	ParentViewDTO getViewByEmail(String email)throws ServiceException;

	boolean doLogin(String email, String password)throws ServiceException;

	ParentViewDTO save(String firstName, String lastName, String email,
			String password)throws ServiceException;

	Parent getByEmail(String email)throws ServiceException;

	ParentViewDTO getViewByEmailAndPassword(String email, String password)throws ServiceException;

	boolean update(Parent parent)throws ServiceException;

	
}
