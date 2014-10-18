package eden.sun.childrenguard.server.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dao.generated.RelationshipMapper;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.Relationship;
import eden.sun.childrenguard.server.service.IRelationshipService;

@Service
public class RelationshipServiceImpl extends BaseServiceImpl implements IRelationshipService{
	@Inject
	private RelationshipMapper relationshipMapper;
	
	@Override
	public boolean isExists(Integer relationshipId) throws ServiceException {
		Relationship relationship = relationshipMapper.selectByPrimaryKey(relationshipId);
		
		if( relationship == null ){
			return false;
		}else{
			return true;
		}
	}

}
