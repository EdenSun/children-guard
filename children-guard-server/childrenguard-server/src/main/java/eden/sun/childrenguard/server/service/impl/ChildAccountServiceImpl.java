package eden.sun.childrenguard.server.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.service.IChildAccountService;
import eden.sun.childrenguard.server.service.IChildService;

@Service
public class ChildAccountServiceImpl implements IChildAccountService {
	@Inject
	private IChildService childService;
	
	@Override
	public ViewDTO<ChildViewDTO> isActivate(String imei)
			throws ServiceException {
		ViewDTO<ChildViewDTO> view = new ViewDTO<ChildViewDTO>();
		ChildViewDTO childViewDTO = childService.getViewByImei(imei);
		
		view.setData(childViewDTO);
		return view;
	}

}
