package eden.sun.childrenguard.server.service;

import java.util.List;

import eden.sun.childrenguard.server.dto.PushMessageViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;

public interface IPushMessageService {

	ViewDTO<List<PushMessageViewDTO>> listPushMsg(String accessToken)throws ServiceException;

	ViewDTO<PushMessageViewDTO> delete(String accessToken, Integer pushMessageId)throws ServiceException;

	ViewDTO<Boolean> batchdelete(String accessToken, Integer[] ids)throws ServiceException;

}
