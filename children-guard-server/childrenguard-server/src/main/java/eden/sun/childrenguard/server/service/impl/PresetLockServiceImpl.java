package eden.sun.childrenguard.server.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dao.generated.PresetLockMapper;
import eden.sun.childrenguard.server.dto.AppViewDTO;
import eden.sun.childrenguard.server.dto.PresetLockListItemViewDTO;
import eden.sun.childrenguard.server.dto.PresetLockViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.PresetLockParam;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.Child;
import eden.sun.childrenguard.server.model.generated.PresetLock;
import eden.sun.childrenguard.server.model.generated.PresetLockExample;
import eden.sun.childrenguard.server.model.generated.PresetLockExample.Criteria;
import eden.sun.childrenguard.server.service.IChildService;
import eden.sun.childrenguard.server.service.IJPushService;
import eden.sun.childrenguard.server.service.IPresetLockAppService;
import eden.sun.childrenguard.server.service.IPresetLockService;
import eden.sun.childrenguard.server.util.DataTypeUtil;
import eden.sun.childrenguard.server.util.PushConstants;

@Service
public class PresetLockServiceImpl extends BaseServiceImpl implements IPresetLockService {
	@Autowired
	private PresetLockMapper presetLockMapper;
	@Autowired
	private IPresetLockAppService presetLockAppService;
	
	@Autowired
	private IChildService childService;
	
	@Autowired
	private IJPushService pushService;
	
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
		
		List<Boolean> repeatList = getRepeatList(presetLock); 
		dto.setRepeat(repeatList);
		String repeatSummary = getRepeatSummary(repeatList); 
		dto.setRepeatSummary(repeatSummary);
		
		dto.setAppList(appList);
		if( appList != null && appList.size() > 0 ){
			dto.setAppLockSummary( appList.size() + " applications is locked");
		}
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
		if( presetLock == null ){
			throw new ServiceException("Parameter presetLock or presetLock id can not be null.");
		}
		
