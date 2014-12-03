package eden.sun.childrenguard.server.service;

import eden.sun.childrenguard.server.dto.PresetLockViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.PresetLock;

public interface IPresetLockService {

	ViewDTO<PresetLockViewDTO> loadPresetLockData(Integer childId)throws ServiceException;

	PresetLock getById(Integer presetLockId)throws ServiceException;
	
	PresetLock saveOrUpdate(PresetLock presetLock)throws ServiceException;
	
	PresetLock createIfNotExists(Integer presetLockId)throws ServiceException;

	ViewDTO<PresetLockViewDTO> retrievePresetLockData(String imei)throws ServiceException;
	
}
