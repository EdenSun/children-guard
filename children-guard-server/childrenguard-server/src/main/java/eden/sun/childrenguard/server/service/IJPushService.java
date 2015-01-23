package eden.sun.childrenguard.server.service;

import java.util.List;
import java.util.Map;

import cn.jpush.api.push.PushResult;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.Parent;

public interface IJPushService {

	void pushNotificationToParent(List<Parent> parentList,String title, String content)throws ServiceException;

	void pushMessageToChildByRegistionId(String registionId,
			String msgContent, Map<String, String> extra)throws ServiceException;

	PushResult pushToChild(String registionId, String msgContent,
			Map<String, String> extra)throws ServiceException;
}
