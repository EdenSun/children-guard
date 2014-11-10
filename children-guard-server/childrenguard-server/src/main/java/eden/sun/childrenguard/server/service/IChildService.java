package eden.sun.childrenguard.server.service;

import java.util.List;

import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.ChildAddParam;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.Child;
import eden.sun.childrenguard.server.model.generated.Parent;

public interface IChildService {

	ViewDTO<List<ChildViewDTO>> listAllViewByParentId(Integer parentId)throws ServiceException;

	ViewDTO<ChildViewDTO> add(String mobile, String nickname)throws ServiceException;

	ChildViewDTO add(ChildAddParam param)throws ServiceException;

	ChildViewDTO getChildViewByMobile(String mobile)throws ServiceException;

	ChildViewDTO deleteChild(Integer childId)throws ServiceException;

	ChildViewDTO getViewById(Integer childId)throws ServiceException;

	Child getChildByAccessToken(String childAccessToken)throws ServiceException;
	
	Child getById(Integer childId)throws ServiceException;

	void update(Child child)throws ServiceException;

	ChildViewDTO getViewByImei(String imei)throws ServiceException;

	Child getChildByMobile(String childMobile)throws ServiceException;

	Child getChildByImei(String imei)throws ServiceException;

	ChildViewDTO getChildViewByImei(String imei)throws ServiceException;

	List<Parent> getParentsByChildId(Integer childId)throws ServiceException;

	ViewDTO<Boolean> saveOrUpdateRegistionId(String imei, String registionId)throws ServiceException;

}
