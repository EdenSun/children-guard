package eden.sun.childrenguard.server.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dto.PushMessageViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.service.IPushMessageService;

@Service
public class PushMessageServiceImpl implements IPushMessageService{

	@Override
	public ViewDTO<List<PushMessageViewDTO>> listPushMsg(String accessToken)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewDTO<PushMessageViewDTO> delete(String accessToken,
			Integer pushMessageId) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
