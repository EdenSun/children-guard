package eden.sun.childrenguard.server.service;

import java.util.List;
import java.util.Map;

import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.Parent;

public interface IJPushService {

	void pushNotificationToParent(List<Parent> parentList,String title, String content)throws ServiceException;

	void pushMessageByRegistionId(String registionId,
			String msgContent, Map<String, String> extra)throws ServiceException;

}
