package eden.sun.childrenguard.server.service;

import java.util.List;

import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.dto.ParentViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.Parent;


public interface IParentService {

	ParentViewDTO getViewByEmail(String email)throws ServiceException;

	boolean doLogin(String email, String password)throws ServiceException;

	ParentViewDTO save(String firstName, String lastName, String email,
			String password)throws ServiceException;

	Parent getByEmail(String email)throws ServiceException;

	ParentViewDTO getViewByEmailAndPassword(String email, String password)throws ServiceException;

	boolean update(Parent parent)throws ServiceException;

	Parent getByAccessToken(String accessToken)throws ServiceException;

	
}
