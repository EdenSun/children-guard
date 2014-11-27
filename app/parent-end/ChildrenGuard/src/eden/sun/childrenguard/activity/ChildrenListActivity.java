package eden.sun.childrenguard.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.volley.Response;

import eden.sun.childrenguard.R;
import eden.sun.childrenguard.adapter.ChildrenListAdapter;
import eden.sun.childrenguard.dto.ChildrenListItemView;
import eden.sun.childrenguard.errhandler.DefaultVolleyErrorHandler;
import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.util.Config;
import eden.sun.childrenguard.util.JSONUtil;
import eden.sun.childrenguard.util.RequestURLConstants;
import eden.sun.childrenguard.util.ShareDataKey;
import eden.sun.childrenguard.util.UIUtil;

public class ChildrenListActivity extends CommonActionBarActivity {
	private static final String TAG = "ChildrenListActivity";
	private ListView list;
	private ChildrenListAdapter childrenListAdapter;
	private boolean doubleBackToExitPressedOnce;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_children_list);
		
		doubleBackToExitPressedOnce = false;
		
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
	
	@Override
	protected void onStart() {
		super.onStart();
		
		 // load person list
	    loadChildrenList();
	}

	private void loadChildrenList() {
	    String url = String.format(
				Config.BASE_URL_MVC + RequestURLConstants.URL_LIST_MY_CHILDREN + "?accessToken=%1$s",  
				getAccessToken());  

	    String title = "Person List";
		String msg = "Loading person list,please wait...";
		showProgressDialog(title,msg);
		
		getRequestHelper().doGet(
			url,
			ChildrenListActivity.class,
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
			new DefaultVolleyErrorHandler(ChildrenListActivity.this)
		);
		
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
			
			int requestCode = 0;
			this.startActivityForResult(intent,requestCode);
			return true;
		} else if( id == R.id.action_children_list_out ){
			Intent intent = new Intent(ChildrenListActivity.this,LoginActivity.class);
			removeLoginData();
			
			startActivity(intent);
			ChildrenListActivity.this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	private void removeLoginData() {
		removeFromShareData(ShareDataKey.LOGIN_ACCOUNT);
		removeFromShareData(ShareDataKey.LOGIN_PASSWORD);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {  
        case 0:  
        	String result = data.getStringExtra("result");
        	if( result != null && result.equals("success") ){
        		this.loadChildrenList();
        	}
            break;  
        default:  
            break;  
        }  
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
	}


	public ChildrenListAdapter getChildrenListAdapter() {
		return childrenListAdapter;
	}
	
	@Override  
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
        if (keyCode == KeyEvent.KEYCODE_BACK) {  
            moveTaskToBack(false);  
            return true;  
        }  
        return super.onKeyDown(keyCode, event);  
    }  
	

	/*@Override
	public void onBackPressed() {
	    if (doubleBackToExitPressedOnce) {
	        super.onBackPressed();
	        return;
	    }

	    this.doubleBackToExitPressedOnce = true;
	    Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

	    new Handler().postDelayed(new Runnable() {

	        @Override
	        public void run() {
	            doubleBackToExitPressedOnce=false;                       
	        }
	    }, 2000);
	} */
}
