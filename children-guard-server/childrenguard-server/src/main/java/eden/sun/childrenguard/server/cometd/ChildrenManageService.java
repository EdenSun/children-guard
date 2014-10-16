package eden.sun.childrenguard.server.cometd;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.cometd.annotation.Listener;
import org.cometd.annotation.Service;
import org.cometd.annotation.Session;
import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;

import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.ChildAddParam;
import eden.sun.childrenguard.server.service.IChildrenManageService;
import eden.sun.childrenguard.server.util.CometdChannel;
import eden.sun.childrenguard.server.util.NumberUtil;

@Named
@Singleton
@Service("childManageService")
public class ChildrenManageService extends BaseCometService{
	
	@Inject
	private BayeuxServer bayeux;
	@Session
	private ServerSession serverSession;
	@Inject
	private IChildrenManageService childrenManageService;
	
	@PostConstruct
	public void init() {
	}
	
	@Listener("/service/childManage/listMyChildren")
	public void listAllChildren(ServerSession remote, ServerMessage message) {
		System.out.println(CometdChannel.CHILD_MANAGE_LIST_MY_CHILDREN);
		Map<String, Object> input = message.getDataAsMap();
		String accessToken = (String) input.get("accessToken");
		
		ViewDTO<List<ChildViewDTO>> view = childrenManageService.listChildrenByParentAccessToken(accessToken);
		
		String resultJSON = toJson(view);
		
		remote.deliver(serverSession, CometdChannel.CHILD_MANAGE_LIST_MY_CHILDREN, resultJSON);
	}
	
	
	@Listener("/service/childManage/addChild")
	public void addChild(ServerSession remote, ServerMessage message) {
		System.out.println(CometdChannel.CHILD_MANAGE_ADD_CHILD);
		Map<String, Object> input = message.getDataAsMap();
		ChildAddParam param = getAddChildParam(input);
		
		ViewDTO<ChildViewDTO> view = childrenManageService.addChild(param);
		
		String resultJSON = toJson(view);
		
		remote.deliver(serverSession, CometdChannel.CHILD_MANAGE_ADD_CHILD, resultJSON);
	}
	
	
	@Listener("/service/childManage/deleteChild")
	public void deleteChild(ServerSession remote, ServerMessage message) {
		System.out.println(CometdChannel.CHILD_MANAGE_DELETE_CHILD);
		Map<String, Object> input = message.getDataAsMap();
		Integer childId = NumberUtil.toInteger((String) input.get("childId") );
		
		ViewDTO<ChildViewDTO> view = childrenManageService.deleteChild(childId);
		
		String resultJSON = toJson(view);
		
		remote.deliver(serverSession, CometdChannel.CHILD_MANAGE_DELETE_CHILD, resultJSON);
	}

	private ChildAddParam getAddChildParam(Map<String, Object> input) {
		String mobile = (String) input.get("mobile");
		String firstName = (String) input.get("firstName");
		String lastName = (String) input.get("lastName");
		String nickname = (String) input.get("nickname");
		String relationshipId = (String) input.get("relationshipId");
		String parentAccessToken = (String) input.get("parentAccessToken");
		
		ChildAddParam param = new ChildAddParam();
		param.setMobile(mobile);
		param.setFirstName(firstName);
		param.setLastName(lastName);
		param.setNickname(nickname);
		param.setRelationshipId(NumberUtil.toInteger(relationshipId));
		param.setParentAccessToken(parentAccessToken);
		return param;
	}

}
