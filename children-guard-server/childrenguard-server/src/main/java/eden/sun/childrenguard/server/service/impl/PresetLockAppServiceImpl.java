package eden.sun.childrenguard.server.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dao.generated.PresetLockAppMapper;
import eden.sun.childrenguard.server.dto.AppViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.PresetLockApp;
import eden.sun.childrenguard.server.model.generated.PresetLockAppExample;
import eden.sun.childrenguard.server.model.generated.PresetLockAppExample.Criteria;
import eden.sun.childrenguard.server.service.IAppService;
import eden.sun.childrenguard.server.service.IPresetLockAppService;

@Service
public class PresetLockAppServiceImpl implements IPresetLockAppService {
	@Autowired
	private PresetLockAppMapper presetLockAppMapper;
	
	@Autowired
	private IAppService appService;
	
	@Override
	public List<AppViewDTO> listAppListByPresetLockId(Integer presetLockId)
			throws ServiceException {
		if( presetLockId == null ){
			throw new ServiceException("Parameter preset lock id can not be null.");
		}
		
		List<PresetLockApp> presetLockAppList = listPresetLock(presetLockId);
		
		List<AppViewDTO> appViewDTOList = trans2AppViewDTOList(presetLockAppList);
		
		return appViewDTOList;
	}

	private List<AppViewDTO> trans2AppViewDTOList(
			List<PresetLockApp> presetLockAppList) {
		if( presetLockAppList == null || presetLockAppList.size() == 0 ){
			return null;
		}
		
		List<Integer> appIdList = new ArrayList<Integer>();
		for(Iterator<PresetLockApp> it=presetLockAppList.iterator();it.hasNext();){
			PresetLockApp presetLockApp = it.next();
			appIdList.add(presetLockApp.getAppId());
		}
		
		List<AppViewDTO> appViewDTOList = appService.getApps(appIdList);
		
		return appViewDTOList;
	}

	@Override
	public List<PresetLockApp> listPresetLock(Integer presetLockId) throws ServiceException{
		PresetLockAppExample example = new PresetLockAppExample();
		Criteria criteria = example.createCriteria();
		criteria.andPresetLockIdEqualTo(presetLockId);
		
		List<PresetLockApp> presetLockAppList = presetLockAppMapper.selectByExample(example);
		return presetLockAppList;
	}

	@Override
	public void updatePresetLockApp(Integer presetId, List<Integer> appIdList)
			throws ServiceException {
		if( presetId == null ){
			throw new ServiceException("Parameter preset id can not be null.");
		}
		
		if( appIdList == null || appIdList.size() == 0 ){
			return ;
		}
		
		//clear data related to preset id
		deleteByPresetId(presetId);
		
		//insert preset lock app
		PresetLockApp presetLockApp = null;
		for(Integer appId: appIdList ){
			presetLockApp = new PresetLockApp();
			presetLockApp.setAppId(appId);
			presetLockApp.setPresetLockId(presetId);
			
			presetLockAppMapper.insert(presetLockApp);
		}
	}

	private void deleteByPresetId(Integer presetId) {
		PresetLockAppExample example = new PresetLockAppExample();
		Criteria criteria = example.createCriteria();
		criteria.andPresetLockIdEqualTo(presetId);
		
		presetLockAppMapper.deleteByExample(example);
	}

}
