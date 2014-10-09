package eden.sun.childrenguard.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dao.ChildMapper;
import eden.sun.childrenguard.server.dao.ChildOfParentsMapper;
import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.Child;
import eden.sun.childrenguard.server.model.ChildOfParents;
import eden.sun.childrenguard.server.service.IChildService;

@Service
public class ChildServiceImpl implements IChildService {
	@Inject
	private ChildMapper childMapper;
	@Inject
	private ChildOfParentsMapper childOfParentsMapper;

	@Override
	public ViewDTO<List<ChildViewDTO>> listAllByParentId(Integer parentId)
			throws ServiceException {
		ViewDTO<List<ChildViewDTO>> view = new ViewDTO<List<ChildViewDTO>>();
		
		List<ChildOfParents> childList = childOfParentsMapper.selectAllByParentId(parentId);
		List<ChildViewDTO> childViewDTOList = trans2ChildViewDTO(childList);
		
		view.setData(childViewDTOList);
		view.setMsg(ViewDTO.MSG_SUCCESS);
		return view;
	}

	private List<ChildViewDTO> trans2ChildViewDTO(List<ChildOfParents> childList) {
		if( childList == null ){
			return null;
		}
		
		List<ChildViewDTO> childViewDTOList = new ArrayList<ChildViewDTO>();
		ChildViewDTO dto = null;
		for(int i=0;i<childList.size();i++){
			ChildOfParents child = childList.get(i);
			dto = trans2ChildViewDTO(child);
			if( dto != null ){
				childViewDTOList.add(dto);
			}
		}
		return childViewDTOList;
	}

	private ChildViewDTO trans2ChildViewDTO(ChildOfParents child) {
		if( child == null ){
			return null;
		}
		
		ChildViewDTO dto = new ChildViewDTO();
		BeanUtils.copyProperties(child, dto);
		return dto;
	}

	@Override
	public ViewDTO<ChildViewDTO> add(String mobile, String nickname)
			throws ServiceException {
		ViewDTO<ChildViewDTO> view = new ViewDTO<ChildViewDTO>();
		
		Child child = new Child();
		child.setCreateTime(new Date());
		child.setMobile(mobile);
		child.setNickname(nickname);
		
		childMapper.insert(child);
		
		ChildViewDTO dto = trans2ChildViewDTO(child);
		view.setData(dto);
		view.setMsg(ViewDTO.MSG_SUCCESS);
		return view;
	}

	private ChildViewDTO trans2ChildViewDTO(Child child) {
		if( child == null ){
			return null;
		}
		ChildViewDTO dto = new ChildViewDTO();
		BeanUtils.copyProperties(child, dto);
		return dto;
	}
	
}
