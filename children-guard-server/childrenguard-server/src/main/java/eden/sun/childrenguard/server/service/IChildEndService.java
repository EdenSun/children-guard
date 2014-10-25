package eden.sun.childrenguard.server.service;

import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.ChildEndExtraInfoParam;
import eden.sun.childrenguard.server.exception.ServiceException;

public interface IChildEndService {

	ViewDTO<Boolean> uploadChildExtraInfo(ChildEndExtraInfoParam extraInfoParam)throws ServiceException;

}
