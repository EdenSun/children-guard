package eden.sun.childrenguard.server.service;

import java.util.List;

import eden.sun.childrenguard.server.dto.AppViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.PresetLockApp;

public interface IPresetLockAppService {

	List<AppViewDTO> listAppListByPresetLockId(Integer presetLockId)throws ServiceException;

	List<PresetLockApp> listPresetLock(Integer presetLockId)throws ServiceException;
	
}
