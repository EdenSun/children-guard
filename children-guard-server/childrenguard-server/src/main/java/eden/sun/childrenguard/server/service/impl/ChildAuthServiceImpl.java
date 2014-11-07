package eden.sun.childrenguard.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.Child;
import eden.sun.childrenguard.server.service.IChildAuthService;
import eden.sun.childrenguard.server.service.IChildService;

@Service
public class ChildAuthServiceImpl implements IChildAuthService {
	@Autowired
	private IChildService childService;
	
	@Override
	public ViewDTO<ChildViewDTO> login(String imei)
			throws ServiceException {
		ViewDTO<ChildViewDTO> view = new ViewDTO<ChildViewDTO>();
		try {
			if( imei == null ){
				throw new ServiceException("Parameter imei can not be null.");
			}
			
			ChildViewDTO childView = childService.getChildViewByImei(imei);
			if( childView == null ){
				view.setInfo("Child have not been activated.");
				view.setMsg(ViewDTO.MSG_ERROR);
				return view;
			}
			
			view.setData(childView);
			return view;
		} catch (Exception e) {
			view.setData(null);
			view.setInfo(e.getMessage());
			view.setMsg(ViewDTO.MSG_ERROR);
			return view;
		}
	}

}
