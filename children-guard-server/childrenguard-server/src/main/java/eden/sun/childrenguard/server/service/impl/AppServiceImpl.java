package eden.sun.childrenguard.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dao.generated.AppMapper;
import eden.sun.childrenguard.server.dto.AppViewDTO;
import eden.sun.childrenguard.server.dto.param.UploadApplicationInfoParam;
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

	@Override
	public boolean addOrUpdate(Integer childId, UploadApplicationInfoParam appInfo)
			throws ServiceException {
		if( childId == null || appInfo == null ){
			throw new ServiceException("Parameter can not be null.");
		}
		App app = getByPackageName(appInfo.getPackageName());
		if( app == null ){
			// add app
			app = new App();
			app.setChildId(childId);
			app.setCreateTime(new Date());
			app.setLockStatus(false);
			app.setName(appInfo.getAppName());
			app.setPackageName(appInfo.getPackageName());
			
			appMapper.insert(app);
			return true;
		}else{
			// app exists ,do nothing
			return true;
		}
	}

	private App getByPackageName(String packageName) {
		AppExample example = new AppExample();
		Criteria criteria = example.createCriteria();
		criteria.andPackageNameEqualTo(packageName);
		
		List<App> appList = appMapper.selectByExample(example);
	
		if( appList != null && appList.size() > 0 ){
			App app = appList.get(0);
			return app;
		}
		return null;
	}

	@Override
	public void deleteApp(Integer childId, UploadApplicationInfoParam appInfo)
			throws ServiceException {
		if( childId == null || appInfo == null ){
			throw new ServiceException("Parameter can not be null.");
		}
		App app = getByPackageName(appInfo.getPackageName());
		
		if( app != null ){
			// app with same package name is exists, delete it
			appMapper.deleteByPrimaryKey(app.getId());
		}
	}

	@Override
	public void clearAppInfoByChildId(Integer childId)
			throws ServiceException {
		if( childId == null ){
			throw new ServiceException("Parameter can not be null.");
		}
		AppExample example = new AppExample();
		Criteria criteria = example.createCriteria();
		criteria.andChildIdEqualTo(childId);
		
		appMapper.deleteByExample(example);
	}

	@Override
	public void saveAll(Integer childId,List<UploadApplicationInfoParam> appList)
			throws ServiceException {
		if( appList != null ){
			App app = null;
			for(UploadApplicationInfoParam param : appList ){
				app = trans2App(childId,param);
				if( app != null ){
					appMapper.insert(app);
				}
			}
		}
	}

	private App trans2App(Integer childId, UploadApplicationInfoParam param) {
		if( param == null ){
			return null;
		}
		
		App app = new App();
		app.setChildId(childId);
		app.setCreateTime(new Date());
		app.setLockStatus(false);
		app.setName(param.getAppName());
		app.setPackageName(param.getPackageName());
		return app;
	}
	
}