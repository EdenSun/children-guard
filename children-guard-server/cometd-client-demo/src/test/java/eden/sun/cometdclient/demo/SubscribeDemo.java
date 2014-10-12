package eden.sun.cometdclient.demo;

import java.util.HashMap;
import java.util.Map;

import org.cometd.bayeux.Message;
import org.cometd.bayeux.client.ClientSession;
import org.cometd.bayeux.client.ClientSessionChannel;
import org.cometd.client.BayeuxClient;
import org.cometd.client.transport.ClientTransport;
import org.cometd.client.transport.LongPollingTransport;
import org.eclipse.jetty.client.HttpClient;

public class SubscribeDemo {
	private static final String LOGIN_CHANNEL = "/service/login";
	private static final String REGISTER_CHANNEL = "/service/register";
    private final ClientSessionChannel.MessageListener fooListener = new FooListener();
     
	public static void main(String[] args) throws Exception{
		new SubscribeDemo().subscribe();
	}

	private void subscribe() throws Exception {
		// Create (and eventually set up) Jetty's HttpClient:
		HttpClient httpClient = new HttpClient();
		// Here set up Jetty's HttpClient, for example:
		// httpClient.setMaxConnectionsPerDestination(2);
		httpClient.start();

		// Prepare the transport
		Map<String, Object> options = new HashMap<String, Object>();
		ClientTransport transport = new LongPollingTransport(options, httpClient);
        ClientSession client = new BayeuxClient("http://localhost:8080/childrenguard-server/cometd", transport);
       
        client.handshake(null,new ClientSessionChannel.MessageListener()
		{
		    public void onMessage(ClientSessionChannel channel, Message message)
		    {
		    	System.out.println("fail to connect to server.");
		        if (message.isSuccessful())
		        {
		        	System.out.println("success connect to server -" + message);
		        	//finalClient.getChannel(CHANNEL).subscribe(fooListener);
		            // Here handshake is successful
		        }
		    }
		});
        
        client.getChannel(LOGIN_CHANNEL).subscribe(fooListener);
        
        Map<String, Object> data = new HashMap<String, Object>();
	    data.put("username", "cccc");
        data.put("password", "222222");
	    client.getChannel(LOGIN_CHANNEL).publish(data);
	}
	
	
	private static class FooListener implements ClientSessionChannel.MessageListener
    {
        public void onMessage(ClientSessionChannel channel, Message message)
        {
            // Here you received a message on the channel
        	System.out.println("received a message");
        	System.out.println(channel);
        	System.out.println(message.getData());
        }
    }
}
