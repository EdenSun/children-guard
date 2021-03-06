package eden.sun.childrenguard.server.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dao.ChildOfParentsMapper;
import eden.sun.childrenguard.server.dao.generated.ParentMapper;
import eden.sun.childrenguard.server.dto.ParentViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
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
	public ParentViewDTO getViewByMobile(String mobile) throws ServiceException {
		Parent parent = getByMobile(mobile);
		ParentViewDTO parentView = trans2ParentViewDTO(parent);
		return parentView;
	}


	@Override
	public boolean doLogin(String mobile,String password) throws ServiceException {
		Parent parent = this.getByMobileAndPassword(mobile, password);
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

	

	public Parent getByMobileAndPassword(String mobile,
			String password) throws ServiceException {
		ParentExample example = new ParentExample();
		Criteria criteria = example.createCriteria();
		criteria.andMobileEqualTo(mobile);
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
	public ParentViewDTO getViewByMobileAndPassword(String mobile,
			String password) throws ServiceException {
		Parent parent = getByMobileAndPassword(mobile,password);
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


	@Override
	public ViewDTO<Boolean> saveOrUpdateRegistionId(String accessToken,
			String registionId) throws ServiceException {
		if( accessToken == null || registionId == null ){
			throw new ServiceException("Parameter can not be null.");
		}
		
		Parent parent = this.getByAccessToken(accessToken);
		
		if( parent == null ){
			throw new ServiceException("Guardian is not exists.");
		}
		
		parent.setRegistionId(registionId);
		parentMapper.updateByPrimaryKey(parent);
		
		ViewDTO<Boolean> view = new ViewDTO<Boolean>();
		view.setData(true);
		return view;
	}


	@Override
	public Parent getByImei(String imei) throws ServiceException {
		ParentExample example = new ParentExample();
		Criteria criteria = example.createCriteria();
		criteria.andImeiEqualTo(imei);
		
		List<Parent> parentList = parentMapper.selectByExample(example);
	
		if( parentList != null && parentList.size() > 0 ){
			Parent parent = parentList.get(0);
			return parent;
		}
		return null;
	}


	@Override
	public Parent getByMobile(String mobile) throws ServiceException {
		ParentExample example = new ParentExample();
		Criteria criteria = example.createCriteria();
		criteria.andMobileEqualTo(mobile);
		
		List<Parent> parentList = parentMapper.selectByExample(example);
	
		if( parentList != null && parentList.size() > 0 ){
			Parent parent = parentList.get(0);
			return parent;
		}
		return null;
	}


	@Override
	public ParentViewDTO save(String imei, String mobile, String password)
			throws ServiceException {
		Date now = new Date();
		Parent parent = new Parent();
		String accessToken = UUIDUtil.generateUUID();
		parent.setAccessToken(accessToken);
		parent.setCreateTime(now);
		parent.setMobile(mobile);
		parent.setLastLoginTime(now);
		parent.setPassword(password);
		parent.setImei(imei);
		
		int cnt = parentMapper.insert(parent);
		
		if( cnt == 0 ){
			return null;
		}
		
		return trans2ParentViewDTO(parent);
	}

	
	
	

}
