package eden.sun.childrenguard.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dao.ChildOfParentsMapper;
import eden.sun.childrenguard.server.dao.generated.ChildMapper;
import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.ChildAddParam;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.ChildOfParents;
import eden.sun.childrenguard.server.model.generated.Child;
import eden.sun.childrenguard.server.model.generated.ChildExample;
import eden.sun.childrenguard.server.model.generated.ChildExample.Criteria;
import eden.sun.childrenguard.server.model.generated.Parent;
import eden.sun.childrenguard.server.service.IChildService;
import eden.sun.childrenguard.server.service.IParentChildService;

@Service
public class ChildServiceImpl implements IChildService {
	@Autowired
	private ChildMapper childMapper;
	@Autowired
	private ChildOfParentsMapper childOfParentsMapper;
	@Autowired
	private IParentChildService parentChildService;
	
	@Override
	public ViewDTO<List<ChildViewDTO>> listAllViewByParentId(Integer parentId)
			throws ServiceException {
		ViewDTO<List<ChildViewDTO>> view = new ViewDTO<List<ChildViewDTO>>();
		
		List<ChildOfParents> childList = childOfParentsMapper.selectAllByParentId(parentId);
		List<ChildViewDTO> childViewDTOList = trans2ChildViewDTO(childList);
		
		view.setData(childViewDTOList);
		view.setMsg(ViewDTO.MSG_SUCCESS);
		return view;
	}

	private List<ChildViewDTO> trans2ChildViewDTO(List<ChildOfParents> childList) {
		if( childList == null ){
			return null;
		}
		
		List<ChildViewDTO> childViewDTOList = new ArrayList<ChildViewDTO>();
		ChildViewDTO dto = null;
		for(int i=0;i<childList.size();i++){
			ChildOfParents child = childList.get(i);
			dto = trans2ChildViewDTO(child);
			if( dto != null ){
				childViewDTOList.add(dto);
			}
		}
		return childViewDTOList;
	}

	private ChildViewDTO trans2ChildViewDTO(ChildOfParents child) {
		if( child == null ){
			return null;
		}
		
		ChildViewDTO dto = new ChildViewDTO();
		BeanUtils.copyProperties(child, dto);
		return dto;
	}

	@Override
	public ViewDTO<ChildViewDTO> add(String mobile, String nickname)
			throws ServiceException {
		ViewDTO<ChildViewDTO> view = new ViewDTO<ChildViewDTO>();
		
		Child child = new Child();
		child.setCreateTime(new Date());
		child.setMobile(mobile);
		child.setNickname(nickname);
		
		childMapper.insert(child);
		
		ChildViewDTO dto = trans2ChildViewDTO(child);
		view.setData(dto);
		view.setMsg(ViewDTO.MSG_SUCCESS);
		return view;
	}

	private ChildViewDTO trans2ChildViewDTO(Child child) {
		if( child == null ){
			return null;
		}
		ChildViewDTO dto = new ChildViewDTO();
		BeanUtils.copyProperties(child, dto);
		return dto;
	}

	private List<ChildViewDTO> trans2ChildViewDTOList(List<Child> childList) {
		if( childList == null ){
			return null;
		}
		List<ChildViewDTO> viewList = new ArrayList<ChildViewDTO>();
		ChildViewDTO view = null;
		
		for(Iterator<Child> it = childList.iterator();it.hasNext();){
			Child child = it.next();
			view = trans2ChildViewDTO(child);
			if( view != null ){
				viewList.add(view);
			}
		}
		
		return viewList;
	}

	@Override
	public ChildViewDTO add(ChildAddParam param) throws ServiceException {
		if( param == null ){
			throw new ServiceException("parameter can not be null");
		}
		
		Child child = trans2Child(param);
		
		childMapper.insert(child);
		
		ChildViewDTO dto = trans2ChildViewDTO(child);
		
		return dto;
	}

	private Child trans2Child(ChildAddParam param) {
		if( param == null ){
			return null;
		}
		
		Child child = new Child();
		child.setCreateTime(new Date());
		child.setFirstName(param.getFirstName());
		child.setLastName(param.getLastName());
		child.setMobile(param.getMobile());
		child.setNickname(param.getNickname());
		
		return child;
	}