		if( presetLock.getId() == null ){
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
	
	
	@Override
	public ViewDTO<List<PresetLockListItemViewDTO>> listScheduleLock(
			Integer childId) throws ServiceException {
		if( childId == null ){
			throw new ServiceException("Parameter childId can not be null.");
		}
		
		List<PresetLock> presetLockList = listByChildId(childId);
		List<PresetLockListItemViewDTO> itemViewDtoList = trans2PresetLockListItemViewDtoList(presetLockList);
		
		ViewDTO<List<PresetLockListItemViewDTO>> view = new ViewDTO<List<PresetLockListItemViewDTO>>();
		view.setData(itemViewDtoList);
		return view;
	}

	private List<PresetLockListItemViewDTO> trans2PresetLockListItemViewDtoList(
			List<PresetLock> presetLockList) {
		if( presetLockList == null ){
			return null;
		}
		
		List<PresetLockListItemViewDTO> viewList = new ArrayList<PresetLockListItemViewDTO>();
		
		for(PresetLock presetLock: presetLockList){
			PresetLockListItemViewDTO viewDto = trans2PresetLockListItemViewDto(presetLock);
			if( viewDto != null ){
				viewList.add(viewDto);
			}
		}
		
		return viewList;
	}

	private PresetLockListItemViewDTO trans2PresetLockListItemViewDto(
			PresetLock presetLock) {
		if( presetLock == null ){
			return null;
		}
		
		PresetLockListItemViewDTO view = new PresetLockListItemViewDTO();
		view.setId(presetLock.getId());
		view.setStartTime(presetLock.getStartTime());
		view.setEndTime(presetLock.getEndTime());
		view.setPresetOnOff(presetLock.getPresetOnOff());
		
		List<Boolean> repeatList = getRepeatList(presetLock); 
		String repeatSummary = getRepeatSummary(repeatList); 
		view.setRepeatSummary(repeatSummary);
		return view;
	}

	private List<PresetLock> listByChildId(Integer childId) {
		PresetLockExample example = new PresetLockExample();
		Criteria criteria = example.createCriteria();
		criteria.andChildIdEqualTo(childId);
		
		return presetLockMapper.selectByExample(example);
	}

	@Override
	public ViewDTO<Boolean> batchDelete(Integer[] ids) throws ServiceException {
		if( ids == null ){
			throw new ServiceException("Parameter ids can not be null.");
		}
		
		for(Integer id:ids){
			presetLockMapper.deleteByPrimaryKey(id);
		}
		
		ViewDTO<Boolean> view = new ViewDTO<Boolean>();
		view.setData(true);
		return view;
	}

	@Override
	public ViewDTO<PresetLockViewDTO> loadPresetLockById(Integer presetLockId)
			throws ServiceException {
		if( presetLockId == null ){
			throw new ServiceException("Parameter presetLockId can not be null.");
		}
		
		ViewDTO<PresetLockViewDTO> view = new ViewDTO<PresetLockViewDTO>();
		PresetLock presetLock = this.getById(presetLockId);
		
		if( presetLock != null ){
			List<AppViewDTO> appList = presetLockAppService.listAppListByPresetLockId(presetLock.getId());
			
			PresetLockViewDTO presetLockViewDTO = trans2PresetLockViewDTO(presetLock,appList);
			view.setData(presetLockViewDTO);
			
			return view;
		}else{
			view.setMsg(ViewDTO.MSG_ERROR);
			view.setInfo("Schedule lock data is not exists.");
			view.setData(null);
			return view;
		}
	}

	@Override
	public ViewDTO<PresetLockViewDTO> delete(Integer childId,
			Integer presetLockId) throws ServiceException {
		if( childId == null || presetLockId == null ){
			throw new ServiceException("Parameter childId or presetLockId can not be null.");
		}
		
		PresetLock presetLock = presetLockMapper.selectByPrimaryKey(presetLockId);
		
		if( presetLock == null ){
			throw new ServiceException("Preset Lock is not exists.");
		}
		
		if( presetLock.getChildId() == null || !presetLock.getChildId().equals(childId) ){
			
		}
		
		// create view
		ViewDTO<PresetLockViewDTO> view = new ViewDTO<PresetLockViewDTO>();
		List<AppViewDTO> appList = presetLockAppService.listAppListByPresetLockId(presetLock.getId());
		view.setData(trans2PresetLockViewDTO(presetLock, appList));
		
		// do delete
		presetLockMapper.deleteByPrimaryKey(presetLockId);
		return view;
	}
	
	@Override
	public ViewDTO<Boolean> applyPresetLock(Integer presetLockId,
			PresetLockParam applyPresetLockParam) throws ServiceException {
		if( presetLockId == null || applyPresetLockParam == null ){
			throw new ServiceException("Parameter presetLockId or applyPresetLockParam can not be null.");
		}
		
		ViewDTO<Boolean> view = new ViewDTO<Boolean>();
		
		try {
			PresetLock presetLock = this.getById(presetLockId);
			
			fillData(presetLock,applyPresetLockParam);
			saveOrUpdate(presetLock);
			
			// update locked app
			presetLockAppService.updatePresetLockApp(presetLock.getId(),applyPresetLockParam.getAppIdList());
			
			if( applyPresetLockParam.getPresetOnOff() != null && applyPresetLockParam.getPresetOnOff().booleanValue() == true ){
				//if preset lock switch is on , send message to child end app 
				//push message to child
				Child child = childService.getById(presetLock.getChildId());

				if( child != null ){
					String registionId = child.getRegistionId();
					if( registionId != null ){
						Map<String,String> extra = new HashMap<String,String>();
						pushService.pushMessageToChildByRegistionId(registionId, PushConstants.MSG_CONTENT_PRESET_LOCK_SWITCH_ON, extra);
					}
				}
			}
			
			
			view.setData(true);
			return view;
		} catch (Exception e) {
			logger.error("apply preset lock error.",e);
			view.setData(false);
			view.setMsg(ViewDTO.MSG_ERROR);
			return view;
		}
	}
	
	private void fillData(PresetLock presetLock,
			PresetLockParam applyPresetLockParam)throws ServiceException {
		if( presetLock == null || applyPresetLockParam == null ){
			return ;
		}
		
		presetLock.setStartTime(applyPresetLockParam.getStartTime());
		presetLock.setEndTime(applyPresetLockParam.getEndTime());
		presetLock.setLockCallStatus(applyPresetLockParam.getLockCallStatus());
		presetLock.setPresetOnOff(applyPresetLockParam.getPresetOnOff());
		presetLock.setChildId(applyPresetLockParam.getChildId());
		
		List<Boolean> repeatList = applyPresetLockParam.getReapeat();
		if( repeatList != null && repeatList.size() == 7 ){
			presetLock.setRepeatMonday(repeatList.get(0));
			presetLock.setRepeatTuesday(repeatList.get(1));
			presetLock.setRepeatWednesday(repeatList.get(2));
			presetLock.setRepeatThurday(repeatList.get(3));
			presetLock.setRepeatFriday(repeatList.get(4));
			presetLock.setRepeatSaturday(repeatList.get(5));
			presetLock.setRepeatSunday(repeatList.get(6));
		}
	}

	@Override
	public ViewDTO<PresetLockViewDTO> newPresetLock(
			PresetLockParam applyPresetLockParam) throws ServiceException {
		if( applyPresetLockParam == null ){
			throw new ServiceException("Parameter presetLockId or applyPresetLockParam can not be null.");
		}
		
		ViewDTO<PresetLockViewDTO> view = new ViewDTO<PresetLockViewDTO>();
		
		try {
			PresetLock presetLock = new PresetLock();
			
			fillData(presetLock,applyPresetLockParam);
			presetLock = saveOrUpdate(presetLock);
			
			// update locked app
			presetLockAppService.updatePresetLockApp(presetLock.getId(),applyPresetLockParam.getAppIdList());
			
			if( applyPresetLockParam.getPresetOnOff() != null && applyPresetLockParam.getPresetOnOff().booleanValue() == true ){
				//if preset lock switch is on , send message to child end app 
				//push message to child
				Child child = childService.getById(presetLock.getChildId());

				if( child != null ){
					String registionId = child.getRegistionId();
					if( registionId != null ){
						Map<String,String> extra = new HashMap<String,String>();
						pushService.pushMessageToChildByRegistionId(registionId, PushConstants.MSG_CONTENT_PRESET_LOCK_SWITCH_ON, extra);
					}
				}
			}
			
			List<AppViewDTO> appList = presetLockAppService.listAppListByPresetLockId(presetLock.getId());
			PresetLockViewDTO presetLockViewDTO = trans2PresetLockViewDTO(presetLock,appList);
			view.setData(presetLockViewDTO);
			
			return view;
		} catch (Exception e) {
			logger.error("apply preset lock error.",e);
			view.setData(null);
			view.setMsg(ViewDTO.MSG_ERROR);
			return view;
		}
	}
	
	
}
