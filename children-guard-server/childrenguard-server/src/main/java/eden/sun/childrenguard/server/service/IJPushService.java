package eden.sun.childrenguard.server.service;

import java.util.List;

import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.Parent;

public interface IJPushService {

	void pushMessageToParent(List<Parent> parentList,String title, String content)throws ServiceException;

}