	@Override
	public ChildViewDTO getChildViewByMobile(String mobile)
			throws ServiceException {
		if( mobile == null ){
			throw new ServiceException("Parameter mobile can not be null.");
		}
		Child child = getByMobile(mobile);
		ChildViewDTO view = trans2ChildViewDTO(child);
		
		return view;
	}

	private Child getByMobile(String mobile) {
		ChildExample example = new ChildExample();
		Criteria criteria = example.createCriteria();
		criteria.andMobileEqualTo(mobile);
		
		List<Child> childList = childMapper.selectByExample(example);
	
		if( childList != null && childList.size() > 0 ){
			Child child = childList.get(0);
			return child;
		}
		return null;
	}

	@Override
	public ChildViewDTO deleteChild(Integer childId) throws ServiceException {
		if( childId == null ){
			throw new ServiceException("Parameter childId can not be null.");
		}
		
		/* check whether child is exists
		 * if exists: delete child ,and delete all relevant relationship with this child, and return child view dto
		 * if not exists: throw exception
		 */
		Child child = this.getById(childId);
		ChildViewDTO childView = this.trans2ChildViewDTO(child);
		if( child == null ){
			// condition: child is not exists
			throw new ServiceException("Child is not exists.");
		}
		
		//condition: child is exists
		//delete child
		boolean isSuccess = this.deleteById(childId);
		
		if( isSuccess ){
			/* delete child success
			 * delete relevant relationship
			 */
			
			parentChildService.deleteRelationByChild(childId);
			
			return childView;
		}
		
		return null;
	}


	@Override
	public Child getById(Integer childId) throws ServiceException {
		Child child = childMapper.selectByPrimaryKey(childId);
		
		return child;
	}

	private boolean deleteById(Integer childId) {
		int cnt = childMapper.deleteByPrimaryKey(childId);
		if( cnt == 0 ){
			return false;
		}
		
		return true;
	}

	@Override
	public ChildViewDTO getViewById(Integer childId) throws ServiceException {
		return this.trans2ChildViewDTO(getById(childId));
	}

	@Override
	public Child getChildByAccessToken(String childAccessToken)
			throws ServiceException {
		ChildExample example = new ChildExample();
		Criteria criteria = example.createCriteria();
		criteria.andAccessTokenEqualTo(childAccessToken);
		
		List<Child> childList = childMapper.selectByExample(example);
	
		if( childList != null && childList.size() > 0 ){
			Child child = childList.get(0);
			return child;
		}
		return null;
	}

	@Override
	public void update(Child child) throws ServiceException {
		if( child == null || child.getId() == null ){
			throw new ServiceException("Parameter child or child.id can not be null");
		}
		
		childMapper.updateByPrimaryKey(child);
	}

	@Override
	public ChildViewDTO getViewByImei(String imei) throws ServiceException {
		if( imei == null ){
			throw new ServiceException("Parameter imei can not be null");
		}
		
		Child child = getChildByImei(imei);
		
		return trans2ChildViewDTO(child);
	}

	public Child getChildByImei(String imei) {
		ChildExample example = new ChildExample();
		Criteria criteria = example.createCriteria();
		criteria.andImeiEqualTo(imei);
		
		List<Child> childList = childMapper.selectByExample(example);
	
		if( childList != null && childList.size() > 0 ){
			Child child = childList.get(0);
			return child;
		}
		return null;
	}
	
	

	@Override
	public ChildViewDTO getChildViewByImei(String imei) throws ServiceException {
		return trans2ChildViewDTO(getChildByImei(imei));
	}

	@Override
	public Child getChildByMobile(String childMobile) throws ServiceException {
		ChildExample example = new ChildExample();
		Criteria criteria = example.createCriteria();
		criteria.andMobileEqualTo(childMobile);
		
		List<Child> childList = childMapper.selectByExample(example);
	
		if( childList != null && childList.size() > 0 ){
			Child child = childList.get(0);
			return child;
		}
		return null;
	}

	@Override
	public List<Parent> getParentsByChildId(Integer childId) throws ServiceException {
		return parentChildService.getParentByChildId(childId);
	}

	@Override
	public ViewDTO<Boolean> saveOrUpdateRegistionId(String imei,
			String registionId) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
