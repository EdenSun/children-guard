package eden.sun.childrenguard.comet;

import org.cometd.bayeux.Message;
import org.cometd.bayeux.client.ClientSessionChannel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import eden.sun.childrenguard.activity.ChildrenListActivity;
import eden.sun.childrenguard.activity.CommonActivity;
import eden.sun.childrenguard.server.dto.LoginViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.util.JSONUtil;
import eden.sun.childrenguard.util.UIUtil;

public class LoginListener implements ClientSessionChannel.MessageListener
{
	private Activity context;
    private static final String TAG = "LoginListener";

    
	public LoginListener(Activity context) {
		super();
		this.context = context;
	}


	public void onMessage(ClientSessionChannel channel, Message message)
    {
		((CommonActivity)context).dismissProgressDialog();
		
        // Here you received a message on the channel
    	Log.i(TAG, "received from channel: " + channel);
    	final ViewDTO<LoginViewDTO> view = JSONUtil.getLoginView(message.getData().toString());
    	context.runOnUiThread(new Runnable(){

			@Override
			public void run() {
				
				
				if( view.getMsg().equals(ViewDTO.MSG_SUCCESS) ){
					context.runOnUiThread(new Runnable(){

						@Override
						public void run() {
							Toast toast = UIUtil.getToast(context,"Login Success!");
							toast.show();
							
							Intent it = new Intent(context, ChildrenListActivity.class);
							context.startActivity(it);
							
							// finish login activity
							context.finish();
						}
						
					});
					
				}else{
					AlertDialog.Builder dialog = UIUtil.getErrorDialog(context,view.getInfo());
		    		
					dialog.show();
				}
				
			}
			
    	});
    }
}