package eden.sun.childrenguard.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dto.IsFirstLoginViewDTO;
import eden.sun.childrenguard.server.dto.LoginViewDTO;
import eden.sun.childrenguard.server.dto.ParentViewDTO;
import eden.sun.childrenguard.server.dto.RegisterViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.Parent;
import eden.sun.childrenguard.server.service.IAuthService;
import eden.sun.childrenguard.server.service.IJPushService;
import eden.sun.childrenguard.server.service.IParentService;
import eden.sun.childrenguard.server.util.Constants;
import eden.sun.childrenguard.server.util.NumberUtil;

@Service
public class AuthServiceImpl implements IAuthService {
	private Logger logger = Logger.getLogger(AuthServiceImpl.class);
	@Autowired 
	private IParentService parentService;
	
	@Autowired
	private IJPushService jpushService;
	
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
	public ViewDTO<RegisterViewDTO> register(String imei,String firstName, String lastName,
			String email, String password) throws ServiceException {
		ViewDTO<RegisterViewDTO> view = new ViewDTO<RegisterViewDTO>();
		if( imei == null || firstName == null || lastName == null || email == null || password == null ){
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
			ParentViewDTO parentView = parentService.save(imei,firstName,lastName,email,password);
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
			//send code to app
			logger.info("Send reset code to " + email);
			List<Parent> toList = new ArrayList<Parent>();
			toList.add(parent);
			jpushService.pushNotificationToParent(toList, "Reset Code", "Reset code is " + resetCode );
			
			view.setData("Reset code have been sent to your app.");
			return view;
		}else{
			view.setInfo("Server error,please try later.");
			view.setMsg(ViewDTO.MSG_ERROR);
			return view;
		}
	}

	@Override
	public ViewDTO<IsFirstLoginViewDTO> isFirstLogin(String email, String password)
			throws ServiceException {
		ViewDTO<IsFirstLoginViewDTO> view = new ViewDTO<IsFirstLoginViewDTO>();
		ParentViewDTO parentView = parentService.getViewByEmailAndPassword(email, password);
		
		if( parentView == null ){
			view.setInfo("Email or password is incorrect.");
			view.setMsg(ViewDTO.MSG_ERROR);
			return view;
		}
		
		IsFirstLoginViewDTO isFirstLoginViewDTO = new IsFirstLoginViewDTO();
		isFirstLoginViewDTO.setEmail(email);
		if( parentView.getLastLoginTime() == null ){
			logger.info("First login.");
			isFirstLoginViewDTO.setFirstLogin(true);
			isFirstLoginViewDTO.setLegalInfo(Constants.LEGAL_INFO);
		}else{
			logger.info("Not first login.");
			isFirstLoginViewDTO.setFirstLogin(false);
		}
		
		view.setData(isFirstLoginViewDTO);
		return view;
	}

	@Override
	public ViewDTO<String> changePassword(String imei, String resetCode,
			String password) throws ServiceException {
		ViewDTO<String> view = new ViewDTO<String>();
		if( imei == null || resetCode == null || password == null ){
			view.setInfo("Server parameters error");
			view.setMsg(ViewDTO.MSG_ERROR);
			return view;
		}
		Parent parent = parentService.getByImei(imei);
		
		if( parent != null ){
			String dbResetCode = parent.getResetCode();
			if( dbResetCode != null ){
				if( !dbResetCode.equals(resetCode) ){
					view.setInfo("Reset code is incorrect, change password failure.");
					view.setMsg(ViewDTO.MSG_ERROR);
					return view;
				}else{
					parent.setPassword(password);
					boolean isSuccess = parentService.update(parent);
					
					if( isSuccess ){
						view.setInfo("Change password success.");
						view.setMsg(ViewDTO.MSG_SUCCESS);
						return view;
					}
				}
			}
		}
		
		view.setInfo("Unknow error, change password failure.");
		view.setMsg(ViewDTO.MSG_ERROR);
		return view;
	}
	
}
