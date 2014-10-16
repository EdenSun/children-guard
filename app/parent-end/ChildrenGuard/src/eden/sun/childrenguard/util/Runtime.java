package eden.sun.childrenguard.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class Runtime {
	protected static final String TAG = "Runtime";

	public static final String PUBLISH_SUCCESS = "success";
	
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
		        }else{
		        	/*Log.i(TAG,"Fail to connect to server.Try again...");
		        	
		        	context.runOnUiThread(new Runnable(){
		    			@Override
		    			public void run() {
		    				Toast toast = UIUtil.getToast(context,"Fail to connect to server.Try again...");
							toast.show();
		    			}
		    			
		        	});*/
		        }
		    }
		});
        
        clientSession.getChannel(CometdConfig.DISCONNECT_CHANNEL).addListener(new ClientSessionChannel.MessageListener(){

			@Override
			public void onMessage(ClientSessionChannel channel, Message message) {
				Log.i(TAG,"Connection is disconnected.");
				Log.i(TAG,"" +  channel);
				Log.i(TAG,"" +  message);
				Log.i(TAG,"" +  message.isSuccessful());
			}
        	
        });
        
        clientSession.getChannel(CometdConfig.SUBSCRIBE_CHANNEL).addListener(new ClientSessionChannel.MessageListener(){

			@Override
			public void onMessage(ClientSessionChannel channel, Message message) {
				Log.i(TAG,"subscribe listener called.");
				Log.i(TAG,"" +  channel);
				Log.i(TAG,"" +  message);
			}
        	
        });
        //subscribe(clientSession);
	}


	private void subscribe(ClientSession client) {
		/*client.getChannel(CometdConfig.LOGIN_CHANNEL).subscribe(new LoginListener());
		client.getChannel(CometdConfig.REGISTER_CHANNEL).subscribe(new RegisterListener());*/
	}

	public String publish(Map<String, Object> data,String channel,MessageListener messagelistener){
		if( this.clientSession.isConnected() ){
			Log.i(TAG, "Connection is connected.");
			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("channel", channel);
			params.put("data", data);
			AsyncTask<Map<String, Object>,Integer,Boolean> task = new PublishTask();
			task.execute(params);
			
			return PUBLISH_SUCCESS;
		}else{
			Log.i(TAG, "Connection is disconnected.");
			this.connect();
			
			this.subscribe(channel,messagelistener);
			if( !this.clientSession.isConnected() ){
				return "Can not connect to server,please try later.";
			}else{
				this.clientSession.getChannel(channel).publish(data);
				return PUBLISH_SUCCESS;
			}
			
		}
	}
	
	/*public void publish(Map<String, Object> data,String channel,MessageListener messagelistener){
		this.clientSession.getChannel(channel).publish(data, messagelistener);
	}*/
	
	
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

	public void subscribe(String channel,MessageListener listener) {
		/*if( hasNotSubscribed(clientSession,channel,listener) ){
			clientSession.getChannel(channel).subscribe(listener);
		}*/
		
		/*MessageListener existsListener = getSubscriberByType(channel,listener.getClass());
		
		if( existsListener != null ){
			final String finalChannel = channel;
			final MessageListener finalListener = listener;
			clientSession.getChannel(channel).unsubscribe(existsListener,new MessageListener(){

				@Override
				public void onMessage(ClientSessionChannel channel, Message message) {
					// TODO Auto-generated method stub
					if( message.isSuccessful() ){
						clientSession.getChannel(finalChannel).subscribe(finalListener);
					}else{
						Log.i(TAG,"unsubscribe fail");
					}
				}
				
			});
		}else{
			clientSession.getChannel(channel).subscribe(listener);
		}*/
		
		
		AsyncTask<Map<String,Object>,Integer,String> subscribeTask = new SubscribeTask();
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("channel", channel);
		params.put("listener", listener);
		
		subscribeTask.execute(params);
		
	}

	private MessageListener getSubscriberByType(
			String channel,
			Class<? extends MessageListener> clazz) {
		List<MessageListener> messageListenerList = clientSession.getChannel(channel).getSubscribers();
		for(Iterator<MessageListener> it=messageListenerList.iterator();it.hasNext();){
			MessageListener curListener = it.next();
			
			if (curListener.getClass().getName().equals(clazz.getName()) ){
				return curListener;
			}
		}
		
		return null;
	}

	private boolean hasNotSubscribed(ClientSession clientSession,
			String channel,
			MessageListener listener) {
		boolean hasNotSubscribed = true;
		List<MessageListener> messageListenerList = clientSession.getChannel(channel).getSubscribers();
		for(Iterator<MessageListener> it=messageListenerList.iterator();it.hasNext();){
			MessageListener curListener = it.next();
			
			if (curListener.getClass().equals(listener.getClass()) ){
				hasNotSubscribed = false;
				break;
			}
		}
		
		return hasNotSubscribed;
	}

	/*public HttpClient getHttpClient() {
		return httpClient;
	}*/
	
	class PublishTask extends AsyncTask<Map<String,Object>,Integer,Boolean>{

		@Override
		protected Boolean doInBackground(Map<String, Object>... params) {
			Map<String, Object> param = params[0];
			String channel = (String)param.get("channel");
			Map<String,Object> data = (Map<String,Object>)param.get("data");
			
			clientSession.getChannel(channel).publish(data);
			
			Log.i(TAG, "Publishing...... success!");
			return true;
		}
		
	};
	
	class SubscribeTask extends AsyncTask<Map<String,Object>,Integer,String>{
		
		public SubscribeTask() {
			super();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			/*String title = "Login";
			String msg = "Please wait...";
			showProgressDialog(title,msg);*/
		}

		@Override
		protected String doInBackground(Map<String,Object>... params) {
			Map<String, Object> data = params[0];
			String channel = (String)data.get("channel");
			MessageListener listener = (MessageListener)data.get("listener");
			
			MessageListener existsListener = getSubscriberByType(channel,listener.getClass());
			
			if( existsListener != null ){
				final String finalChannel = channel;
				final MessageListener finalListener = listener;
				clientSession.getChannel(channel).unsubscribe(existsListener,new MessageListener(){

					@Override
					public void onMessage(ClientSessionChannel channel, Message message) {
						// TODO Auto-generated method stub
						if( message.isSuccessful() ){
							clientSession.getChannel(finalChannel).subscribe(finalListener);
						}else{
							Log.i(TAG,"unsubscribe fail");
						}
					}
					
				});
			}else{
				clientSession.getChannel(channel).subscribe(listener);
			}
			
			return "success";
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			
		}
		
	}
}
