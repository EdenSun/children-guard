package eden.sun.childrenguard.server.service;

import java.util.List;

import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;

public interface IChildOfParentsService {

	List<ChildViewDTO> listChildrenViewByParentId(Integer parentId)throws ServiceException;

}
