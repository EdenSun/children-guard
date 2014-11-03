package eden.sun.childrenguard.server.service.impl;

import javax.inject.Inject;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dao.generated.ChildSettingMapper;
import eden.sun.childrenguard.server.dto.ChildSettingViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.ChildSetting;
import eden.sun.childrenguard.server.service.IChildSettingService;

@Service
public class ChildSettingServiceImpl implements IChildSettingService {
	@Inject
	ChildSettingMapper childSettingMapper;
	

	@Override
	public ChildSetting addIfNotExists(Integer childId) throws ServiceException {
		if( childId == null ){
			throw new ServiceException("Parameter child id can not be null.");
		}
		ChildSetting childSetting = childSettingMapper.selectByPrimaryKey(childId);
		if( childSetting == null ){
			childSetting = new ChildSetting();
			childSetting.setId(childId);
			
			childSettingMapper.insert(childSetting);
		}
		
		return childSettingMapper.selectByPrimaryKey(childId);
	}


	@Override
	public void update(ChildSetting childSetting) throws ServiceException {
		childSettingMapper.updateByPrimaryKey(childSetting);
	}


	@Override
	public ChildSettingViewDTO getViewById(Integer childId)
			throws ServiceException {
		if( childId == null ){
			throw new ServiceException("Parameter child id can not be null.");
		}
		
		ChildSetting childSetting = childSettingMapper.selectByPrimaryKey(childId);
		ChildSettingViewDTO view = trans2ChildSettingViewDTO(childSetting);
		return view;
	}


	private ChildSettingViewDTO trans2ChildSettingViewDTO(
			ChildSetting childSetting) {
		ChildSettingViewDTO view = new ChildSettingViewDTO();
		BeanUtils.copyProperties(childSetting, view);
		return view;
	}

	
}
