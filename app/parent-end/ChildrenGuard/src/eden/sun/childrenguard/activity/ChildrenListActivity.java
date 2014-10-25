package eden.sun.childrenguard.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import eden.sun.childrenguard.R;
import eden.sun.childrenguard.adapter.ChildrenListAdapter;
import eden.sun.childrenguard.dto.ChildrenListItemView;
import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.util.Config;
import eden.sun.childrenguard.util.JSONUtil;
import eden.sun.childrenguard.util.RequestHelper;
import eden.sun.childrenguard.util.RequestURLConstants;
import eden.sun.childrenguard.util.UIUtil;

public class ChildrenListActivity extends CommonActionBarActivity {
	private static final String TAG = "ChildrenListActivity";
	private ListView list;
	private ChildrenListAdapter childrenListAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_children_list);
		
		//runtime.subscribe(CometdChannel.CHILD_MANAGE_LIST_MY_CHILDREN , new ListMyChildrenListener(ChildrenListActivity.this));
		
	    ArrayList<ChildrenListItemView> childrenList = retrieveChildrenData();
	    
	    list=(ListView)findViewById(R.id.list);

	    // Getting adapter by passing xml data ArrayList
	    childrenListAdapter = new ChildrenListAdapter(this, childrenList);
	    list.setAdapter(childrenListAdapter);

	    // Click event for single list row
	    list.setOnItemClickListener(new OnItemClickListener() {

	        @Override
	        public void onItemClick(AdapterView<?> parent, View view,
	                int position, long id) {
	        	Intent intent = new Intent(ChildrenListActivity.this,ChildrenManageActivity.class);
	        	
	        	ChildrenListItemView child = (ChildrenListItemView)childrenListAdapter.getItem(position);
	        	
	        	intent.putExtra("childId", child.getId());
	        	startActivity(intent);
	        }
	    });
	    
	    /*AsyncTask<Map<String, Object>,Integer,String> task = new LoadMyChildrenTask(ChildrenListActivity.this);
		Map<String, Object> data = getLoadMyChildrenParam();
		task.execute(data);*/
	}

	
	private void loadChildrenList() {
		RequestHelper helper = getRequestHelper();
	    String url = String.format(
				Config.BASE_URL_MVC + RequestURLConstants.URL_LIST_MY_CHILDREN + "?accessToken=%1$s",  
				getAccessToken());  

	    String title = "Person List";
		String msg = "Loading person list,please wait...";
		showProgressDialog(title,msg);
		
		helper.doGet(
			url,
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					ChildrenListActivity.this.dismissProgressDialog();
					
			    	final ViewDTO<List<ChildViewDTO>> view = JSONUtil.getListMyChildrenView(response);
			    	
			    	if( view.getMsg().equals(ViewDTO.MSG_SUCCESS) ){
						String accessToken = ChildrenListActivity.this.getAccessToken();
						
						List<ChildViewDTO> childList = view.getData();
						
						ChildrenListAdapter childrenListAdapter = ChildrenListActivity.this.getChildrenListAdapter();
						childrenListAdapter.reloadData(childList);
						
					}else{
						AlertDialog.Builder dialog = UIUtil.getErrorDialog(ChildrenListActivity.this,view.getInfo());
			    		
						dialog.show();
					}
					
				}
			}, 
			new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					Log.e("TAG", error.getMessage(), error);
			}
		});
		
	}

	/*private Map<String, Object> getLoadMyChildrenParam() {
		Map<String, Object> data = new HashMap<String,Object>();
		data.put("accessToken", this.getAccessToken());
		return data;
	}*/

	private ArrayList<ChildrenListItemView> retrieveChildrenData() {
		ArrayList<ChildrenListItemView> list = new ArrayList<ChildrenListItemView>();
		/*ChildrenListItemView view = null;
		
		for(int i=1;i<=10 ;i++){
			view = new ChildrenListItemView();
			view.setId(i);
			view.setChildName("child-00" + i);
			if( i%3 == 0 ){
				view.setOnlineStatus("Online");
			}else{
				view.setOnlineStatus("Offline");
			}
			list.add(view);
		}*/
		
		return list;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_children_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_children_list_add) {
			Intent intent = new Intent(ChildrenListActivity.this,ChildrenListAddActivity.class);
			
			this.startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	
	/*class LoadMyChildrenTask extends AsyncTask<Map<String, Object>,Integer,String>{
		private Activity context;
		
		public LoadMyChildrenTask(Activity context) {
			super();
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			String title = "Person List";
			String msg = "Loading person list,please wait...";
			showProgressDialog(title,msg);
		}

		@Override
		protected String doInBackground(Map<String, Object>... params) {
			Map<String, Object> data = params[0];
			
			String msg = runtime.publish(data, CometdChannel.CHILD_MANAGE_LIST_MY_CHILDREN , new ListMyChildrenListener(ChildrenListActivity.this));
			
			return msg;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			
		}
		
	}*/

	@Override
	protected void onResume() {
		super.onResume();
		// load person list
	    loadChildrenList();
	}


	public ChildrenListAdapter getChildrenListAdapter() {
		return childrenListAdapter;
	}

}
