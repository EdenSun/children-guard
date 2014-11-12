package eden.sun.childrenguard.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.ChildAddParam;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.Parent;
import eden.sun.childrenguard.server.service.IChildOfParentsService;
import eden.sun.childrenguard.server.service.IChildService;
import eden.sun.childrenguard.server.service.IChildSettingService;
import eden.sun.childrenguard.server.service.IChildrenManageService;
import eden.sun.childrenguard.server.service.IParentChildService;
import eden.sun.childrenguard.server.service.IParentService;

@Service
public class ChildrenManageServiceImpl implements IChildrenManageService{
	@Autowired
	private IParentService parentService;
	@Autowired
	private IChildService childService;
	@Autowired
	private IChildOfParentsService childOfParentsService;
	@Autowired
	private IParentChildService parentChildService;
	
	@Autowired
	private IChildSettingService childSettingService;
	
	@Override
	public ViewDTO<List<ChildViewDTO>> listChildrenByParentAccessToken(
			String accessToken) throws ServiceException {
		ViewDTO<List<ChildViewDTO>>	view = new ViewDTO<List<ChildViewDTO>>();
		
		Parent parent = parentService.getByAccessToken(accessToken);
		
		if( parent == null ){
			view.setMsg(ViewDTO.MSG_ERROR);
			view.setInfo("Login failure,please login again.");
			return view;
		}
		
		Integer parentId = parent.getId();
		List<ChildViewDTO> childList = childOfParentsService.listChildrenViewByParentId(parentId);
		
		view.setData(childList);
		return view;
	}

	@Override
	public ViewDTO<ChildViewDTO> addChild(ChildAddParam param)
			throws ServiceException {
		ViewDTO<ChildViewDTO> view = new ViewDTO<ChildViewDTO>();
		
		/* check whether child mobile exists 
		 * if exists, add relationship only
		 * if not exists, add child ,and add relationship
		 */
		ChildViewDTO childViewDTO = childService.getChildViewByMobile(param.getMobile());
		if( childViewDTO != null ){
			view.setMsg(ViewDTO.MSG_ERROR);
			view.setInfo("Add Person fail,same mobile have been added.");
		}else{
			
			// child is not exists , add child
			childViewDTO = childService.add(param);
			
			if( childViewDTO == null ){
				view.setMsg(ViewDTO.MSG_ERROR);
				view.setInfo("Add person failure.Please try later.");
				return view;
			}
			
			/* cond: add child success
			 * then add child setting
			 */
			childSettingService.addIfNotExists(childViewDTO.getId());
		}
		
		//add relationship
		Parent parent = parentService.getByAccessToken(param.getParentAccessToken());
		if( parent == null ){
			throw new ServiceException("Data error,parent is not exists.");
		}
		
		parentChildService.addRelationship(parent.getId(),childViewDTO.getId(),param.getRelationshipId());
		
		view.setData(childViewDTO);
		return view;
	}

	@Override
	public ViewDTO<ChildViewDTO> deleteChild(Integer childId)
			throws ServiceException {
		if( childId == null ){
			throw new ServiceException("Parameter childId can not be null.");
		}
		
		ViewDTO<ChildViewDTO> view = new ViewDTO<ChildViewDTO>();
		
		ChildViewDTO viewDTO = childService.deleteChild(childId);

		view.setData(viewDTO);
		return view;
	}
	
	
}
