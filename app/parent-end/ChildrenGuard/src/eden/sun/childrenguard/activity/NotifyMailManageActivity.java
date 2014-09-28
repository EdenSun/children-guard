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
import eden.sun.childrenguard.adapter.NotifyMailListAdapter;
import eden.sun.childrenguard.dto.NotifyMailListItemView;

public class NotifyMailManageActivity extends ActionBarActivity {
	private ListView notifyMailList;
	private NotifyMailListAdapter notifyMailListAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notify_mail_manage);
		
		ArrayList<NotifyMailListItemView> mailList = retrieveNotifyMailListData();
	    
		notifyMailList = (ListView)findViewById(R.id.list);

	    // Getting adapter by passing xml data ArrayList
		notifyMailListAdapter = new NotifyMailListAdapter(this, mailList);
	    notifyMailList.setAdapter(notifyMailListAdapter);

	    // Click event for single list row
	    notifyMailList.setOnItemClickListener(new OnItemClickListener() {

	        @Override
	        public void onItemClick(AdapterView<?> parent, View view,
	                int position, long id) {
	        }
	    });
	}

	private ArrayList<NotifyMailListItemView> retrieveNotifyMailListData() {
		ArrayList<NotifyMailListItemView> list = new ArrayList<NotifyMailListItemView>();
		NotifyMailListItemView view = null;
		
		String[] nameAry = new String[]{
			"Jack",
			"Rose",
			"Kate",
			"Eden"
		};
		
		String[] mailAry = new String[]{
			"jack@gmail.com",
			"rose@hotmail.com",
			"kate@yahoo.com",
			"eden@sina.com"
		};
		for(int i=0;i<4 ;i++){
			view = new NotifyMailListItemView();
			view.setId(i);
			view.setName(nameAry[i]);
			view.setMail(mailAry[i]);
			list.add(view);
		}
		
		return list;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_notify_mail_manage, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_add) {
			Intent intent = new Intent(NotifyMailManageActivity.this,NotifyMailAddActivity.class);
			
			NotifyMailManageActivity.this.startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
