package eden.sun.childrenguard.server.cometd;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.log4j.Logger;
import org.cometd.annotation.Listener;
import org.cometd.annotation.Service;
import org.cometd.annotation.Session;
import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;

import eden.sun.childrenguard.server.dto.LoginViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.service.IAuthService;

@Named
@Singleton
@Service("passwordResetService")
public class PasswordResetService extends BaseCometService{
	private Logger logger = Logger.getLogger(PasswordResetService.class);
	@Inject
	private BayeuxServer bayeux;
	@Session
	private ServerSession serverSession;
	@Inject
	private IAuthService authService;
	
	@PostConstruct
	public void init() {
	}
	
	@Listener("/service/passwordReset")
	public void passwordReset(ServerSession remote, ServerMessage message) {
		System.out.println("/service/passwordReset");
		Map<String, Object> input = message.getDataAsMap();
		String email = (String) input.get("email");
		logger.info("password reset email:" + email );
		
		//ViewDTO<LoginViewDTO> view = authService.login(username,password);
		
		Map<String, Object> output = new HashMap<String, Object>();
		output.put("greeting", "Hello, " + email);
		
		remote.deliver(serverSession, "/service/passwordReset", output);
	}
}
