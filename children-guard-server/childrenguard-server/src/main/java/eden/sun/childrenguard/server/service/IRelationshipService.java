package eden.sun.childrenguard.server.service;

import java.util.List;

import eden.sun.childrenguard.server.dto.RelationshipViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;

public interface IRelationshipService {

	boolean isExists(Integer relationshipId)throws ServiceException;

	ViewDTO<List<RelationshipViewDTO>> listAll()throws ServiceException;

}
