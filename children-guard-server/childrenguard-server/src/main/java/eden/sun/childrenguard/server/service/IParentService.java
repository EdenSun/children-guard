package eden.sun.childrenguard.server.service;

import eden.sun.childrenguard.server.dto.ParentViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.Parent;


public interface IParentService {

	ParentViewDTO getViewByEmail(String email)throws ServiceException;

	boolean doLogin(String email, String password)throws ServiceException;

	ParentViewDTO save(String imei,String firstName, String lastName, String email,
			String password)throws ServiceException;

	Parent getByEmail(String email)throws ServiceException;

	ParentViewDTO getViewByEmailAndPassword(String email, String password)throws ServiceException;

	boolean update(Parent parent)throws ServiceException;

	Parent getByAccessToken(String accessToken)throws ServiceException;

	Parent getById(Integer parentId)throws ServiceException;

	
}
