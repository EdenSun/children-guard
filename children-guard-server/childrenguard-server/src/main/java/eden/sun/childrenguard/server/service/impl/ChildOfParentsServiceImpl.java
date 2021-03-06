package eden.sun.childrenguard.server.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dao.ChildOfParentsMapper;
import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.ChildOfParents;
import eden.sun.childrenguard.server.service.IChildOfParentsService;
import eden.sun.childrenguard.server.service.IChildService;

@Service
public class ChildOfParentsServiceImpl extends BaseServiceImpl implements IChildOfParentsService{

	@Autowired
	private ChildOfParentsMapper childOfParentsMapper;
	
	@Autowired
	private IChildService childService;
	
	@Override
	public List<ChildViewDTO> listChildrenViewByParentId(Integer parentId)
			throws ServiceException {
		if( parentId == null ){
			String errMsg = "parameter parentId can not be null";
			logger.error(errMsg);
			throw new ServiceException(errMsg);
		}
		List<ChildOfParents> childOfParentsList = childOfParentsMapper.selectAllByParentId(parentId);
		
		List<ChildViewDTO> childViewDTOList = trans2ChildViewDTOList(childOfParentsList);
		
		return childViewDTOList;
	}

	private List<ChildViewDTO> trans2ChildViewDTOList(
			List<ChildOfParents> childOfParentsList) {
		if( childOfParentsList == null ){
			return null;
		}
		List<ChildViewDTO> viewDTOList = new ArrayList<ChildViewDTO>();
		ChildViewDTO view = null;
		
		for(Iterator<ChildOfParents> it = childOfParentsList.iterator();it.hasNext();){
			ChildOfParents childOfParents = it.next();
			view = trans2ChildOfParents(childOfParents);
			if( view != null ){
				viewDTOList.add(view);
			}
		}
		return viewDTOList;
	}

	private ChildViewDTO trans2ChildOfParents(ChildOfParents childOfParents) {
		if( childOfParents == null ){
			return null;
		}
		ChildViewDTO chldViewDTO = new ChildViewDTO();
		BeanUtils.copyProperties(childOfParents, chldViewDTO);
		
		// set lock state
		boolean isInLockState = childService.isInLockState(childOfParents.getId());
		chldViewDTO.setInLockState(isInLockState);
		
		// set online state
		boolean isOnline = childService.isOnline(childOfParents.getId());
		chldViewDTO.setOnline(isOnline);
		
		return chldViewDTO;
	}

	/*@Override
	public boolean isChildBelongTo(Integer childId, Integer parentId)
			throws ServiceException {
		if( childId == null || parentId == null ){
			throw new ServiceException("Parameter parentId or childId can not be null");
		}
		
		List<ChildOfParents> list = childOfParentsMapper.selectByChildIdAndParentId(childId,parentId);
		
		if( list != null && list.size() > 0 ){
			return true;
		}
		return false;
	}*/
	
	

}
