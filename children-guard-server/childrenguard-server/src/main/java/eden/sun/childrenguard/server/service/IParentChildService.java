package eden.sun.childrenguard.server.service;

import eden.sun.childrenguard.server.exception.ServiceException;

public interface IParentChildService {

	void addRelationship(Integer parentId, Integer childId, Integer relationshipId)throws ServiceException;

}