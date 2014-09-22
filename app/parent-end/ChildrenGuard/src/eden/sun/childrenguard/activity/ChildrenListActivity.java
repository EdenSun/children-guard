package eden.sun.childrenguard.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import eden.sun.childrenguard.R;
import eden.sun.childrenguard.adapter.ChildrenListAdapter;
import eden.sun.childrenguard.dto.ChildrenListItemView;

public class ChildrenListActivity extends ActionBarActivity {
	private static final String TAG = "ChildrenListActivity";
	
	private ListView list;
	private ChildrenListAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_children_list);
		/*RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());  
		mQueue.add(new JsonObjectRequest(Method.GET, URL, null,  
		            new Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject response) {
							Log.d(TAG, "response : " + response.toString());  
						}

		            }, null));  
		mQueue.start();*/
		
		
	    ArrayList<ChildrenListItemView> childrenList = retrieveChildrenData();
	    
	    list=(ListView)findViewById(R.id.list);

	    // Getting adapter by passing xml data ArrayList
	    adapter = new ChildrenListAdapter(this, childrenList);
	    list.setAdapter(adapter);

	    // Click event for single list row
	    list.setOnItemClickListener(new OnItemClickListener() {

	        @Override
	        public void onItemClick(AdapterView<?> parent, View view,
	                int position, long id) {
	        	Intent intent = new Intent(ChildrenListActivity.this,ChildrenManageActivity.class);
	        	
	        	startActivity(intent);
	        }
	    });
	}

	private ArrayList<ChildrenListItemView> retrieveChildrenData() {
		ArrayList<ChildrenListItemView> list = new ArrayList<ChildrenListItemView>();
		ChildrenListItemView view = null;
		
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
		}
		
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
}
