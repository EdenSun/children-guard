package eden.sun.childrenguard.server.cometd;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.cometd.annotation.Listener;
import org.cometd.annotation.Service;
import org.cometd.annotation.Session;
import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;

import eden.sun.childrenguard.server.dto.IsFirstLoginViewDTO;
import eden.sun.childrenguard.server.dto.LoginViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.service.IAuthService;
import eden.sun.childrenguard.server.util.CometdChannel;

@Named
@Singleton
@Service("loginCometdService")
public class LoginService extends BaseCometService{
	private Logger logger = Logger.getLogger(LoginService.class);
	@Inject
	private BayeuxServer bayeux;
	@Session
	private ServerSession serverSession;
	@Inject
	private IAuthService authService;
	
	@PostConstruct
	public void init() {
	}
	
	@Listener("/service/login")
	public void processLogin(ServerSession remote, ServerMessage message) {
		System.out.println(CometdChannel.LOGIN);
		Map<String, Object> input = message.getDataAsMap();
		String email = (String) input.get("email");
		String password = (String) input.get("password");
		logger.info("user login:" + email );
		
		ViewDTO<LoginViewDTO> view = authService.login(email,password);
		
		ObjectMapper mapper = new ObjectMapper();
		String json;
		try {
			json = mapper.writeValueAsString(view);
			logger.info(json);
			
			remote.deliver(serverSession, CometdChannel.LOGIN, json);
		} catch (Exception e) {
			logger.error("convert json error",e);
		}
		
	}
	
	
	@Listener("/service/isFirstLogin")
	public void isFristLogin(ServerSession remote, ServerMessage message) {
		System.out.println("/service/isFirstLogin");
		Map<String, Object> input = message.getDataAsMap();
		String email = (String) input.get("email");
		String password = (String) input.get("password");
		logger.info("user email:" + email );
		
		ViewDTO<IsFirstLoginViewDTO> view = authService.isFirstLogin(email,password);
		
		ObjectMapper mapper = new ObjectMapper();
		String json;
		try {
			json = mapper.writeValueAsString(view);
			logger.info(json);
			
			remote.deliver(serverSession, CometdChannel.IS_FIRST_LOGIN, json);
		} catch (Exception e) {
			logger.error("convert json error",e);
		}
	}
}
