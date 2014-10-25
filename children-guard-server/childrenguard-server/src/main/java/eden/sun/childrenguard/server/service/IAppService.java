package eden.sun.childrenguard.server.service;

import java.util.List;

import eden.sun.childrenguard.server.dto.AppViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;

public interface IAppService {

	List<AppViewDTO> listViewByChildId(Integer childId)throws ServiceException;

}
