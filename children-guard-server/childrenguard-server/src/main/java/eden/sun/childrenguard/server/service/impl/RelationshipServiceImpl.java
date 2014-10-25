package eden.sun.childrenguard.server.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dao.generated.RelationshipMapper;
import eden.sun.childrenguard.server.dto.RelationshipViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.Relationship;
import eden.sun.childrenguard.server.model.generated.RelationshipExample;
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

	@Override
	public ViewDTO<List<RelationshipViewDTO>> listAll() throws ServiceException {
		ViewDTO<List<RelationshipViewDTO>> view = new ViewDTO<List<RelationshipViewDTO>>();
		
		List<Relationship> relationships = relationshipMapper.selectByExample(new RelationshipExample());
		List<RelationshipViewDTO> relationshipViewDTOList = trans2RelationshipViewDTOList(relationships);
		
		view.setData(relationshipViewDTOList);
		return view;
	}

	private List<RelationshipViewDTO> trans2RelationshipViewDTOList(
			List<Relationship> relationships) {
		if( relationships == null ){
			return null;
		}
		List<RelationshipViewDTO> list = new ArrayList<RelationshipViewDTO>();
		RelationshipViewDTO view = null;
		
		for(Iterator<Relationship> it = relationships.iterator(); it.hasNext();){
			Relationship relationship = it.next();
			view = trans2RelationshipViewDTO(relationship);
			if( view != null ){
				list.add(view);
			}
		}
		return list;
	}

	private RelationshipViewDTO trans2RelationshipViewDTO(
			Relationship relationship) {
		if( relationship == null ){
			return null;
		}
		RelationshipViewDTO view = new RelationshipViewDTO();
		BeanUtils.copyProperties(relationship, view);
		
		return view;
	}

}
