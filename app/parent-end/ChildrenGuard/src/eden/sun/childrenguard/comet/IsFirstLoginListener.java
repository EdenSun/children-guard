package eden.sun.childrenguard.comet;

import org.cometd.bayeux.Message;
import org.cometd.bayeux.client.ClientSessionChannel;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import eden.sun.childrenguard.activity.CommonActivity;
import eden.sun.childrenguard.activity.LoginActivity;
import eden.sun.childrenguard.server.dto.IsFirstLoginViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.util.JSONUtil;
import eden.sun.childrenguard.util.UIUtil;

public class IsFirstLoginListener extends BaseMessageListener implements ClientSessionChannel.MessageListener
{
	private Activity context;
    private static final String TAG = "IsFirstLoginListener";

    
	public IsFirstLoginListener(Activity context) {
		super();
		this.context = context;
	}


	public void onMessage(ClientSessionChannel channel, Message message)
    {
		((CommonActivity)context).dismissProgressDialog();
        // Here you received a message on the channel
    	Log.i(TAG, "received from channel: " + channel);
    	final ViewDTO<IsFirstLoginViewDTO> view = JSONUtil.getIsFirstLoginView(message.getData().toString());
    	
    	if( view.getMsg().equals(ViewDTO.MSG_SUCCESS)){
    		if( view.getData().isFirstLogin() ){
    			context.runOnUiThread(new Runnable(){

					@Override
					public void run() {
						AlertDialog.Builder dialog = UIUtil.getLegalInfoDialog(context,view.getData().getLegalInfo());
						
						dialog.show();
					}
					
				});
    		}else{
    			((LoginActivity)context).doLogin();
    		}
    	}else{
    		context.runOnUiThread(new Runnable(){

				@Override
				public void run() {
					AlertDialog.Builder dialog = UIUtil.getErrorDialog(context,view.getInfo());
		    		
					dialog.show();
				}
				
			});
    		
    	}
    	
    	
    }


}