package eden.sun.childrenguard.server.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dto.EmergencyContactViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.service.IEmergencyContactsService;

@Service
public class EmergencyContactsServiceImpl implements IEmergencyContactsService {

	@Override
	public ViewDTO<List<EmergencyContactViewDTO>> listByChild(Integer childId)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewDTO<EmergencyContactViewDTO> add(Integer childId, String name,
			String phone) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewDTO<Boolean> delete(Integer childId, String phone)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
