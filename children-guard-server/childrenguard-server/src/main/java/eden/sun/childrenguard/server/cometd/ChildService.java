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
import eden.sun.childrenguard.server.service.IChildService;

@Named
@Singleton
@Service("childCometdService")
public class ChildService extends BaseCometService{
	@Inject
	private BayeuxServer bayeux;
	@Session
	private ServerSession serverSession;
	@Inject
	private IChildService childService;
	
	@PostConstruct
	public void init() {
	}
	
	@Listener("/service/child/listAllByParentId")
	public void listAllChildren(ServerSession remote, ServerMessage message) {
		System.out.println("/service/child/listAll");
		Map<String, Object> input = message.getDataAsMap();
		Integer parentId = Integer.parseInt( (String) input.get("parentId") );
		
		ViewDTO<List<ChildViewDTO>> view = childService.listAllViewByParentId(parentId);
		
		String resultJSON = toJson(view);
		
		remote.deliver(serverSession, "/service/child/listAll", resultJSON);
	}
	
	@Listener("/service/child/add")
	public void addChild(ServerSession remote, ServerMessage message) {
		System.out.println("/service/child/add");
		Map<String, Object> input = message.getDataAsMap();
		String mobile = (String) input.get("mobile") ;
		String nickname = (String) input.get("nickname") ;
		
		ViewDTO<ChildViewDTO> view = childService.add(mobile,nickname);
		
		String resultJSON = toJson(view);
		
		remote.deliver(serverSession, "/service/child/add", resultJSON);
	}
	
}
