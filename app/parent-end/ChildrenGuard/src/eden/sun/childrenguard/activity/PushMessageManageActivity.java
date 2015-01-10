package eden.sun.childrenguard.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.android.volley.Response;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;

import eden.sun.childrenguard.R;
import eden.sun.childrenguard.adapter.PushMessageListAdapter;
import eden.sun.childrenguard.dto.PushMessageListItemView;
import eden.sun.childrenguard.errhandler.DefaultVolleyErrorHandler;
import eden.sun.childrenguard.server.dto.PushMessageViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.util.Config;
import eden.sun.childrenguard.util.JSONUtil;
import eden.sun.childrenguard.util.RequestURLConstants;
import eden.sun.childrenguard.util.UIUtil;

public class PushMessageManageActivity extends CommonActionBarActivity  {
	private static final String TAG = "PushMessageManageActivity";
	private SwipeMenuListView list;
	private PushMessageListAdapter pushMsgListAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_push_message_manage);
		
	    ArrayList<PushMessageListItemView> pushMsgList = retrievePushMsgListData();
	    
	    initSwipeList();

	    // Getting adapter by passing xml data ArrayList
	    pushMsgListAdapter = new PushMessageListAdapter(this, pushMsgList);
	    list.setAdapter(pushMsgListAdapter);

	    // Click event for single list row
	    list.setOnItemClickListener(new OnItemClickListener() {

	        @Override
	        public void onItemClick(AdapterView<?> parent, View view,
	                int position, long id) {
	        	PushMessageListItemView msg = (PushMessageListItemView)pushMsgListAdapter.getItem(position);
	        	
	        	String title = "Message";
	        	String content = msg.getContent() + "(" + msg.getCreateTime() + ")";
	        	String leftBtnText = "Close";
	        	AlertDialog.Builder builder = 
	        			UIUtil.getAlertDialogWithOneBtn(
	        					PushMessageManageActivity.this, 
	        					title, content, 
	        					leftBtnText, 
	        					new DialogInterface.OnClickListener(){

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
									}
	        						
	        					});
	        	builder.show();
	        }
	    });
	    
	    // load message list
	    loadPushMessageList();
	    
	}
	
	private void initSwipeList() {
		list=(SwipeMenuListView)findViewById(R.id.list);
		
		SwipeMenuCreator creator = new SwipeMenuCreator() {

		    @Override
		    public void create(SwipeMenu menu) {
		        // create "delete" item
		        SwipeMenuItem deleteItem = new SwipeMenuItem(
		                getApplicationContext());
		        // set item background
		        deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
		                0x3F, 0x25)));
		        // set item width
		        deleteItem.setWidth(270);
		        // set a icon
		        deleteItem.setIcon(R.drawable.ic_delete);
		        // add to menu
		        menu.addMenuItem(deleteItem);
		    }
		};

		// set creator
		list.setMenuCreator(creator);
		
		// click event
		list.setOnMenuItemClickListener(new OnMenuItemClickListener() {
		    @Override
		    public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
		        switch (index) {
			        case 0:
			            // delete
			        	PushMessageListItemView selected = (PushMessageListItemView)pushMsgListAdapter.getItem(index);
			        	
			        	doDeletePushMessage(selected.getId());
			            break;
		        }
		        // false : close the menu; true : not close the menu
		        return false;
		    }
		});
		    
	}

	private void doDeletePushMessage(Integer messageId) {
		String url = Config.BASE_URL_MVC + RequestURLConstants.URL_DELETE_PUSH_MESSAGE;  

		String title = "Delete Message";
		String msg = "Please wait...";
		showProgressDialog(title,msg);	
		
		Map<String, String> params = new HashMap<String,String>();
		params.put("messageId", messageId.toString());
		
		getRequestHelper().doPost(
			url,
			params,
			PushMessageManageActivity.this.getClass(),
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					dismissProgressDialog();
					
					final ViewDTO<PushMessageViewDTO> view = JSONUtil.getDeletePushMessageView(response);
							
					if( view.getMsg().equals(ViewDTO.MSG_SUCCESS) ){
						Toast.makeText(PushMessageManageActivity.this, "Message deleted", Toast.LENGTH_SHORT).show();
						
						PushMessageViewDTO deletedMsg = view.getData();
						
						if( deletedMsg != null ){
							pushMsgListAdapter.delete(deletedMsg);
						}
					}else{
						String title = "Error";
						String msg = view.getInfo();
						String btnText = "OK";
						
						AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
								PushMessageManageActivity.this,
							title,
							msg,
							btnText,
							new DialogInterface.OnClickListener() {
					            @Override
					            public void onClick(DialogInterface dialog, int which) {
					            	dialog.dismiss();
					            }
					        }
						);
						
						dialog.show();
					}
				}

			}, 
			new DefaultVolleyErrorHandler(PushMessageManageActivity.this));
	}
	
	
	@Override
	protected void onStart() {
		super.onStart();
		
	}

	private void loadPushMessageList() {
	    String url = String.format(
				Config.BASE_URL_MVC + RequestURLConstants.URL_LIST_PUSH_MESSAGE + "?accessToken=%1$s",  
				getAccessToken());  

	    String title = "Message List";
		String msg = "Loading messages,please wait...";
		showProgressDialog(title,msg);
		
		getRequestHelper().doGet(
				url,
				PushMessageManageActivity.class,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						dismissProgressDialog();
						
				    	final ViewDTO<List<PushMessageViewDTO>> view = JSONUtil.getListPushMessageView(response);
				    	
				    	if( view.getMsg().equals(ViewDTO.MSG_SUCCESS) ){
							List<PushMessageViewDTO> pushMessageList = view.getData();
							
							PushMessageListAdapter pushMessageListAdapter = PushMessageManageActivity.this.getPushMsgListAdapter();
							pushMessageListAdapter.reloadData(pushMessageList);
							
						}else{
							AlertDialog.Builder dialog = UIUtil.getErrorDialog(PushMessageManageActivity.this,view.getInfo());
				    		
							dialog.show();
						}
						
					}
				}, 
				new DefaultVolleyErrorHandler(PushMessageManageActivity.this)
			);
		
	}


	private ArrayList<PushMessageListItemView> retrievePushMsgListData() {
		ArrayList<PushMessageListItemView> list = new ArrayList<PushMessageListItemView>();
		
		return list;
	}

	public PushMessageListAdapter getPushMsgListAdapter() {
		return pushMsgListAdapter;
	}

}
