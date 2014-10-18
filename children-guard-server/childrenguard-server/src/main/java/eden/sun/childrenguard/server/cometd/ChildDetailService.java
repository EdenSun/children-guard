package eden.sun.childrenguard.server.cometd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.cometd.annotation.Listener;
import org.cometd.annotation.Service;
import org.cometd.annotation.Session;
import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;

import eden.sun.childrenguard.server.dto.AppViewDTO;
import eden.sun.childrenguard.server.dto.ChildBasicInfoViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.AppSettingParam;
import eden.sun.childrenguard.server.dto.param.SyncAppSettingParam;
import eden.sun.childrenguard.server.service.IChildDetailService;
import eden.sun.childrenguard.server.util.CometdChannel;
import eden.sun.childrenguard.server.util.JSONUtil;
import eden.sun.childrenguard.server.util.NumberUtil;

@Named
@Singleton
@Service("childDetailService")
public class ChildDetailService extends BaseCometService{
	@Inject
	private BayeuxServer bayeux;
	@Session
	private ServerSession serverSession;
	@Inject
	private IChildDetailService childDetailService;
	
	@PostConstruct
	public void init() {
	}
	
	@Listener("/service/childDetail/basicInfo")
	public void basicInfo(ServerSession remote, ServerMessage message) {
		logger.info(CometdChannel.CHILD_DETAIL_BASEIC_INFO_CHANNEL);
		Map<String, Object> input = message.getDataAsMap();
		Integer childId = NumberUtil.toInteger(input.get("childId").toString());
		
		ViewDTO<ChildBasicInfoViewDTO> view = childDetailService.getChildBasicInfo(childId);
		
		String resultJSON = toJson(view);

		remote.deliver(serverSession, CometdChannel.CHILD_DETAIL_BASEIC_INFO_CHANNEL, resultJSON);
	}

	
	@Listener("/service/childDetail/listChildApp")
	public void listApp(ServerSession remote, ServerMessage message) {
		logger.info(CometdChannel.CHILD_DETAIL_LIST_CHILD_APP);
		Map<String, Object> input = message.getDataAsMap();
		Integer childId = NumberUtil.toInteger(input.get("childId").toString());
		
		ViewDTO<List<AppViewDTO>> view = childDetailService.listChildApp(childId);
		
		String resultJSON = toJson(view);
		
		remote.deliver(serverSession, CometdChannel.CHILD_DETAIL_LIST_CHILD_APP, resultJSON);
	}
	
	@Listener("/service/childDetail/syncAppSetting")
	public void syncAppSetting(ServerSession remote, ServerMessage message) {
		logger.info(CometdChannel.CHILD_DETAIL_SYNC_APP_SETTING);
		Map<String, Object> input = message.getDataAsMap();
		SyncAppSettingParam param = null;
		try {
			param = getSyncChildAppParam(input);
		} catch (Exception e) {
			String resultJSON = JSONUtil.getErrorViewDTO("can not init parameters.");
			remote.deliver(serverSession, CometdChannel.CHILD_DETAIL_SYNC_APP_SETTING, resultJSON);
		}
		
		ViewDTO<Boolean> view = childDetailService.syncChildApp(param);
		
		String resultJSON = toJson(view);
		
		remote.deliver(serverSession, CometdChannel.CHILD_DETAIL_SYNC_APP_SETTING, resultJSON);
	}

	/**
	 * appSettings format:
	 * [{
	 *     appId: 1,
	 *     lockStatus: true
	 * },
	 * {
	 * 	   appId: 2,
	 *     lockStatus: false
	 * },
	 * ...
	 * ]
	 * @param input
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	private SyncAppSettingParam getSyncChildAppParam(Map<String, Object> input) throws JsonParseException, JsonMappingException, IOException {
		Integer childId = Integer.parseInt( (String) input.get("childId") );
		String appSettings = (String) input.get("appSettings");
		
		SyncAppSettingParam param = new SyncAppSettingParam();
		
		param.setChildId(childId);
		

		ObjectMapper mapper = new ObjectMapper();
		
		JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, AppSettingParam.class);
		
		List<AppSettingParam> appSettingsList =  (List<AppSettingParam>)mapper.readValue(appSettings, javaType); 
		 
		param.setAppSettings(appSettingsList);
		return param;
	}
}
