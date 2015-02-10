package eden.sun.childrenguard.server.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dto.AppViewDTO;
import eden.sun.childrenguard.server.dto.PresetLockViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.PresetLock;
import eden.sun.childrenguard.server.service.IChildScheduleLockService;
import eden.sun.childrenguard.server.service.IPresetLockAppService;
import eden.sun.childrenguard.server.service.IPresetLockService;
import eden.sun.childrenguard.server.util.ScheduleLockHelper;

@Service
public class ChildScheduleLockServiceImpl implements IChildScheduleLockService {

	@Autowired
	private IPresetLockService presetLockService;
	
	@Autowired
	private IPresetLockAppService presetLockAppService;

	@Override
	public ViewDTO<List<PresetLockViewDTO>> listAllByImei(String imei)
			throws ServiceException {
		ViewDTO<List<PresetLockViewDTO>> view = new ViewDTO<List<PresetLockViewDTO>>();
		List<PresetLock> scheduleLockList = presetLockService.listByChildImei(imei);
		
		List<PresetLockViewDTO> viewList = trans2ScheduleLockDTOList(scheduleLockList);
		
		view.setData(viewList);
		return view;
	}
	
	
	private List<PresetLockViewDTO> trans2ScheduleLockDTOList(
			List<PresetLock> scheduleLockList) {
		if( scheduleLockList == null ){
			return null;
		}
		List<PresetLockViewDTO> viewList = new ArrayList<PresetLockViewDTO>();
		
		for(PresetLock presetLock: scheduleLockList){
			List<AppViewDTO> appList = presetLockAppService.listAppListByPresetLockId(presetLock.getId());
			
			PresetLockViewDTO presetLockViewDTO = trans2ScheduleLockViewDTO(presetLock,appList);
			
			if( presetLockViewDTO != null ){
				viewList.add(presetLockViewDTO);
			}
		}
		return viewList;
	}

	private PresetLockViewDTO trans2ScheduleLockViewDTO(PresetLock presetLock,
			List<AppViewDTO> appList) {
		if( presetLock == null ){
			return null;
		}
		
		PresetLockViewDTO dto = new PresetLockViewDTO();
		dto.setId(presetLock.getId());
		DateFormat df = new SimpleDateFormat("HH:mm");
		Date startTime = presetLock.getStartTime();
		if( startTime != null ){
			dto.setStartTime(startTime);
			dto.setStartTimeSummary(df.format(startTime));
		}
		Date endTime = presetLock.getEndTime();
		if( endTime != null ){
			dto.setEndTime(endTime);
			dto.setEndTimeSummary(df.format(endTime));
		}
		dto.setLockCallStatus(presetLock.getLockCallStatus());
		dto.setPresetOnOff(presetLock.getPresetOnOff());
		
		List<Boolean> repeatList = ScheduleLockHelper.getRepeatList(presetLock); 
		dto.setRepeat(repeatList);
		String repeatSummary = ScheduleLockHelper.getRepeatSummary(repeatList); 
		dto.setRepeatSummary(repeatSummary);
		
		dto.setAppList(appList);
		if( appList != null && appList.size() > 0 ){
			dto.setAppLockSummary( appList.size() + " applications is locked");
		}
		return dto;
	}
	
}
