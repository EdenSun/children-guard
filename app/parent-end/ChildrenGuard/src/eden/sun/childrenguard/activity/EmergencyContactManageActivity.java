package eden.sun.childrenguard.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;

import eden.sun.childrenguard.R;
import eden.sun.childrenguard.adapter.ExceptionPhoneListAdapter;
import eden.sun.childrenguard.dto.ExceptionPhoneListItemView;
import eden.sun.childrenguard.errhandler.DefaultVolleyErrorHandler;
import eden.sun.childrenguard.server.dto.EmergencyContactViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.util.Config;
import eden.sun.childrenguard.util.JSONUtil;
import eden.sun.childrenguard.util.RequestURLConstants;
import eden.sun.childrenguard.util.UIUtil;

public class EmergencyContactManageActivity extends CommonActionBarActivity {
	private static final String TAG = "EmergencyContactManageActivity";
	
	private SwipeMenuListView list;
	//private ListView list;
	private ExceptionPhoneListAdapter adapter;
	private Integer childId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exception_phone);
		
		childId = getIntent().getIntExtra("childId", 0);
		
		retrievePhoneData();

		initComponent();
				
	    // Getting adapter by passing xml data ArrayList
	    adapter = new ExceptionPhoneListAdapter(this,R.layout.list_row_exception_phone_list, new ArrayList<ExceptionPhoneListItemView>());
	    list.setAdapter(adapter);

	    // Click event for single list row
	    list.setOnItemClickListener(new OnItemClickListener() {

	        @Override
	        public void onItemClick(AdapterView<?> parent, View view,
	                int position, long id) {
	        }
	    });
	    
	    
	    // click event
		list.setOnMenuItemClickListener(new OnMenuItemClickListener() {
		    @Override
		    public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
		        switch (index) {
			        case 0:
			            // delete
			        	ExceptionPhoneListItemView selected = adapter.getItem(position);
			        	
			        	doDeleteEmergencyContact(selected.getId());
			            break;
		        }
		        // false : close the menu; true : not close the menu
		        return false;
		    }
		});
	    
	}
	
	private void initComponent() {
		list=(SwipeMenuListView)findViewById(R.id.list);
		SwipeMenuCreator creator = new SwipeMenuCreator() {

		    @Override
		    public void create(SwipeMenu menu) {
		        // create "delete" item
		        SwipeMenuItem deleteItem = new SwipeMenuItem(
		                EmergencyContactManageActivity.this.getApplicationContext());
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
	}

	private void retrievePhoneData() {
		String title = "Emergency Contacts";
		String msg = "Please wait...";
		showProgressDialog(title,msg);
		
		String url = String.format(
				Config.getInstance().BASE_URL_MVC + RequestURLConstants.URL_LIST_EMERGENCY_CONTACT_BY_CHILD + "?childId=%1$s",  
				childId);

		getRequestHelper().doGet(
			url,
			this.getClass(),
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					Log.d(TAG, response);
					dismissProgressDialog();
					ViewDTO<List<EmergencyContactViewDTO>> view = JSONUtil.getListEmergencyContactByChildView(response);
					
					if( view.getMsg().equals(ViewDTO.MSG_SUCCESS) ){
						List<EmergencyContactViewDTO> contacts = view.getData();
						
						adapter.reloadData(contacts);
					}else{
						AlertDialog.Builder dialog = UIUtil.getErrorDialog(EmergencyContactManageActivity.this,view.getInfo());
			    		
						dialog.show();
					}
					
				}
			}, 
			new DefaultVolleyErrorHandler(EmergencyContactManageActivity.this)
		);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.exception_phone, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_add) {
			Intent intent = new Intent(EmergencyContactManageActivity.this,EmergencyContactAddActivity.class);
			
			intent.putExtra("childId", childId);
			int requestCode = 0;
			EmergencyContactManageActivity.this.startActivityForResult(intent,requestCode);

			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {  
        case 0:  
        	String result = data.getStringExtra("result");
        	if( result != null && result.equals("success") ){
        		this.retrievePhoneData();
        	}
            break;  
        default:  
            break;  
        }  
	}
	
	
	
	
	private void doDeleteEmergencyContact(final Integer emergencyContactId) {
		String url = Config.getInstance().BASE_URL_MVC + RequestURLConstants.URL_DELETE_EMERGENCY_CONTACT;  

		Map<String, String> params = new HashMap<String,String>();
		params.put("emergencyContactId", emergencyContactId.toString());
		
		getRequestHelper().doPost(
			url,
			params,
			this.getClass(),
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					final ViewDTO<Boolean> view = JSONUtil.getDeleteEmergencyContactView(response);
							
					if( view.getMsg().equals(ViewDTO.MSG_SUCCESS) ){
						
						if( view.getData() != null && view.getData().booleanValue()==true){
							Toast.makeText(EmergencyContactManageActivity.this, "Emergency contacts deleted", Toast.LENGTH_SHORT).show();
							adapter.deleteById(emergencyContactId);
						}
					}else{
						Log.e(TAG,"delete emergency contact fail.");
					}
				}

			}, 
			new ErrorListener(){

				@Override
				public void onErrorResponse(VolleyError arg0) {
					Log.e(TAG,"delete emergency contact error.");
				}
				
			});
	}
	
}
