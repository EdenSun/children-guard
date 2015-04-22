package eden.sun.childrenguard.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class PushMessageListFragment extends CommonFragment{
	private static final String TAG = "PushMessageListFragment";
	private SwipeMenuListView list;
	private PushMessageListAdapter pushMsgListAdapter;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		View vi = inflater.inflate(R.layout.fragment_push_message_list, container, false);
		
		ArrayList<PushMessageListItemView> pushMsgList = retrievePushMsgListData();
	    
	    initList(vi);

	    // Getting adapter by passing xml data ArrayList
	    pushMsgListAdapter = new PushMessageListAdapter(getActivity(),R.layout.list_row_push_message_list, pushMsgList);
	    list.setAdapter(pushMsgListAdapter);

	    // Click event for single list row
	    list.setOnItemClickListener(new OnItemClickListener() {

	        @Override
	        public void onItemClick(AdapterView<?> parent, View view,
	                int position, long id) {
	        	PushMessageListItemView msg = (PushMessageListItemView)pushMsgListAdapter.getItem(position);
	        	
	        	String title = msg.getTitle();
	        	String content = msg.getContent();
	        	String leftBtnText = "Close";
	        	AlertDialog.Builder builder = 
	        			UIUtil.getAlertDialogWithOneBtn(
	        					PushMessageListFragment.this.getActivity(), 
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
	    //loadPushMessageList();
	    
		return vi;
    }
	
	
	private void initList(View vi) {
		list=(SwipeMenuListView)vi.findViewById(R.id.list);
		SwipeMenuCreator creator = new SwipeMenuCreator() {

		    @Override
		    public void create(SwipeMenu menu) {
		        // create "delete" item
		        SwipeMenuItem deleteItem = new SwipeMenuItem(
		                getActivity().getApplicationContext());
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
			        	PushMessageListItemView selected = (PushMessageListItemView)pushMsgListAdapter.getItem(position);
			        	
			        	doDeletePushMessage(selected.getId());
			            break;
		        }
		        // false : close the menu; true : not close the menu
		        return false;
		    }
		});
		    
	}

	private void doDeletePushMessage(Integer messageId) {
		String url = Config.getInstance().BASE_URL_MVC + RequestURLConstants.URL_DELETE_PUSH_MESSAGE;  

		String title = "Delete Message";
		String msg = "Please wait...";
		showProgressDialog(title,msg);	
		
		Map<String, String> params = new HashMap<String,String>();
		params.put("accessToken", getAccessToken());
		params.put("pushMessageId", messageId.toString());
		
		getRequestHelper().doPost(
			url,
			params,
			PushMessageListFragment.this.getClass(),
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					dismissProgressDialog();
					
					final ViewDTO<PushMessageViewDTO> view = JSONUtil.getDeletePushMessageView(response);
							
					if( view.getMsg().equals(ViewDTO.MSG_SUCCESS) ){
						Toast.makeText(PushMessageListFragment.this.getActivity(), "Message deleted", Toast.LENGTH_SHORT).show();
						
						PushMessageViewDTO deletedMsg = view.getData();
						
						if( deletedMsg != null ){
							pushMsgListAdapter.delete(deletedMsg);
						}
					}else{
						String title = "Error";
						String msg = view.getInfo();
						String btnText = "OK";
						
						AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
							PushMessageListFragment.this.getActivity(),
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
			new DefaultVolleyErrorHandler(getActivity()));
	}
	
	
	private void loadPushMessageList() {
	    String url = String.format(
	    		Config.getInstance().BASE_URL_MVC + RequestURLConstants.URL_LIST_PUSH_MESSAGE + "?accessToken=%1$s",  
				getAccessToken());  

	    String title = "Message List";
		String msg = "Loading messages,please wait...";
		showProgressDialog(title,msg);
		
		getRequestHelper().doGet(
				url,
				PushMessageListFragment.class,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
				    	final ViewDTO<List<PushMessageViewDTO>> view = JSONUtil.getListPushMessageView(response);
				    	
				    	if( view.getMsg().equals(ViewDTO.MSG_SUCCESS) ){
							List<PushMessageViewDTO> pushMessageList = view.getData();
							
							pushMsgListAdapter.reloadData(pushMessageList);
							
						}else{
							AlertDialog.Builder dialog = UIUtil.getErrorDialog(PushMessageListFragment.this.getActivity(),view.getInfo());
				    		
							dialog.show();
						}
				    	
				    	dismissProgressDialog();
					}
				}, 
				new DefaultVolleyErrorHandler(PushMessageListFragment.this.getActivity())
			);
		
	}


	private ArrayList<PushMessageListItemView> retrievePushMsgListData() {
		ArrayList<PushMessageListItemView> list = new ArrayList<PushMessageListItemView>();
		
		return list;
	}


	@Override
	public void onResume() {
		loadPushMessageList();
		super.onResume();
	}

	

}
