package eden.sun.childrenguard.server.service;

import java.util.List;

import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.Parent;

public interface IParentChildService {

	void addRelationship(Integer parentId, Integer childId, Integer relationshipId)throws ServiceException;

	void deleteRelationByChild(Integer childId)throws ServiceException;

	boolean isChildBelongTo(Integer childId, Integer parentId)throws ServiceException;

	List<Parent> getParentByChildId(Integer childId)throws ServiceException;

	void addRelationship(Integer parentId, Integer childId)throws ServiceException;

}
