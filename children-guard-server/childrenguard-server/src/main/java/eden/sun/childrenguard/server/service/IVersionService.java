package eden.sun.childrenguard.server.service;

import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;

public interface IVersionService {

	ViewDTO<Boolean> isInTrial(String mobile)throws ServiceException;

}
