package eden.sun.childrenguard.server.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.Parent;
import eden.sun.childrenguard.server.service.IJPushService;
import eden.sun.childrenguard.server.util.JPushConfig;

@Service
public class JPushServiceImpl extends BaseServiceImpl implements IJPushService {
	private static final int RETRY_TIMES = 5;
	private JPushClient parentJPushClient ;
	private JPushClient childJPushClient ;
	
	public JPushServiceImpl() {
		super();
		parentJPushClient = new JPushClient(JPushConfig.PARENT_END_MASTER_SECRET, JPushConfig.PARENT_END_APP_KEY, RETRY_TIMES);
		childJPushClient = new JPushClient(JPushConfig.CHILD_END_MASTER_SECRET, JPushConfig.CHILD_END_APP_KEY, RETRY_TIMES);
	}

	@Override
	public void pushNotificationToParent(List<Parent> parentList, String title,String content)
			throws ServiceException {
		if( parentList == null || parentList.size() == 0 ){
			return ;
		}
		
		List<String> registrationIdList = getRegistionIdList(parentList);
		
		if( registrationIdList != null && registrationIdList.size() > 0 ){
			try {
				PushPayload payload = buildAndroidPushPayload(registrationIdList,title,content);
				
	            PushResult result = parentJPushClient.sendPush(payload);
	            logger.info("Push done,Got result - " + result);

	        } catch (APIConnectionException e) {
	            // Connection error, should retry later
	        	logger.error("Connection error, should retry later", e);
	        } catch (APIRequestException e) {
	            // Should review the error, and fix the request
	        	logger.error("Should review the error, and fix the request", e);
	        	logger.info("HTTP Status: " + e.getStatus());
	        	logger.info("Error Code: " + e.getErrorCode());
	        	logger.info("Error Message: " + e.getErrorMessage());
	        }
		}
		
	}
	
	private List<String> getRegistionIdList(List<Parent> parentList) {
		List<String> registionIdList = new ArrayList<String>();
		
		if( parentList != null ){
			for(Parent parent:parentList){
				if( parent.getRegistionId() != null ){
					registionIdList.add(parent.getRegistionId());
				}
			}
		}
		return registionIdList;
	}

	private PushPayload buildAndroidPushPayload(String registrationId,String notificationTitle,String notificationContent) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.registrationId(registrationId))
                .setNotification(Notification.android(notificationContent, notificationTitle, null))
                .build();
    }
	
	private PushPayload buildAndroidPushPayload(List<String> registrationIds,String notificationTitle,String notificationContent) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.registrationId(registrationIds))
                .setNotification(Notification.android(notificationContent, notificationTitle, null))
                .build();
    }
	
	public static PushPayload buildAndroidMessageWithExtras(List<String> registrationIds,String msgContent,Map<String,String> extra) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.registrationId(registrationIds))
                .setMessage(Message.newBuilder()
                        .setMsgContent(msgContent)
                        .addExtras(extra)
                        .build())
                .build();
    }

	@Override
	public void pushMessageToChildByRegistionId(String registionId, String msgContent,
			Map<String, String> extra) throws ServiceException {
		try {
			List<String> registionIds = new ArrayList<String>();
			registionIds.add(registionId);
			
			PushPayload payload = buildAndroidMessageWithExtras(registionIds,msgContent,extra);
			
            PushResult result = childJPushClient.sendPush(payload);
            logger.info("Push done,Got result - " + result);

        } catch (APIConnectionException e) {
            // Connection error, should retry later
        	logger.error("Connection error, should retry later", e);
        } catch (APIRequestException e) {
            // Should review the error, and fix the request
        	logger.error("Should review the error, and fix the request", e);
        	logger.info("HTTP Status: " + e.getStatus());
        	logger.info("Error Code: " + e.getErrorCode());
        	logger.info("Error Message: " + e.getErrorMessage());
        }
	}

	@Override
	public PushResult pushToChild(String registionId, String msgContent,
			Map<String, String> extra) throws ServiceException {
		try {
			List<String> registionIds = new ArrayList<String>();
			registionIds.add(registionId);
			
			PushPayload payload = buildAndroidMessageWithExtras(registionIds,msgContent,extra);
			
            PushResult result = childJPushClient.sendPush(payload);
            logger.info("Push done,Got result - " + result);
            return result;
            
        } catch (APIConnectionException e) {
            // Connection error, should retry later
        	logger.error("Connection error, should retry later", e);
        	return null;
        } catch (APIRequestException e) {
            // Should review the error, and fix the request
        	logger.error("Should review the error, and fix the request", e);
        	logger.info("HTTP Status: " + e.getStatus());
        	logger.info("Error Code: " + e.getErrorCode());
        	logger.info("Error Message: " + e.getErrorMessage());
        	return null;
        }
	}
	

}
