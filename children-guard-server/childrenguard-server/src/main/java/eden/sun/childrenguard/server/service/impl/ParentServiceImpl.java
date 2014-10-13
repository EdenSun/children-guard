package eden.sun.childrenguard.server.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dao.ParentMapper;
import eden.sun.childrenguard.server.dto.ParentViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.Parent;
import eden.sun.childrenguard.server.model.ParentExample;
import eden.sun.childrenguard.server.model.ParentExample.Criteria;
import eden.sun.childrenguard.server.service.IParentService;
import eden.sun.childrenguard.server.util.UUIDUtil;

@Service
public class ParentServiceImpl implements IParentService {

	@Inject
	private ParentMapper parentMapper;
	
	@Override
	public ParentViewDTO getViewByEmail(String email) throws ServiceException {
		Parent parent = getByEmail(email);
		ParentViewDTO parentView = trans2ParentViewDTO(parent);
		return parentView;
	}


	@Override
	public boolean doLogin(String email,String password) throws ServiceException {
		Parent parent = this.getByEmailAndPassword(email, password);
		if( parent == null ){
			return false;
		}
		String accessToken =  UUIDUtil.generateUUID();
		parent.setAccessToken(accessToken);
		parent.setLastLoginTime(new Date());
		
		int cnt = parentMapper.updateByPrimaryKey(parent);
		if( cnt == 0 ){
			return false;
		}
		
		return true;
	}

	private Parent getByEmailAndPassword(String email, String password) {
		ParentExample example = new ParentExample();
		Criteria criteria = example.createCriteria();
		criteria.andEmailEqualTo(email);
		criteria.andPasswordEqualTo(password);
		
		List<Parent> parentList = parentMapper.selectByExample(example);
	
		if( parentList != null && parentList.size() > 0 ){
			Parent parent = parentList.get(0);
			return parent;
		}
		return null;
	}


	public Parent getByEmail(String email) {
		ParentExample example = new ParentExample();
		Criteria criteria = example.createCriteria();
		criteria.andEmailEqualTo(email);
		
		List<Parent> parentList = parentMapper.selectByExample(example);
	
		if( parentList != null && parentList.size() > 0 ){
			Parent parent = parentList.get(0);
			return parent;
		}
		return null;
	}

	@Override
	public ParentViewDTO save(String firstName, String lastName, String email,
			String password) throws ServiceException {
		Date now = new Date();
		Parent parent = new Parent();
		String accessToken = UUIDUtil.generateUUID();
		parent.setAccessToken(accessToken);
		parent.setCreateTime(now);
		parent.setEmail(email);
		parent.setFirstName(firstName);
		parent.setLastLoginTime(now);
		parent.setLastName(lastName);
		parent.setPassword(password);
		
		int cnt = parentMapper.insert(parent);
		
		if( cnt == 0 ){
			return null;
		}
		
		return trans2ParentViewDTO(parent);
	}


	private ParentViewDTO trans2ParentViewDTO(Parent parent) {
		if( parent == null ) {
			return null;
		}
		ParentViewDTO view = new ParentViewDTO();
		BeanUtils.copyProperties(parent, view);
		return view;
	}


	@Override
	public ParentViewDTO getViewByEmailAndPassword(String email, String password)
			throws ServiceException {
		Parent parent = getByEmailAndPassword(email,password);
		return trans2ParentViewDTO(parent);
	}


	@Override
	public boolean update(Parent parent) throws ServiceException {
		int cnt = parentMapper.updateByPrimaryKey(parent);
		if( cnt == 0 ){
			return false;
		}
		
		return true;
	}
	
}
