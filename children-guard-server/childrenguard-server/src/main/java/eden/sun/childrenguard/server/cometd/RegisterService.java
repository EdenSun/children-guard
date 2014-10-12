package eden.sun.childrenguard.server.cometd;

import java.io.IOException;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.cometd.annotation.Listener;
import org.cometd.annotation.Service;
import org.cometd.annotation.Session;
import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;

import eden.sun.childrenguard.server.dto.RegisterViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.service.IAuthService;

@Named
@Singleton
@Service("registerService")
public class RegisterService extends BaseCometService{
	@Inject
	private BayeuxServer bayeux;
	@Session
	private ServerSession serverSession;
	@Inject
	private IAuthService authService;
	
	@PostConstruct
	public void init() {
	}
	
	@Listener("/service/register")
	public void processRegister(ServerSession remote, ServerMessage message) {
		logger.info("/service/register");
		Map<String, Object> input = message.getDataAsMap();
		String firstName = (String) input.get("firstName");
		String lastName = (String) input.get("lastName");
		String email = (String) input.get("email");
		String password = (String) input.get("password");
		logger.info("Login(" + firstName + "," + lastName + "," + email + "," + password + ")");
		
		ViewDTO<RegisterViewDTO> view = authService.register(firstName,lastName,email,password);
		
		ObjectMapper mapper = new ObjectMapper();
		String json;
		try {
			json = mapper.writeValueAsString(view);
			
			remote.deliver(serverSession, "/service/register", json);
		} catch (Exception e) {
			logger.error("convert json error",e);
		}
		
		
		/*Map<String, Object> output = new HashMap<String, Object>();
		output.put("greeting", "register, " + email + "-" + password);
		*/
		
	}
}
