package eden.sun.childrenguard.comet;

import java.util.List;

import org.cometd.bayeux.Message;
import org.cometd.bayeux.client.ClientSessionChannel;
import org.cometd.bayeux.client.ClientSessionChannel.MessageListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import eden.sun.childrenguard.activity.ChildrenListActivity;
import eden.sun.childrenguard.activity.CommonActionBarActivity;
import eden.sun.childrenguard.adapter.ChildrenListAdapter;
import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.util.JSONUtil;
import eden.sun.childrenguard.util.UIUtil;

public class ListMyChildrenListener extends BaseMessageListener implements MessageListener {
	private Activity context;
    private static final String TAG = "ListMyChildrenListener";

    
	public ListMyChildrenListener(Activity context) {
		super();
		this.context = context;
	}

	public void onMessage(ClientSessionChannel channel, Message message)
    {
		((CommonActionBarActivity)context).dismissProgressDialog();
		
        // Here you received a message on the channel
    	Log.i(TAG, "received from channel: " + channel);
    	final ViewDTO<List<ChildViewDTO>> view = JSONUtil.getListMyChildrenView(message.getData().toString());
    	context.runOnUiThread(new Runnable(){

			@Override
			public void run() {
				
				if( view.getMsg().equals(ViewDTO.MSG_SUCCESS) ){
					String accessToken = ((CommonActionBarActivity)context).getAccessToken();
					
					List<ChildViewDTO> childList = view.getData();
					
					ChildrenListAdapter childrenListAdapter = ((ChildrenListActivity)context).getChildrenListAdapter();
					childrenListAdapter.reloadData(childList);
					/*context.runOnUiThread(new Runnable(){

						@Override
						public void run() {
							Toast toast = UIUtil.getToast(context,"Login Success!");
							toast.show();
							
							Intent it = new Intent(context, ChildrenListActivity.class);
							context.startActivity(it);
							
							// finish login activity
							context.finish();
						}
						
					});*/
					
				}else{
					AlertDialog.Builder dialog = UIUtil.getErrorDialog(context,view.getInfo());
		    		
					dialog.show();
				}
				
			}
			
    	});
    }

}
