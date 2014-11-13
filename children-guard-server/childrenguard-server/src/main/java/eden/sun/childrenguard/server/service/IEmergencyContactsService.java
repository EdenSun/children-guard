package eden.sun.childrenguard.server.service;

import java.util.List;

import eden.sun.childrenguard.server.dto.EmergencyContactViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;

public interface IEmergencyContactsService {

	ViewDTO<List<EmergencyContactViewDTO>> listByChild(Integer childId)throws ServiceException;

	ViewDTO<EmergencyContactViewDTO> add(Integer childId, String name,
			String phone)throws ServiceException;

	ViewDTO<Boolean> delete(Integer childId, String phone)throws ServiceException;

}
