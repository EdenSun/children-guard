package eden.sun.childrenguard.server.service;

import eden.sun.childrenguard.server.exception.ServiceException;

public interface IRelationshipService {

	boolean isExists(Integer relationshipId)throws ServiceException;

}
