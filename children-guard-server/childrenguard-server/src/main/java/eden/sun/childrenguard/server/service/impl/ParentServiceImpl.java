package eden.sun.childrenguard.server.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dao.ChildOfParentsMapper;
import eden.sun.childrenguard.server.dao.generated.ParentMapper;
import eden.sun.childrenguard.server.dto.ParentViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.Parent;
import eden.sun.childrenguard.server.model.generated.ParentExample;
import eden.sun.childrenguard.server.model.generated.ParentExample.Criteria;
import eden.sun.childrenguard.server.service.IChildService;
import eden.sun.childrenguard.server.service.IParentService;
import eden.sun.childrenguard.server.util.UUIDUtil;

@Service
public class ParentServiceImpl implements IParentService {

	@Autowired
	private ParentMapper parentMapper;
	
	@Autowired
	private IChildService childService;
	
	@Autowired
	private ChildOfParentsMapper childOfParentsMapper;
	
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


	@Override
	public Parent getById(Integer parentId) throws ServiceException {
		return parentMapper.selectByPrimaryKey(parentId);
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
	public ParentViewDTO save(String imei,String firstName, String lastName, String email,
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
		parent.setImei(imei);
		
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


	@Override
	public Parent getByAccessToken(String accessToken) throws ServiceException {
		ParentExample example = new ParentExample();
		Criteria criteria = example.createCriteria();
		criteria.andAccessTokenEqualTo(accessToken);
		
		List<Parent> parentList = parentMapper.selectByExample(example);
	
		if( parentList != null && parentList.size() > 0 ){
			Parent parent = parentList.get(0);
			return parent;
		}
		return null;
	}


	
}
