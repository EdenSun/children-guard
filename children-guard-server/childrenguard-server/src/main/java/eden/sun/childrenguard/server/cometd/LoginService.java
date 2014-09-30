package eden.sun.childrenguard.server.cometd;

import java.util.HashMap;
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

@Named
@Singleton
@Service("loginService")
public class LoginService {
	@Inject
	private BayeuxServer bayeux;
	@Session
	private ServerSession serverSession;

	@PostConstruct
	public void init() {
	}

	@Listener("/service/login")
	public void processLogin(ServerSession remote, ServerMessage message) {
		System.out.println("/service/login");
		Map<String, Object> input = message.getDataAsMap();
		String username = (String) input.get("username");
		String password = (String) input.get("password");
		System.out.println(username + "|" +password);

		Map<String, Object> output = new HashMap<String, Object>();
		output.put("greeting", "Hello, " + username + "-" + password);
		
		remote.deliver(serverSession, "/service/login", output);
	}
}
