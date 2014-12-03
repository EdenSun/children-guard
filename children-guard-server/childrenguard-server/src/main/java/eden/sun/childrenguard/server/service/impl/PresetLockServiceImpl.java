package eden.sun.childrenguard.server.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dao.generated.PresetLockMapper;
import eden.sun.childrenguard.server.dto.AppViewDTO;
import eden.sun.childrenguard.server.dto.PresetLockViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.Child;
import eden.sun.childrenguard.server.model.generated.PresetLock;
import eden.sun.childrenguard.server.service.IChildService;
import eden.sun.childrenguard.server.service.IPresetLockAppService;
import eden.sun.childrenguard.server.service.IPresetLockService;
import eden.sun.childrenguard.server.util.DataTypeUtil;

@Service
public class PresetLockServiceImpl implements IPresetLockService {
	@Autowired
	private PresetLockMapper presetLockMapper;
	@Autowired
	private IPresetLockAppService presetLockAppService;
	
	@Autowired
	private IChildService childService;
	
	@Override
	public ViewDTO<PresetLockViewDTO> loadPresetLockData(Integer childId)
			throws ServiceException {
		if(childId == null ){
			throw new ServiceException("Parameter child id can not be null.");
		}
		Child child = childService.getById(childId);
		if( child == null ){
			throw new ServiceException("Child is not exists.");
		}
		
		Integer presetLockId = childId;
		PresetLock presetLock = createIfNotExists(presetLockId);
		
		ViewDTO<PresetLockViewDTO>  view = new ViewDTO<PresetLockViewDTO>();
		if( presetLock != null ){
			List<AppViewDTO> appList = presetLockAppService.listAppListByPresetLockId(presetLock.getId());
			
			PresetLockViewDTO presetLockViewDTO = trans2PresetLockViewDTO(presetLock,appList);
			view.setData(presetLockViewDTO);
			
			return view;
		}
		
		view.setMsg(ViewDTO.MSG_ERROR);
		view.setInfo("Load preset lock data error.");
		return view;
	}

	private PresetLockViewDTO trans2PresetLockViewDTO(PresetLock presetLock,
			List<AppViewDTO> appList) {
		if( presetLock == null ){
			return null;
		}
		
		PresetLockViewDTO dto = new PresetLockViewDTO();
		DateFormat df = new SimpleDateFormat("HH:ss");
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
		
		List<Boolean> repeatList = getRepeatList(presetLock); 
		dto.setRepeat(repeatList);
		String repeatSummary = getRepeatSummary(repeatList); 
		dto.setRepeatSummary(repeatSummary);
		
		dto.setAppList(appList);
		return dto;
	}

	private String getRepeatSummary(List<Boolean> repeatList)throws ServiceException {
		if( repeatList == null || repeatList.size() == 0 ){
			return "";
		}
		if( repeatList.size() != 7 ){
			throw new ServiceException("weekday data error.");
		}
		
		boolean monday = repeatList.get(0);
		boolean tuesday = repeatList.get(1);
		boolean wednesday = repeatList.get(2);
		boolean thurday = repeatList.get(3);
		boolean friday = repeatList.get(4);
		boolean saturday = repeatList.get(5);
		boolean sunday = repeatList.get(6);
		
		StringBuffer sb = new StringBuffer();
		if( monday ){
			sb.append("Mon.").append(" ");
		}
		
		if( tuesday ){
			sb.append("Tues.").append(" ");
		}
		
		if( wednesday ){
			sb.append("Wed.").append(" ");
		}
		
		if( thurday ){
			sb.append("Thur.").append(" ");
		}
		
		if( friday ){
			sb.append("Fri.").append(" ");
		}
		
		if( saturday ){
			sb.append("Sat.").append(" ");
		}
		
		if( sunday ){
			sb.append("Sun.").append(" ");
		}
		return sb.toString().trim();
	}

