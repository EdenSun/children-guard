package eden.sun.childrenguard.server.service;

import eden.sun.childrenguard.server.exception.ServiceException;

public interface IParentChildService {

	void addRelationship(Integer parentId, Integer childId, Integer relationshipId)throws ServiceException;

	void deleteRelationByChild(Integer childId)throws ServiceException;

	boolean isChildBelongTo(Integer childId, Integer parentId)throws ServiceException;

}
