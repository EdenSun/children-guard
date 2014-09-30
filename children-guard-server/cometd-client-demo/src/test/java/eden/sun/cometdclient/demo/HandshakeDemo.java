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


public class HandshakeDemo {
	
	public static void main(String[] args)throws Exception{
		new HandshakeDemo().doHandshake();
		
	}

	private void doHandshake()throws Exception {
		// Create (and eventually set up) Jetty's HttpClient:
		HttpClient httpClient = new HttpClient();
		// Here set up Jetty's HttpClient, for example:
		// httpClient.setMaxConnectionsPerDestination(2);
		httpClient.start();

		// Prepare the transport
		Map<String, Object> options = new HashMap<String, Object>();
		ClientTransport transport = new LongPollingTransport(options, httpClient);

		// Create the BayeuxClient
		ClientSession client = new BayeuxClient("http://localhost:8080/childrenguard-server/cometd", transport);

		client.handshake(null,new ClientSessionChannel.MessageListener()
		{
		    public void onMessage(ClientSessionChannel channel, Message message)
		    {
		    	System.out.println("fail to connect to server.");
		        if (message.isSuccessful())
		        {
		        	System.out.println(message);
		            // Here handshake is successful
		        }
		    }
		});
		
		// Here set up the BayeuxClient, for example:
		//client.getChannel(Channel.META_CONNECT).addListener(new ClientSessionChannel.MessageListener() { });

		
	}
}
