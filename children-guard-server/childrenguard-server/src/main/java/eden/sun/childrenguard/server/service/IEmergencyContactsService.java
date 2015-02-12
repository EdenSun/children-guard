package eden.sun.childrenguard.server.service;

import java.util.List;

import eden.sun.childrenguard.server.dto.EmergencyContactViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.EmergencyContacts;

public interface IEmergencyContactsService {

	ViewDTO<List<EmergencyContactViewDTO>> listViewByChild(Integer childId)throws ServiceException;

	ViewDTO<EmergencyContactViewDTO> add(Integer childId, String name,
			String phone)throws ServiceException;

	//ViewDTO<Boolean> delete(Integer childId, String phone)throws ServiceException;

	EmergencyContacts getByPhone(String phone) throws ServiceException;
	
	List<EmergencyContacts> listByChild(Integer childId)throws ServiceException;

	ViewDTO<Boolean> deleteById(Integer emergencyContactId)throws ServiceException;
}
