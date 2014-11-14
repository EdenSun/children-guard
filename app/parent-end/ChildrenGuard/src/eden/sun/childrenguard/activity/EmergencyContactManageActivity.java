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

import eden.sun.childrenguard.R;
import eden.sun.childrenguard.adapter.ExceptionPhoneListAdapter;
import eden.sun.childrenguard.dto.ExceptionPahoneListItemView;
import eden.sun.childrenguard.errhandler.DefaultVolleyErrorHandler;
import eden.sun.childrenguard.helper.RequestHelper;
import eden.sun.childrenguard.server.dto.EmergencyContactViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.util.Config;
import eden.sun.childrenguard.util.JSONUtil;
import eden.sun.childrenguard.util.RequestURLConstants;
import eden.sun.childrenguard.util.UIUtil;

public class EmergencyContactManageActivity extends CommonActionBarActivity {
	private static final String TAG = "EmergencyContactManageActivity";
	
	private ListView list;
	private ExceptionPhoneListAdapter adapter;
	private Integer childId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exception_phone);
		
		childId = getIntent().getIntExtra("childId", 0);
		
		retrievePhoneData();

		list=(ListView)findViewById(R.id.list);

	    // Getting adapter by passing xml data ArrayList
	    adapter = new ExceptionPhoneListAdapter(this, new ArrayList<ExceptionPahoneListItemView>());
	    list.setAdapter(adapter);

	    // Click event for single list row
	    list.setOnItemClickListener(new OnItemClickListener() {

	        @Override
	        public void onItemClick(AdapterView<?> parent, View view,
	                int position, long id) {
	        }
	    });
	    
	}
	
	private void retrievePhoneData() {
		String title = "Emergency Contacts";
		String msg = "Please wait...";
		showProgressDialog(title,msg);
		
		String url = String.format(
				Config.BASE_URL_MVC + RequestURLConstants.URL_LIST_EMERGENCY_CONTACT_BY_CHILD + "?childId=%1$s",  
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
	
	
}
