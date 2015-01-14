package eden.sun.childrenguard.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;

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
	private ListView list;
	private PushMessageListAdapter pushMsgListAdapter;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		View vi = inflater.inflate(R.layout.fragment_push_message_list, container, false);
		
		ArrayList<PushMessageListItemView> pushMsgList = retrievePushMsgListData();
	    
	    initList(vi);

	    // Getting adapter by passing xml data ArrayList
	    pushMsgListAdapter = new PushMessageListAdapter(getActivity(), pushMsgList);
	    list.setAdapter(pushMsgListAdapter);

	    // Click event for single list row
	    list.setOnItemClickListener(new OnItemClickListener() {

	        @Override
	        public void onItemClick(AdapterView<?> parent, View view,
	                int position, long id) {
	        	PushMessageListItemView msg = (PushMessageListItemView)pushMsgListAdapter.getItem(position);
	        	
	        	String title = msg.getTitle();
	        	String content = msg.getContent() + "(" + msg.getCreateTime() + ")";
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
	    loadPushMessageList();
	    
		return vi;
    }
	
	
	private void initList(View vi) {
		list=(ListView)vi.findViewById(R.id.list);
		list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		list.setMultiChoiceModeListener(new MultiChoiceModeListener() {
			 
			@Override
			public void onItemCheckedStateChanged(ActionMode mode,
					int position, long id, boolean checked) {
				// Capture total checked items
				final int checkedCount = list.getCheckedItemCount();
				// Set the CAB title according to total checked items
				mode.setTitle(checkedCount + " Selected");
				// Calls toggleSelection method from ListViewAdapter Class
				pushMsgListAdapter.toggleSelection(position);
			}
 
			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				switch (item.getItemId()) {
				case R.id.delete:
					// Calls getSelectedIds method from ListViewAdapter Class
					SparseBooleanArray selected = pushMsgListAdapter
							.getSelectedIds();
					// Captures all selected ids with a loop
					for (int i = (selected.size() - 1); i >= 0; i--) {
						if (selected.valueAt(i)) {
							WorldPopulation selecteditem = pushMsgListAdapter
									.getItem(selected.keyAt(i));
							// Remove selected items following the ids
							pushMsgListAdapter.remove(selecteditem);
						}
					}
					// Close CAB
					mode.finish();
					return true;
				default:
					return false;
				}
			}
 
			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				mode.getMenuInflater().inflate(R.menu.activity_main, menu);
				return true;
			}
 
			@Override
			public void onDestroyActionMode(ActionMode mode) {
				// TODO Auto-generated method stub
				pushMsgListAdapter.removeSelection();
			}
 
			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		
		
		
		/*SwipeMenuCreator creator = new SwipeMenuCreator() {

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
		};*/

		// set creator
		//list.setMenuCreator(creator);
		
		// click event
		/*list.setOnMenuItemClickListener(new OnMenuItemClickListener() {
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
		});*/
		    
	}

	private void doDeletePushMessage(Integer messageId) {
		String url = Config.BASE_URL_MVC + RequestURLConstants.URL_DELETE_PUSH_MESSAGE;  

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
				Config.BASE_URL_MVC + RequestURLConstants.URL_LIST_PUSH_MESSAGE + "?accessToken=%1$s",  
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
						dismissProgressDialog();
						
				    	final ViewDTO<List<PushMessageViewDTO>> view = JSONUtil.getListPushMessageView(response);
				    	
				    	if( view.getMsg().equals(ViewDTO.MSG_SUCCESS) ){
							List<PushMessageViewDTO> pushMessageList = view.getData();
							
							pushMsgListAdapter.reloadData(pushMessageList);
							
						}else{
							AlertDialog.Builder dialog = UIUtil.getErrorDialog(PushMessageListFragment.this.getActivity(),view.getInfo());
				    		
							dialog.show();
						}
						
					}
				}, 
				new DefaultVolleyErrorHandler(PushMessageListFragment.this.getActivity())
			);
		
	}


	private ArrayList<PushMessageListItemView> retrievePushMsgListData() {
		ArrayList<PushMessageListItemView> list = new ArrayList<PushMessageListItemView>();
		
		return list;
	}


}
