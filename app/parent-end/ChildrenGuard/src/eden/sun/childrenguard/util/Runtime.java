package eden.sun.childrenguard.util;

import java.util.HashMap;
import java.util.Map;

import org.cometd.bayeux.Message;
import org.cometd.bayeux.client.ClientSession;
import org.cometd.bayeux.client.ClientSessionChannel;
import org.cometd.client.BayeuxClient;
import org.cometd.client.transport.ClientTransport;
import org.cometd.client.transport.LongPollingTransport;
import org.eclipse.jetty.client.HttpClient;

import android.util.Log;
import eden.sun.childrenguard.comet.LoginListener;
import eden.sun.childrenguard.comet.RegisterListener;

public class Runtime {
	protected static final String TAG = "Runtime";
	
	
	private volatile static Runtime runtime;
	private HttpClient httpClient ;
	
	private Runtime() {
		Log.i(TAG, "Init Runtime.");
		connect();
	}

	private void connect() {
		Log.i(TAG, "Connect to server.");
		httpClient  = new HttpClient();
		try {
			httpClient.start();
		} catch (Exception e) {
			Log.e(TAG, "¡¨Ω” ß∞‹");
			return ;
		}
		
		// Prepare the transport
		Map<String, Object> options = new HashMap<String, Object>();
		ClientTransport transport = new LongPollingTransport(options, httpClient);
        ClientSession client = new BayeuxClient(CometdConfig.COMETD_URL, transport);
		
        client.handshake(null,new ClientSessionChannel.MessageListener()
		{
		    public void onMessage(ClientSessionChannel channel, Message message)
		    {
		    	System.out.println("fail to connect to server.");
		        if (message.isSuccessful())
		        {
		        	Log.i(TAG,"success connect to server -" + message);
		        	//finalClient.getChannel(CHANNEL).subscribe(fooListener);
		            // Here handshake is successful
		        }
		    }
		});
        
        subscribe(client);
	}


	private void subscribe(ClientSession client) {
		client.getChannel(CometdConfig.LOGIN_CHANNEL).subscribe(new LoginListener());
		client.getChannel(CometdConfig.REGISTER_CHANNEL).subscribe(new RegisterListener());
	}

	public void publish(ClientSession client){
		Map<String, Object> data = new HashMap<String, Object>();
	    data.put("username", "cccc");
        data.put("password", "222222");
	    client.getChannel(CometdConfig.LOGIN_CHANNEL).publish(data);
	    
	}
	
	public static Runtime getInstance() {
		if (runtime == null) {
			synchronized (Runtime.class) {
				if (runtime == null) {
					runtime = new Runtime();
				}
			}
		}

		return runtime;
	}

	/*public HttpClient getHttpClient() {
		return httpClient;
	}*/
	
}
