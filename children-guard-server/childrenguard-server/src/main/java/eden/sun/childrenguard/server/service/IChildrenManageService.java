package eden.sun.childrenguard.server.service;

import java.util.List;

import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.ChildAddParam;
import eden.sun.childrenguard.server.exception.ServiceException;

public interface IChildrenManageService {

	ViewDTO<List<ChildViewDTO>> listChildrenByParentAccessToken(
			String accessToken)throws ServiceException;

	ViewDTO<ChildViewDTO> addChild(ChildAddParam param)throws ServiceException;

	ViewDTO<ChildViewDTO> deleteChild(Integer childId)throws ServiceException;

}
