package eden.sun.childrenguard.server.service.impl;

import java.util.List;

import eden.sun.childrenguard.server.dto.AppViewDTO;
import eden.sun.childrenguard.server.dto.ChildBasicInfoViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.SyncAppSettingParam;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.service.IChildDetailService;

public class ChildDetailServiceImpl implements IChildDetailService {

	@Override
	public ViewDTO<List<AppViewDTO>> listChildApp(Integer childId)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewDTO<Boolean> syncChildApp(SyncAppSettingParam param)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewDTO<ChildBasicInfoViewDTO> getChildBasicInfo(Integer childId)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
