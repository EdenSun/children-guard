package eden.sun.childrenguard.server.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dto.LoginViewDTO;
import eden.sun.childrenguard.server.dto.ParentViewDTO;
import eden.sun.childrenguard.server.dto.RegisterViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.Parent;
import eden.sun.childrenguard.server.service.IAuthService;
import eden.sun.childrenguard.server.service.IParentService;

@Service
public class AuthServiceImpl implements IAuthService {
	@Inject 
	private IParentService parentService;
	
	@Override
	public ViewDTO<LoginViewDTO> login(String email, String password)
			throws ServiceException {
		ViewDTO<LoginViewDTO> view = new ViewDTO<LoginViewDTO>();
		boolean isSuccess = parentService.doLogin(email);

		if( !isSuccess ){
			view.setMsg(ViewDTO.MSG_ERROR);
			return view;
		}
		
		ParentViewDTO parentView = parentService.getViewByEmail(email);
		
		LoginViewDTO loginView = trans2LoginViewDTO(parentView);
		
		view.setData(loginView);
		return view;
	}

	@Override
	public ViewDTO<RegisterViewDTO> register(String firstName, String lastName,
			String email, String password) throws ServiceException {
		ViewDTO<RegisterViewDTO> view = new ViewDTO<RegisterViewDTO>();
		if( firstName == null || lastName == null || email == null || password == null ){
			view.setInfo("²ÎÊý´íÎó");
			view.setMsg(ViewDTO.MSG_ERROR);
			return view;
		}
		
		Parent parent = parentService.getByEmail(email);
		if( parent != null ){
			view.setInfo("Email has been registered.");
			view.setMsg(ViewDTO.MSG_ERROR);
			return view;
		}else{
			// do register
			ParentViewDTO parentView = parentService.save(firstName,lastName,email,password);
			RegisterViewDTO registerView = trans2RegisterViewDTO(parentView);
			
			view.setData(registerView);
			return view;
		}
		
	}


	private RegisterViewDTO trans2RegisterViewDTO(ParentViewDTO parentView) {
		if(parentView == null ){
			return null;
		}
		RegisterViewDTO registerView = new RegisterViewDTO();
		registerView.setAccessToken(parentView.getAccessToken());
		registerView.setEmail(parentView.getEmail());
		return registerView;
	}

	private LoginViewDTO trans2LoginViewDTO(ParentViewDTO parentView) {
		if(parentView == null ){
			return null;
		}
		LoginViewDTO loginView = new LoginViewDTO();
		loginView.setAccessToken(parentView.getAccessToken());
		loginView.setEmail(parentView.getEmail());
		return loginView;
	}

}
