package eden.sun.childrenguard.server.service.impl;

import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.ChildEndExtraInfoParam;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.service.IChildEndService;

@Service
public class ChildEndServiceImpl extends BaseServiceImpl implements IChildEndService {

	@Override
	public ViewDTO<Boolean> uploadChildExtraInfo(
			ChildEndExtraInfoParam extraInfoParam) throws ServiceException {
		
		
		return null;
	}

}
