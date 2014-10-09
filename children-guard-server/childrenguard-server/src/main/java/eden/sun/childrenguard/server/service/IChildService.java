package eden.sun.childrenguard.server.service;

import java.util.List;

import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;

public interface IChildService {

	ViewDTO<List<ChildViewDTO>> listAllByParentId(Integer parentId)throws ServiceException;

	ViewDTO<ChildViewDTO> add(String mobile, String nickname)throws ServiceException;

}
