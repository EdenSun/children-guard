package eden.sun.childrenguard.server.service.impl;

import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.ChildExtraInfo;
import eden.sun.childrenguard.server.service.IChildExtraInfoService;

@Service
public class ChildExtraInfoServiceImpl extends BaseServiceImpl implements IChildExtraInfoService {

	@Override
	public ChildExtraInfo getById(Integer childId) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
