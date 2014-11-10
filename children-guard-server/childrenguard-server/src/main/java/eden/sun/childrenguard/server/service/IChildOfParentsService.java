package eden.sun.childrenguard.server.service;

import java.util.List;

import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.Parent;

public interface IChildOfParentsService {

	List<ChildViewDTO> listChildrenViewByParentId(Integer parentId)throws ServiceException;

	//boolean isChildBelongTo(Integer childId, Integer parentId)throws ServiceException;

}
