package eden.sun.childrenguard.server.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dao.generated.AppMapper;
import eden.sun.childrenguard.server.dto.AppViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.App;
import eden.sun.childrenguard.server.model.generated.AppExample;
import eden.sun.childrenguard.server.model.generated.AppExample.Criteria;
import eden.sun.childrenguard.server.service.IAppService;

@Service
public class AppServiceImpl extends BaseServiceImpl implements IAppService{
	@Inject
	private AppMapper appMapper;
	
	@Override
	public List<AppViewDTO> listViewByChildId(Integer childId)
			throws ServiceException {
		AppExample example = new AppExample();
		Criteria criteria = example.createCriteria();
		criteria.andChildIdEqualTo(childId);
		
		List<App> appList = appMapper.selectByExample(example);
		List<AppViewDTO> appViewList = trans2AppViewDTOList(appList);
		
		return appViewList;
	}

	private List<AppViewDTO> trans2AppViewDTOList(List<App> appList) {
		if( appList == null ){
			return null;
		}
		List<AppViewDTO> viewList = new ArrayList<AppViewDTO>();
		AppViewDTO dto = null;
		for(Iterator<App> it = appList.iterator();it.hasNext(); ){
			App app = it.next();
			dto = trans2AppViewDTO(app);
			if( dto != null ){
				viewList.add(dto);
			}
		}
		return viewList;
	}

	private AppViewDTO trans2AppViewDTO(App app) {
		if( app == null ){
			return null;
		}
		AppViewDTO view = new AppViewDTO();
		BeanUtils.copyProperties(app, view);
		
		return view;
	}

}
