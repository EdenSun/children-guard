package eden.sun.childrenguard.server.service;

import java.util.List;

import eden.sun.childrenguard.server.dto.AppViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.PresetLockApp;

public interface IPresetLockAppService {

	List<AppViewDTO> listAppListByPresetLockId(Integer presetLockId)throws ServiceException;

	List<PresetLockApp> listPresetLockApp(Integer presetLockId)throws ServiceException;

	void updatePresetLockApp(Integer presetId, List<Integer> appIdList)throws ServiceException;

	boolean hasAppLocked(Integer presetLockId)throws ServiceException;
	
}
