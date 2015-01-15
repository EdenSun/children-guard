package eden.sun.childrenguard.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dto.PresetLockListItemViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.service.IPresetLockService;
import eden.sun.childrenguard.server.service.IScheduleLockService;

@Service
public class ScheduleLockServiceImpl extends BaseServiceImpl implements IScheduleLockService{

	@Autowired
	private IPresetLockService presetLockService;
	
	@Override
	public ViewDTO<List<PresetLockListItemViewDTO>> listScheduleLock(
			Integer childId) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewDTO<Boolean> batchDelete(Integer[] ids) throws ServiceException {
		if( ids == null ){
			throw new ServiceException("Parameter ids can not be null.");
		}
		
		for(Integer id:ids){
			
		}
		return null;
	}

}
