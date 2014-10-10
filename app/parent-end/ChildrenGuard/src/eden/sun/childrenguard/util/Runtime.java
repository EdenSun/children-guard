package eden.sun.childrenguard.util;

import java.util.HashMap;
import java.util.Map;

import org.cometd.bayeux.Message;
import org.cometd.bayeux.client.ClientSession;
import org.cometd.bayeux.client.ClientSessionChannel;
import org.cometd.bayeux.client.ClientSessionChannel.MessageListener;
import org.cometd.client.BayeuxClient;
import org.cometd.client.transport.ClientTransport;
import org.cometd.client.transport.LongPollingTransport;
import org.eclipse.jetty.client.HttpClient;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

public class Runtime {
	protected static final String TAG = "Runtime";
	
	private Activity context;
	private volatile static Runtime runtime;
	private HttpClient httpClient ;
	private ClientSession clientSession;
	 
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
        clientSession = new BayeuxClient(CometdConfig.COMETD_URL, transport);
		
        clientSession.handshake(null,new ClientSessionChannel.MessageListener()
		{
		    public void onMessage(ClientSessionChannel channel, Message message)
		    {
		    	System.out.println("fail to connect to server.");
		        if (message.isSuccessful())
		        {
		        	Log.i(TAG,"success connect to server -" + message);
		        	
		        	context.runOnUiThread(new Runnable(){
		    			@Override
		    			public void run() {
		    				Toast toast = UIUtil.getToast(context,"Success to connect to server.");
							toast.show();
		    			}
		    			
		        	});
		        	
		        	
					
		        	//finalClient.getChannel(CHANNEL).subscribe(fooListener);
		            // Here handshake is successful
		        }
		    }
		});
        
        //subscribe(clientSession);
	}


	/*private void subscribe(ClientSession client) {
		client.getChannel(CometdConfig.LOGIN_CHANNEL).subscribe(new LoginListener());
		client.getChannel(CometdConfig.REGISTER_CHANNEL).subscribe(new RegisterListener());
	}*/

	public void publish(Map<String, Object> data,String channel){
	    this.clientSession.getChannel(channel).publish(data);
	}
	
	public void publish(Map<String, Object> data,String channel,MessageListener messagelistener){
		this.clientSession.getChannel(channel).publish(data, messagelistener);
	}
	
	
	public HttpClient getHttpClient() {
		return httpClient;
	}

	public static Runtime getInstance(Activity context) {
		if (runtime == null) {
			synchronized (Runtime.class) {
				if (runtime == null) {
					runtime = new Runtime();
				}
			}
		}

		runtime.context = context;
		return runtime;
	}

	/*public HttpClient getHttpClient() {
		return httpClient;
	}*/
	
}
