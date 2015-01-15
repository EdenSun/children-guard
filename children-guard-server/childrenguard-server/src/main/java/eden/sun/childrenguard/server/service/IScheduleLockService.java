package eden.sun.childrenguard.server.service;

import java.util.List;

import eden.sun.childrenguard.server.dto.PresetLockListItemViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;

public interface IScheduleLockService {

	ViewDTO<List<PresetLockListItemViewDTO>> listScheduleLock(Integer childId)throws ServiceException;

	ViewDTO<Boolean> batchDelete(Integer[] ids)throws ServiceException;

}
