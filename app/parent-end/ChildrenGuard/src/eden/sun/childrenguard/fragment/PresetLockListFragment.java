package eden.sun.childrenguard.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import eden.sun.childrenguard.activity.PresetLockActivity;
import eden.sun.childrenguard.adapter.PresetLockListAdapter;
import eden.sun.childrenguard.dto.ScheduleLockListItemView;
import eden.sun.childrenguard.errhandler.DefaultVolleyErrorHandler;
import eden.sun.childrenguard.server.dto.PresetLockListItemViewDTO;
import eden.sun.childrenguard.server.dto.PresetLockViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.util.Config;
import eden.sun.childrenguard.util.JSONUtil;
import eden.sun.childrenguard.util.RequestURLConstants;
import eden.sun.childrenguard.util.UIUtil;

public class PresetLockListFragment extends CommonFragment{
	private static final String TAG = "PresetLockListFragment";
	private Integer childId;
	private SwipeMenuListView list;
	private PresetLockListAdapter presetLockListAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View vi = inflater.inflate(R.layout.fragment_preset_lock_list, container, false);
		
		Intent intent = this.getActivity().getIntent();
		childId = intent.getIntExtra("childId",0);
		
		ArrayList<ScheduleLockListItemView> pushMsgList = new ArrayList<ScheduleLockListItemView>();
	    
	    initList(vi);

	    // Getting adapter by passing xml data ArrayList
	    presetLockListAdapter = new PresetLockListAdapter(getActivity(),R.layout.list_row_preset_lock_list, pushMsgList);
	    list.setAdapter(presetLockListAdapter);

	    // Click event for single list row
	    list.setOnItemClickListener(new OnItemClickListener() {

	        @Override
	        public void onItemClick(AdapterView<?> parent, View view,
	                int position, long id) {
	        	ScheduleLockListItemView item = presetLockListAdapter.getItem(position);
	        	
	        	Intent intent = new Intent(getActivity(),PresetLockActivity.class);
	        	
	        	intent.putExtra("presetLockId", item.getId());
	        	startActivity(intent);
	        }
	    });
	    
	    
	    // load message list
	    //loadPresetLockList();
	    
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
			        	ScheduleLockListItemView selected = presetLockListAdapter.getItem(position);
			        	
			        	doDeletePresetLock(selected.getId());
			            break;
		        }
		        // false : close the menu; true : not close the menu
		        return false;
		    }
		});
		
		
		/*list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		list.setMultiChoiceModeListener(new MultiChoiceModeListener() {
			 
			@Override
			public void onItemCheckedStateChanged(ActionMode mode,
					int position, long id, boolean checked) {
				// Capture total checked items
				final int checkedCount = list.getCheckedItemCount();
				// Set the CAB title according to total checked items
				mode.setTitle(checkedCount + " Selected");
				// Calls toggleSelection method from ListViewAdapter Class
				presetLockListAdapter.toggleSelection(position);
			}
 
			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				switch (item.getItemId()) {
				case R.id.delete:
					// Calls getSelectedIds method from ListViewAdapter Class
					SparseBooleanArray selected = presetLockListAdapter
							.getSelectedIds();
					// Captures all selected ids with a loop
					for (int i = (selected.size() - 1); i >= 0; i--) {
						if (selected.valueAt(i)) {
							ScheduleLockListItemView selecteditem = presetLockListAdapter
									.getItem(selected.keyAt(i));
							// Remove selected items following the ids
							presetLockListAdapter.remove(selecteditem);
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
				mode.getMenuInflater().inflate(R.menu.fragment_preset_lock_list,menu);
				return true;
			}
 
			@Override
			public void onDestroyActionMode(ActionMode mode) {
				presetLockListAdapter.removeSelection();
			}
 
			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				return false;
			}
		});*/
	}
	
	private void loadPresetLockList() {
	    String url = String.format(
				Config.BASE_URL_MVC + RequestURLConstants.URL_LIST_PRESET_LOCK + "?childId=%1$s",  
				childId);  

	   /* String title = "Message List";
		String msg = "Loading schedules,please wait...";
		showProgressDialog(title,msg);*/
		
		getRequestHelper().doGet(
				url,
				PushMessageListFragment.class,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						//dismissProgressDialog();
						
				    	final ViewDTO<List<PresetLockListItemViewDTO>> view = JSONUtil.getListPresetLockView(response);
				    	
				    	if( view.getMsg().equals(ViewDTO.MSG_SUCCESS) ){
							List<PresetLockListItemViewDTO> pushMessageList = view.getData();
							
							presetLockListAdapter.reloadData(pushMessageList);
							
						}else{
							AlertDialog.Builder dialog = UIUtil.getErrorDialog(getActivity(),view.getInfo());
				    		
							dialog.show();
						}
						
					}
				}, 
				new DefaultVolleyErrorHandler(getActivity())
			);
		
	}


	@Override
	public void onResume() {
		// load message list
	    loadPresetLockList();
	    super.onResume();
	}

	
	private void doDeletePresetLock(Integer presetLockId) {
		String url = Config.BASE_URL_MVC + RequestURLConstants.URL_DELETE_PRESET_LOCK;  

		String title = "Delete Schedule";
		String msg = "Please wait...";
		showProgressDialog(title,msg);	
		
		Map<String, String> params = new HashMap<String,String>();
		params.put("childId", childId.toString());
		params.put("presetLockId", presetLockId.toString());
		
		getRequestHelper().doPost(
			url,
			params,
			getActivity().getClass(),
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					dismissProgressDialog();
					
					final ViewDTO<PresetLockViewDTO> view = JSONUtil.getDeletePresetLockView(response);
							
					if( view.getMsg().equals(ViewDTO.MSG_SUCCESS) ){
						Toast.makeText(getActivity(), "Schedule deleted", Toast.LENGTH_SHORT).show();
						
						PresetLockViewDTO deleted = view.getData();
						
						if( deleted != null ){
							presetLockListAdapter.delete(deleted);
						}
					}else{
						String title = "Error";
						String msg = view.getInfo();
						String btnText = "OK";
						
						AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
							getActivity(),
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
	
	
	
}