	private List<Boolean> getRepeatList(PresetLock presetLock)throws ServiceException {
		if( presetLock == null){
			return null;
		}
		
		List<Boolean> repeatList = new ArrayList<Boolean>();
		boolean monday = DataTypeUtil.getBooleanValue(presetLock.getRepeatMonday());
		boolean tuesday = DataTypeUtil.getBooleanValue(presetLock.getRepeatTuesday());
		boolean wednesday = DataTypeUtil.getBooleanValue(presetLock.getRepeatWednesday());
		boolean thurday = DataTypeUtil.getBooleanValue(presetLock.getRepeatThurday());
		boolean friday = DataTypeUtil.getBooleanValue(presetLock.getRepeatFriday());
		boolean saturday = DataTypeUtil.getBooleanValue(presetLock.getRepeatSaturday());
		boolean sunday = DataTypeUtil.getBooleanValue(presetLock.getRepeatSunday());

		repeatList.add(monday);
		repeatList.add(tuesday);
		repeatList.add(wednesday);
		repeatList.add(thurday);
		repeatList.add(friday);
		repeatList.add(saturday);
		repeatList.add(sunday);
		
		return repeatList;
	}

	@Override
	public PresetLock createIfNotExists(Integer presetLockId)throws ServiceException {
		if( presetLockId == null ){
			return null;
		}
		PresetLock presetLock = this.getById(presetLockId);
		
		if( presetLock != null ){
			return presetLock;
		}else{
			// not exists, create one
			presetLock = initPresetLock(presetLockId);
			
			presetLock = saveOrUpdate(presetLock);
			return presetLock;
		}
	}

	private PresetLock initPresetLock(Integer presetLockId) {
		PresetLock presetLock = new PresetLock();
		presetLock.setId(presetLockId);
		presetLock.setPresetOnOff(false);
		presetLock.setStartTime(null);
		presetLock.setEndTime(null);
		presetLock.setLockCallStatus(false);
		presetLock.setRepeatFriday(false);
		presetLock.setRepeatMonday(false);
		presetLock.setRepeatSaturday(false);
		presetLock.setRepeatSunday(false);
		presetLock.setRepeatThurday(false);
		presetLock.setRepeatTuesday(false);
		presetLock.setRepeatWednesday(false);
		return presetLock;
	}

	@Override
	public PresetLock getById(Integer presetLockId) throws ServiceException {
		return presetLockMapper.selectByPrimaryKey(presetLockId);
	}

	@Override
	public PresetLock saveOrUpdate(PresetLock presetLock)
			throws ServiceException {
		if( presetLock == null || presetLock.getId() == null ){
			throw new ServiceException("Parameter presetLock or presetLock id can not be null.");
		}
		
		PresetLock existsPresetLock = getById(presetLock.getId());
		if( existsPresetLock == null ){
			//create one
			presetLockMapper.insert(presetLock);
		}else{
			//update exists one
			presetLockMapper.updateByPrimaryKeySelective(presetLock);
		}
		
		return getById(presetLock.getId());
	}

	@Override
	public ViewDTO<PresetLockViewDTO> retrievePresetLockData(String imei)
			throws ServiceException {
		if(imei == null ){
			throw new ServiceException("Parameter imei can not be null.");
		}
		Child child = childService.getChildByImei(imei);
		if( child == null ){
			throw new ServiceException("Child is not exists.");
		}
		
		return this.loadPresetLockData(child.getId());
	}

	
	/*private PresetLock getById(Integer presetLockId) {
		if( presetLockId == null ){
			throw new ServiceException("Parameter can not be null.");
		}
		PresetLockExample example = new PresetLockExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(presetLockId);
		
		
		List<PresetLock> presetLockList = presetLockMapper.selectByExample(example);
		
		if( presetLockList != null && presetLockList.size()> 0){
			return presetLockList.get(0);
		}
		
		return null;
	}*/
	
	
}
