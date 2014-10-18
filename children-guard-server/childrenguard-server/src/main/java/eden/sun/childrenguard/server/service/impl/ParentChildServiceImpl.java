package eden.sun.childrenguard.server.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dao.generated.ParentChildMapper;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.ParentChild;
import eden.sun.childrenguard.server.model.generated.ParentChildExample;
import eden.sun.childrenguard.server.model.generated.ParentChildExample.Criteria;
import eden.sun.childrenguard.server.service.IParentChildService;
import eden.sun.childrenguard.server.service.IRelationshipService;

@Service
public class ParentChildServiceImpl extends BaseServiceImpl implements IParentChildService{
	@Inject
	private ParentChildMapper parentChildMapper;
	@Inject
	private IRelationshipService relationshipService;
	@Override
	public void addRelationship(Integer parentId, Integer childId,
			Integer relationshipId) throws ServiceException {
		/* check whether relationship between parent and child has been created
		 * if created: only update their relationship
		 * if not created : create relationship
		 */
		
		ParentChild parentChild = getRelationship(parentId,childId);
		
		if( parentChild != null ){
			// relationship has been created, update relationship
			
			boolean isExists = relationshipService.isExists(relationshipId);
			
			if( isExists ) {
				parentChild.setRelationshipId(relationshipId);
				parentChildMapper.updateByPrimaryKey(parentChild);
			}
		}else{
			// has not created, create one for them
			parentChild = new ParentChild();
			parentChild.setChildId(childId);
			parentChild.setParentId(parentId);
			parentChild.setRelationshipId(relationshipId);
			parentChild.setCreateTime(new Date());
			
			parentChildMapper.insert(parentChild);
		}
		
	}

	private ParentChild getRelationship(Integer parentId, Integer childId) {
		ParentChildExample example = new ParentChildExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		criteria.andChildIdEqualTo(childId);
		
		List<ParentChild> parentChildList = parentChildMapper.selectByExample(example);
		
		if( parentChildList != null && parentChildList.size() > 0 ){
			return parentChildList.get(0);
		}
		return null;
	}

	@Override
	public void deleteRelationByChild(Integer childId) throws ServiceException {
		if( childId == null ){
			String errMsg = "Parameter childId can not be null.";
			logger.error(errMsg);
			throw new ServiceException(errMsg);
		}
		
		ParentChildExample example = new ParentChildExample();
		Criteria criteria = example.createCriteria();
		criteria.andChildIdEqualTo(childId);
		
		parentChildMapper.deleteByExample(example);
	}
	
}
