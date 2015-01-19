package eden.sun.childrenguard.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.Parent;
import eden.sun.childrenguard.server.service.IParentService;
import eden.sun.childrenguard.server.service.ISettingService;

@Service
public class SettingServiceImpl implements ISettingService{

	@Autowired
	private IParentService parentService;
	
	@Override
	public ViewDTO<Boolean> saveEmail(String accessToken, String email)
			throws ServiceException {
		if( accessToken == null || email == null ){
			throw new ServiceException("Parameter accessToken or email can not be null.");
		}
		
		Parent parent = parentService.getByAccessToken(accessToken);
		if( parent == null ){
			throw new ServiceException("Parent is not exists.");
		}
		
		parent.setEmail(email);
		parentService.update(parent);
		ViewDTO<Boolean> view = new ViewDTO<Boolean>();
		
		view.setData(true);
		return view;
	}
	
}
