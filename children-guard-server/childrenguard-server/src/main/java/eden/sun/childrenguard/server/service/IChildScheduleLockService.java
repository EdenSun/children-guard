package eden.sun.childrenguard.server.service;

import java.util.List;

import eden.sun.childrenguard.server.dto.PresetLockViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;

public interface IChildScheduleLockService {

	ViewDTO<List<PresetLockViewDTO>> listAllByImei(String imei)throws ServiceException;

}
