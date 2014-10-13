package eden.sun.childrenguard.server.service.impl;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.cometd.PasswordResetService;
import eden.sun.childrenguard.server.dto.LoginViewDTO;
import eden.sun.childrenguard.server.dto.ParentViewDTO;
import eden.sun.childrenguard.server.dto.RegisterViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.Parent;
import eden.sun.childrenguard.server.service.IAuthService;
import eden.sun.childrenguard.server.service.IParentService;
import eden.sun.childrenguard.server.util.Constants;
import eden.sun.childrenguard.server.util.NumberUtil;

@Service
public class AuthServiceImpl implements IAuthService {
	private Logger logger = Logger.getLogger(AuthServiceImpl.class);
	@Inject 
	private IParentService parentService;
	
	@Override
	public ViewDTO<LoginViewDTO> login(String email, String password)
			throws ServiceException {
		ViewDTO<LoginViewDTO> view = new ViewDTO<LoginViewDTO>();
		boolean isSuccess = parentService.doLogin(email,password);

		if( !isSuccess ){
			view.setInfo("Email or password is incorrect.");
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
			view.setInfo("Server parameters error");
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

	@Override
	public ViewDTO<String> resetPasswordByMail(String email)
			throws ServiceException {
		ViewDTO<String> view = new ViewDTO<String>();
		if( email == null ){
			view.setInfo("Server parameters error");
			view.setMsg(ViewDTO.MSG_ERROR);
			return view;
		}
		
		Parent parent = parentService.getByEmail(email);
		if( parent == null ){
			view.setInfo("Sorry,email is not exists.");
			view.setMsg(ViewDTO.MSG_ERROR);
			return view;
		}
		
		/* email exists
		 * generate reset code and send code to user's email 
		 */
		String resetCode = NumberUtil.getRandomNumberByCount(Constants.RESET_CODE_N);
		logger.info("Generating reset code...... reset code: " + resetCode);
		parent.setResetCode(resetCode);
		boolean isUpdateSuccess = parentService.update(parent);
		
		if( isUpdateSuccess ){
			//TODO: send email
			//...... send email code here
			logger.info("Send reset password mail to " + email);
			
			//.......................
			
			view.setData("Reset code have been sent to your email.");
			return view;
		}else{
			view.setInfo("Server error,please try later.");
			view.setMsg(ViewDTO.MSG_ERROR);
			return view;
		}
	}
	
}
