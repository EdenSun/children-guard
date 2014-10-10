package eden.sun.childrenguard.comet;

import org.cometd.bayeux.Message;
import org.cometd.bayeux.client.ClientSessionChannel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import eden.sun.childrenguard.activity.CommonActivity;
import eden.sun.childrenguard.util.UIUtil;

public class RegisterListener implements ClientSessionChannel.MessageListener
{
	private static final String TAG = "RegisterListener";
	private Activity context;
    
    public RegisterListener(Activity context) {
		super();
		this.context = context;
	}


	public void onMessage(ClientSessionChannel channel, Message message)
    {
        // Here you received a message on the channel
    	System.out.println("received a message");
    	
    	context.runOnUiThread(new Runnable(){

			@Override
			public void run() {
				((CommonActivity)context).dismissProgressDialog();
				
				String title = "Register";
				String msg = "Register Success.Press OK to login.";
				String btnText = "OK";
				
				AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
					context,
					title,
					msg,
					btnText,
					new DialogInterface.OnClickListener() {
			            @Override
			            public void onClick(DialogInterface dialog, int which) {
			            	context.finish();
			            }
			        }
				);
				
				dialog.show();
			}
    		
    	});
    }
}