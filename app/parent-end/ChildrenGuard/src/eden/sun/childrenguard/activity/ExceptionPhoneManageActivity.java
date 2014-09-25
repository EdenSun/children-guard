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
import eden.sun.childrenguard.adapter.ExceptionPhoneListAdapter;
import eden.sun.childrenguard.dto.ExceptionPahoneListItemView;

public class ExceptionPhoneManageActivity extends ActionBarActivity {
	private static final String TAG = "ExceptionPhoneManageActivity";
	
	private ListView list;
	private ExceptionPhoneListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exception_phone);
		
		ArrayList<ExceptionPahoneListItemView> phoneList = retrievePhoneData();
	    
	    list=(ListView)findViewById(R.id.list);

	    // Getting adapter by passing xml data ArrayList
	    adapter = new ExceptionPhoneListAdapter(this, phoneList);
	    list.setAdapter(adapter);

	    // Click event for single list row
	    list.setOnItemClickListener(new OnItemClickListener() {

	        @Override
	        public void onItemClick(AdapterView<?> parent, View view,
	                int position, long id) {
	        }
	    });
	}
	
	private ArrayList<ExceptionPahoneListItemView> retrievePhoneData() {
		ArrayList<ExceptionPahoneListItemView> list = new ArrayList<ExceptionPahoneListItemView>();
		ExceptionPahoneListItemView view = null;
		
		String[] nameAry = new String[]{
			"Jack",
			"Rose",
			"Kate",
			"Eden"
		};
		
		String[] phoneAry = new String[]{
			"13322928377",
			"13452637839",
			"15672654374",
			"17793877465"
		};
		for(int i=0;i<4 ;i++){
			view = new ExceptionPahoneListItemView();
			view.setId(i);
			view.setName(nameAry[i]);
			view.setPhone(phoneAry[i]);
			list.add(view);
		}
		
		return list;
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
			Intent intent = new Intent(ExceptionPhoneManageActivity.this,ExceptionPhoneAddActivity.class);
			
			ExceptionPhoneManageActivity.this.startActivity(intent);

			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
