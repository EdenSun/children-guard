package eden.sun.childrenguard.server.service.impl;

import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dto.ChildLoginViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.service.IChildAuthService;

@Service
public class ChildAuthServiceImpl implements IChildAuthService {

	@Override
	public ViewDTO<ChildLoginViewDTO> login(String mobile)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
