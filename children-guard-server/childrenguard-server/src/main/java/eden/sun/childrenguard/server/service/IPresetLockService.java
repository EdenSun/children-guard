package eden.sun.childrenguard.server.service;

import java.util.List;

import eden.sun.childrenguard.server.dto.PresetLockListItemViewDTO;
import eden.sun.childrenguard.server.dto.PresetLockViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.PresetLockParam;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.PresetLock;

public interface IPresetLockService {

	ViewDTO<PresetLockViewDTO> loadPresetLockData(Integer childId)throws ServiceException;

	PresetLock getById(Integer presetLockId)throws ServiceException;
	
	PresetLock saveOrUpdate(PresetLock presetLock)throws ServiceException;
	
	PresetLock createIfNotExists(Integer presetLockId)throws ServiceException;

	//ViewDTO<List<PresetLockViewDTO>> listAllByImei(String imei)throws ServiceException;
	
	ViewDTO<List<PresetLockListItemViewDTO>> listScheduleLock(Integer childId)throws ServiceException;

	ViewDTO<Boolean> batchDelete(Integer[] ids)throws ServiceException;

	ViewDTO<PresetLockViewDTO> loadPresetLockById(Integer presetLockId)throws ServiceException;

	ViewDTO<PresetLockViewDTO> delete(Integer childId, Integer presetLockId)throws ServiceException;

	ViewDTO<Boolean> applyPresetLock(Integer presetLockId,
			PresetLockParam applyPresetLockParam)throws ServiceException;

	ViewDTO<PresetLockViewDTO> newPresetLock(
			PresetLockParam applyPresetLockParam)throws ServiceException;

	ViewDTO<Boolean> switchPresetLock(Integer presetLockId, boolean isChecked)throws ServiceException;

	List<PresetLock> listByChildId(Integer childId)throws ServiceException;

	List<PresetLock> listByChildImei(String imei)throws ServiceException;
	
}
