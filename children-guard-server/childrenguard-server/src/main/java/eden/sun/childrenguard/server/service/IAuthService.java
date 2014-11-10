package eden.sun.childrenguard.server.service;

import eden.sun.childrenguard.server.dto.IsFirstLoginViewDTO;
import eden.sun.childrenguard.server.dto.LoginViewDTO;
import eden.sun.childrenguard.server.dto.RegisterViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;

public interface IAuthService {

	ViewDTO<LoginViewDTO> login(String email, String password)throws ServiceException;

	ViewDTO<RegisterViewDTO> register(String imei,String firstName, String lastName,
			String email, String password)throws ServiceException;

	ViewDTO<String> resetPasswordByMail(String email)throws ServiceException;

	ViewDTO<IsFirstLoginViewDTO> isFirstLogin(String email, String password)throws ServiceException;

	
}
