package eden.sun.childrenguard.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dao.generated.EmergencyContactsMapper;
import eden.sun.childrenguard.server.dto.EmergencyContactViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.Child;
import eden.sun.childrenguard.server.model.generated.EmergencyContacts;
import eden.sun.childrenguard.server.model.generated.EmergencyContactsExample;
import eden.sun.childrenguard.server.model.generated.EmergencyContactsExample.Criteria;
import eden.sun.childrenguard.server.service.IChildService;
import eden.sun.childrenguard.server.service.IEmergencyContactsService;

@Service
public class EmergencyContactsServiceImpl implements IEmergencyContactsService {
	@Autowired
	private IChildService childService;
	@Autowired
	private EmergencyContactsMapper emergencyContactsMapper;
	
	@Override
	public ViewDTO<List<EmergencyContactViewDTO>> listViewByChild(Integer childId)
			throws ServiceException {
		if( childId == null ){
			throw new ServiceException("Parameter childId can not be null.");
		}
		
		Child child = childService.getById(childId);
		if( child == null ){
			throw new ServiceException("Child is not exists.");
		}
		ViewDTO<List<EmergencyContactViewDTO>> view = new ViewDTO<List<EmergencyContactViewDTO>>();
		List<EmergencyContacts> ecList = this.listByChild(childId);
		List<EmergencyContactViewDTO> ecViewList = trans2EmergencyContactViewDTOList(ecList);
		
		view.setData(ecViewList);
		return view;
	}

	private List<EmergencyContactViewDTO> trans2EmergencyContactViewDTOList(
			List<EmergencyContacts> ecList) {
		if( ecList == null ){
			return null;
		}
		List<EmergencyContactViewDTO> viewList = new ArrayList<EmergencyContactViewDTO>();
		EmergencyContactViewDTO view = null;
		for(EmergencyContacts contact: ecList ){
			view = trans2EmergencyContactViewDTO(contact);
			if( view != null ){
				viewList.add(view);
			}
		}
		return viewList;
	}

	private EmergencyContactViewDTO trans2EmergencyContactViewDTO(
			EmergencyContacts contact) {
		if( contact == null ){
			return null;
		}
		EmergencyContactViewDTO contactView = new EmergencyContactViewDTO();
		BeanUtils.copyProperties(contact, contactView);
		return contactView;
	}

	@Override
	public ViewDTO<EmergencyContactViewDTO> add(Integer childId, String name,
			String phone) throws ServiceException {
		if( childId == null || name == null || phone == null ){
			throw new ServiceException("Parameter childId or name or phone can not be null.");
		}
		
		ViewDTO<EmergencyContactViewDTO> view = new ViewDTO<EmergencyContactViewDTO>();
		//do not insert if exists same phone
		EmergencyContacts contact = this.getByPhone(phone);
		if( contact != null ){
			view.setMsg(ViewDTO.MSG_ERROR);
			view.setInfo("Can not add same phone.");
			return view;
		}
		
		contact = new EmergencyContacts();
		contact.setChildId(childId);
		contact.setName(name);
		contact.setPhone(phone);
		contact.setCreateTime(new Date());
		emergencyContactsMapper.insert(contact);
		
		view.setData(this.trans2EmergencyContactViewDTO(contact));
		return view;
	}


	/*@Override
	public ViewDTO<Boolean> delete(Integer childId, String phone)
			throws ServiceException {
		if( childId == null ||  phone == null ){
			throw new ServiceException("Parameter childId or phone can not be null.");
		}
		
		ViewDTO<Boolean> view = new ViewDTO<Boolean>();
		EmergencyContacts contact = getByPhone(phone);
		if( contact != null ){
			int cnt = emergencyContactsMapper.deleteByPrimaryKey(contact.getId());
			if( cnt == 1 ){
				view.setData(true);
				return view;
			}
		}
		
		view.setData(false);
		return view;
	}*/

	@Override
	public EmergencyContacts getByPhone(String phone) throws ServiceException {
		EmergencyContactsExample example = new EmergencyContactsExample();
		Criteria criteria = example.createCriteria();
		criteria.andPhoneEqualTo(phone);
		
		List<EmergencyContacts> contactsList = emergencyContactsMapper.selectByExample(example);
		if( contactsList != null && contactsList.size() > 0 ){
			return contactsList.get(0);
		}
		
		return null;
	}

	@Override
	public List<EmergencyContacts> listByChild(Integer childId)
			throws ServiceException {
		EmergencyContactsExample example = new EmergencyContactsExample();
		Criteria criteria = example.createCriteria();
		criteria.andChildIdEqualTo(childId);
		
		List<EmergencyContacts> contactsList = emergencyContactsMapper.selectByExample(example);
		
		return contactsList;
	}

	@Override
	public ViewDTO<Boolean> deleteById(Integer emergencyContactId)
			throws ServiceException {
		if( emergencyContactId == null ){
			throw new ServiceException("Parameter emergencyContactId can not be null.");
		}
		
		emergencyContactsMapper.deleteByPrimaryKey(emergencyContactId);
		ViewDTO<Boolean> view = new ViewDTO<Boolean>();
		view.setData(true);
		return view;
	}
	
	
}
