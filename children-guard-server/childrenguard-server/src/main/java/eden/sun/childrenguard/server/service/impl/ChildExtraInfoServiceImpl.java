package eden.sun.childrenguard.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dao.generated.ChildExtraInfoMapper;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.UpdateLocationParam;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.Child;
import eden.sun.childrenguard.server.model.generated.ChildExtraInfo;
import eden.sun.childrenguard.server.service.IChildExtraInfoService;
import eden.sun.childrenguard.server.service.IChildService;

@Service
public class ChildExtraInfoServiceImpl extends BaseServiceImpl implements IChildExtraInfoService {
	@Autowired
	private ChildExtraInfoMapper childExtraInfoMapper;
	
	@Autowired
	private IChildService childService;
	
	@Override
	public ChildExtraInfo getById(Integer childId) throws ServiceException {
		if( childId == null ){
			throw new ServiceException("Parameter child id can not be null.");
		}
		return childExtraInfoMapper.selectByPrimaryKey(childId);
	}

	@Override
	public ViewDTO<Boolean> updateLocation(String imei,
			UpdateLocationParam locationInfo) throws ServiceException {
		if( imei == null || locationInfo == null ){
			throw new ServiceException("Parameter imei or locationInfo can not be null.");
		}
		
		Child child = childService.getChildByImei(imei);
		if( child == null){
			throw new ServiceException("Person is not exists.");
		}
		
		ChildExtraInfo extraInfo = this.getById(child.getId());
		ViewDTO<Boolean> view = new ViewDTO<Boolean>();
		if( extraInfo == null ){
			// extra info is not exists , create it
			extraInfo = new ChildExtraInfo();
			extraInfo.setId(child.getId());
			extraInfo.setLongitude(locationInfo.getLongitude());
			extraInfo.setLatitude(locationInfo.getLatitude());
			extraInfo.setLocationUpdateTime(locationInfo.getLocationUpdateTime());
			extraInfo.setSpeed(locationInfo.getSpeed());
			extraInfo.setSpeedUpdateTime(locationInfo.getSpeedUpdateTime());
			
			childExtraInfoMapper.insert(extraInfo);
		}else{
			extraInfo.setLongitude(locationInfo.getLongitude());
			extraInfo.setLatitude(locationInfo.getLatitude());
			extraInfo.setLocationUpdateTime(locationInfo.getLocationUpdateTime());
			extraInfo.setSpeed(locationInfo.getSpeed());
			extraInfo.setSpeedUpdateTime(locationInfo.getSpeedUpdateTime());
			childExtraInfoMapper.updateByPrimaryKey(extraInfo);
		}
		
		view.setData(true);
		return view;
	}

	
}
