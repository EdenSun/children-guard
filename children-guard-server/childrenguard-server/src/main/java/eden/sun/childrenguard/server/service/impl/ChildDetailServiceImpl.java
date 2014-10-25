package eden.sun.childrenguard.server.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dto.AppViewDTO;
import eden.sun.childrenguard.server.dto.ChildBasicInfoViewDTO;
import eden.sun.childrenguard.server.dto.ChildExtraInfoViewDTO;
import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.SyncAppSettingParam;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.ChildExtraInfo;
import eden.sun.childrenguard.server.service.IAppService;
import eden.sun.childrenguard.server.service.IChildDetailService;
import eden.sun.childrenguard.server.service.IChildExtraInfoService;
import eden.sun.childrenguard.server.service.IChildService;

@Service
public class ChildDetailServiceImpl implements IChildDetailService {
	@Inject
	private IChildExtraInfoService childExtraInfoService;
	@Inject
	private IChildService childService;
	@Inject
	private IAppService appService;
	
	@Override
	public ViewDTO<List<AppViewDTO>> listChildApp(Integer childId)
			throws ServiceException {
		if( childId == null ){
			throw new ServiceException("Parameter childId can not be null.");
		}
		ViewDTO<List<AppViewDTO>> view = new ViewDTO<List<AppViewDTO>>();
		
		List<AppViewDTO> appViewDTOList = appService.listViewByChildId(childId);
		
		view.setData(appViewDTOList);
		return view;
	}

	@Override
	public ViewDTO<Boolean> syncChildApp(SyncAppSettingParam param)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewDTO<ChildBasicInfoViewDTO> getChildBasicInfo(Integer childId)
			throws ServiceException {
		ViewDTO<ChildBasicInfoViewDTO> view = new ViewDTO<ChildBasicInfoViewDTO>();
		
		ChildExtraInfo childExtraInfo = childExtraInfoService.getById(childId);
		
		ChildBasicInfoViewDTO childBasicInfoViewDTO = trans2ChildBasicInfoViewDTO(childId,childExtraInfo);
		
		view.setData(childBasicInfoViewDTO);
		return view;
	}

	private ChildBasicInfoViewDTO trans2ChildBasicInfoViewDTO(Integer childId,
			ChildExtraInfo childExtraInfo)throws ServiceException{
		ChildBasicInfoViewDTO childBasicInfoViewDTO = new ChildBasicInfoViewDTO();
		ChildViewDTO childViewDTO = childService.getViewById(childId);
		if( childViewDTO == null ){
			throw new ServiceException("Child is not exists");
		}
		
		childBasicInfoViewDTO.setChild(childViewDTO);
		
		ChildExtraInfoViewDTO childExtraInfoViewDTO = null;
		if( childExtraInfo != null ){
			childExtraInfoViewDTO = trans2ChildExtraInfoViewDTO(childExtraInfo);
		}
		
		childBasicInfoViewDTO.setExtraInfo(childExtraInfoViewDTO);
		return childBasicInfoViewDTO;
	}

	private ChildExtraInfoViewDTO trans2ChildExtraInfoViewDTO(
			ChildExtraInfo childExtraInfo) {
		ChildExtraInfoViewDTO view = new ChildExtraInfoViewDTO();
		BeanUtils.copyProperties(childExtraInfo, view);
		
		//FIXME: not implements
		view.setOnlineStatus("");
		view.setLocation("");
		
		return view;
	}

}
