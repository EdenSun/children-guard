package eden.sun.childrenguard.server.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dto.PresetLockListItemViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.PresetLock;
import eden.sun.childrenguard.server.service.IPresetLockAppService;
import eden.sun.childrenguard.server.service.IPresetLockService;
import eden.sun.childrenguard.server.service.IScheduleLockService;

@Service
public class ScheduleLockServiceImpl extends BaseServiceImpl implements IScheduleLockService{

	@Autowired
	private IPresetLockService presetLockService;
	
	@Autowired
	private IPresetLockAppService presetLockAppService;
	@Override
	public ViewDTO<List<PresetLockListItemViewDTO>> listScheduleLock(
			Integer childId) throws ServiceException {
		
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

	@Override
	public boolean isChildScheduleLock(Integer childId) throws ServiceException {
		if( childId == null ){
			throw new ServiceException("Parameter child id can not be null");
		}
		
		List<PresetLock> presetLockList = presetLockService.listByChildId(childId);
		
		if( presetLockList != null ){
			for(PresetLock presetLock:presetLockList){
				if( presetLock.getPresetOnOff() != null && presetLock.getPresetOnOff().booleanValue() == true ){
					// preset lock is on
					if( inLockTime(presetLock) ){
						// is in lock time period
						if( presetLock.getLockCallStatus() != null && presetLock.getLockCallStatus().booleanValue() == true ){
							return true;
						}
						
						boolean hasAppLocked = presetLockAppService.hasAppLocked(presetLock.getId());
						if( hasAppLocked ){
							return true;
						}
					}
				}
				
			}
		}
		
		return false;
	}

	private boolean inLockTime(PresetLock presetLock) {
		if( presetLock == null || presetLock.getStartTime() == null || presetLock.getEndTime() == null ){
			throw new ServiceException("Parameter presetLock , startTime , endTime can not be null.");
		}
		Calendar cal = Calendar.getInstance();
		
		Date startDateTime = presetLock.getStartTime();
		Date endDateTime = presetLock.getEndTime();
		Date now = new Date();
		cal.setTime(now);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		if( inLockDayOfWeek(presetLock,dayOfWeek) ){
			if( now.after(startDateTime) && now.before(endDateTime) ){
				return true;
			}
		}
		
		return false;
	}

	private boolean inLockDayOfWeek(PresetLock presetLock, int dayOfWeek) {
		if( presetLock != null ){
			if( dayOfWeek == Calendar.MONDAY && presetLock.getRepeatMonday() != null && presetLock.getRepeatMonday().booleanValue() == true  ){
				return true;
			}else if( dayOfWeek == Calendar.TUESDAY && presetLock.getRepeatTuesday() != null && presetLock.getRepeatTuesday().booleanValue() == true  ){
				return true;
			}else if( dayOfWeek == Calendar.WEDNESDAY && presetLock.getRepeatWednesday() != null && presetLock.getRepeatWednesday().booleanValue() == true  ){
				return true;
			}else if( dayOfWeek == Calendar.THURSDAY && presetLock.getRepeatThurday() != null && presetLock.getRepeatThurday().booleanValue() == true  ){
				return true;
			}else if( dayOfWeek == Calendar.FRIDAY && presetLock.getRepeatFriday() != null && presetLock.getRepeatFriday().booleanValue() == true  ){
				return true;
			}else if( dayOfWeek == Calendar.SATURDAY && presetLock.getRepeatSaturday() != null && presetLock.getRepeatSaturday().booleanValue() == true  ){
				return true;
			}else if( dayOfWeek == Calendar.SUNDAY && presetLock.getRepeatSunday() != null && presetLock.getRepeatSunday().booleanValue() == true  ){
				return true;
			}
		}
		return false;
	}

	
}
