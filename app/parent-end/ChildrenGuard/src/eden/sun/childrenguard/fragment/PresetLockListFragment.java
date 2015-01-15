package eden.sun.childrenguard.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Intent;
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
import eden.sun.childrenguard.activity.PresetLockActivity;
import eden.sun.childrenguard.adapter.PresetLockListAdapter;
import eden.sun.childrenguard.dto.ScheduleLockListItemView;
import eden.sun.childrenguard.errhandler.DefaultVolleyErrorHandler;
import eden.sun.childrenguard.server.dto.PresetLockListItemViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.util.Config;
import eden.sun.childrenguard.util.JSONUtil;
import eden.sun.childrenguard.util.RequestURLConstants;
import eden.sun.childrenguard.util.UIUtil;

public class PresetLockListFragment extends CommonFragment{
	private static final String TAG = "PresetLockListFragment";
	private Integer childId;
	private ListView list;
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
		});
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
		super.onResume();
		// load message list
	    loadPresetLockList();
	}

	
	
}
