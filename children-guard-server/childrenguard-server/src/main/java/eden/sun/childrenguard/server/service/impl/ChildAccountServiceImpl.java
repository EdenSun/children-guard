package eden.sun.childrenguard.server.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.Child;
import eden.sun.childrenguard.server.model.generated.Parent;
import eden.sun.childrenguard.server.service.IChildAccountService;
import eden.sun.childrenguard.server.service.IChildService;
import eden.sun.childrenguard.server.service.IParentChildService;
import eden.sun.childrenguard.server.service.IParentService;

@Service
public class ChildAccountServiceImpl implements IChildAccountService {
	@Autowired
	private IChildService childService;
	
	@Autowired
	private IParentService parentService;
	
	@Autowired
	private IParentChildService parentChildService;
	
	@Override
	public ViewDTO<ChildViewDTO> isActivate(String imei)
			throws ServiceException {
		ViewDTO<ChildViewDTO> view = new ViewDTO<ChildViewDTO>();
		ChildViewDTO childViewDTO = childService.getViewByImei(imei);
		
		view.setData(childViewDTO);
		return view;
	}

	@Override
	public ViewDTO<Boolean> doActivate(String parentEmail,
			String childMobile, String imei) throws ServiceException {
		if( parentEmail == null || childMobile == null || imei == null ){
			throw new ServiceException("Parameter parentAccount or childMobile or imei can not be null.");
		}
		ViewDTO<Boolean> view = new ViewDTO<Boolean>();
		
		Child child = childService.getChildByMobile(childMobile);
		if( child == null ){
			view.setData(false);
			view.setMsg(ViewDTO.MSG_ERROR);
			view.setInfo("Person is not exists,please add person first.");
			return view;
		}
		
		Parent parent = parentService.getByEmail(parentEmail);
		if( parent == null ){
			view.setData(false);
			view.setMsg(ViewDTO.MSG_ERROR);
			view.setInfo("Guardian have not register,please register first.");
			return view;
		}
		
		boolean isBelong = parentChildService.isChildBelongTo(child.getId(),parent.getId());
		if( isBelong == false ){
			view.setData(false);
			view.setMsg(ViewDTO.MSG_ERROR);
			view.setInfo("Can not activate,please add person first.");
			return view;
		}
		
		child.setImei(imei);
		child.setActivateTime(new Date());
		childService.update(child);
		
		view.setData(true);
		view.setInfo("Activate success!");
		return view;
	}
	
	
}
