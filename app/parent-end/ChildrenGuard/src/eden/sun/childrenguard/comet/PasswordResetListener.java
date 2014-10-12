package eden.sun.childrenguard.comet;

import org.cometd.bayeux.Message;
import org.cometd.bayeux.client.ClientSessionChannel;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import eden.sun.childrenguard.activity.ChangePasswordActivity;
import eden.sun.childrenguard.activity.ChildrenListActivity;
import eden.sun.childrenguard.activity.CommonActivity;
import eden.sun.childrenguard.activity.PasswordResetActivity;
import eden.sun.childrenguard.util.UIUtil;

public class PasswordResetListener implements ClientSessionChannel.MessageListener
{
	private Activity context;
    private static final String TAG = "PasswordResetListener";

    
	public PasswordResetListener(Activity context) {
		super();
		this.context = context;
	}


	public void onMessage(ClientSessionChannel channel, Message message)
    {
        // Here you received a message on the channel
    	Log.i(TAG, "received from channel: " + channel);
    	Log.i(TAG," Message:" + message);
    	
    	
    	Intent it = new Intent(context, ChangePasswordActivity.class);
		context.startActivity(it);   
		
		context.finish();
		
		
    	/*context.runOnUiThread(new Runnable(){

			@Override
			public void run() {
				Toast toast = UIUtil.getToast(context,"Login Success!");
				toast.show();
				
				context.runOnUiThread(new Runnable(){

					@Override
					public void run() {
						((CommonActivity)context).dismissProgressDialog();
						
						Intent it = new Intent(context, ChildrenListActivity.class);
						context.startActivity(it);
					}
					
				});
			}
			
    	});*/
    }
}