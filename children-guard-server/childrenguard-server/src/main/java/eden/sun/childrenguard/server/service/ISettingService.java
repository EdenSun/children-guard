package eden.sun.childrenguard.server.service;

import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;

public interface ISettingService {

	ViewDTO<Boolean> saveEmail(String accessToken, String email)throws ServiceException;

}
