package eden.sun.childrenguard.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import eden.sun.childrenguard.R;
import eden.sun.childrenguard.activity.ExceptionPhoneManageActivity;
import eden.sun.childrenguard.activity.ModifyLockPasswordActivity;
import eden.sun.childrenguard.activity.NotifyMailManageActivity;
import eden.sun.childrenguard.adapter.MoreListAdapter;
import eden.sun.childrenguard.dto.MoreListItemView;
import eden.sun.childrenguard.util.ShareDataKey;

public class ChildManageMoreFragment extends CommonFragment{
	private Integer childId;
	protected static final String TAG = "ChildManageMoreFragment";

	private ListView moreList ;
	
	private MoreListAdapter moreListAdapter;
	private TextView nicknameTextView;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_child_manage_more, container, false);
        Intent intent = this.getActivity().getIntent();
		childId = intent.getIntExtra("childId",0);
		
		nicknameTextView = (TextView)v.findViewById(R.id.nicknameTextView);
		
        moreList = (ListView)v.findViewById(R.id.list);
        moreList.setAdapter(getMoreListAdapter());
        moreList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ListView listView = (ListView)parent;
				MoreListItemView item = (MoreListItemView)listView.getItemAtPosition(position);
				Log.i(TAG,item.toString());
				
				if( item.getType() == MoreListItemView.TYPE_ARROW_ITEM ){
					Class nextStep = item.getNextActivity();
					Intent intent = new Intent(ChildManageMoreFragment.this.getActivity(),nextStep);
					
					intent.putExtra("childId", childId);
					ChildManageMoreFragment.this.getActivity().startActivity(intent);
				}else if( item.getType() == MoreListItemView.TYPE_SWITCH_ITEM ){
					if( item.getTitle().equals(MoreListItemView.TITLE_SPEEDING_NOTIFICATION) ){
						
						String title = MoreListItemView.TITLE_MODITY_LOCK_PASSWORD;
						int type = MoreListItemView.TYPE_EDITTEXT_ITEM;
						Boolean switchOn = null;
						MoreListItemView speedingLimitItem = new MoreListItemView(title,type,null, switchOn);
						if( item.getSwitchOn() ){
							moreListAdapter.appendItem(speedingLimitItem);
						}else{
							moreListAdapter.removeItem(speedingLimitItem);
						}
						
					}
				}
				
			}
			
		});
		
        nicknameTextView.setText(getStringShareData( ShareDataKey.CHILD_NICKNAME ));
		return v;
    }

	private MoreListAdapter getMoreListAdapter() {
		ArrayList<MoreListItemView> list = new ArrayList<MoreListItemView>();
		MoreListItemView view = null;
		
		String title = null;
		int type = 0;
		Boolean switchOn = null;
		
		title = MoreListItemView.TITLE_MODITY_LOCK_PASSWORD;
		type = MoreListItemView.TYPE_ARROW_ITEM;
		switchOn = null;
		view = new MoreListItemView(title,type,ModifyLockPasswordActivity.class, switchOn);
		list.add(view);
		
		title = MoreListItemView.TITLE_EMERGENCY_CONTACTS;
		type = MoreListItemView.TYPE_ARROW_ITEM;
		switchOn = null;
		view = new MoreListItemView(title,type,ExceptionPhoneManageActivity.class,switchOn);
		list.add(view);
		
		title = MoreListItemView.TITLE_LOCK_CALLS;
		type = MoreListItemView.TYPE_SWITCH_ITEM;
		switchOn = false;
		view = new MoreListItemView(title,type,null,switchOn);
		list.add(view);
		
		title = MoreListItemView.TITLE_LOCK_TEXT_MESSAGING;
		type = MoreListItemView.TYPE_SWITCH_ITEM;
		switchOn = false;
		view = new MoreListItemView(title,type,null,switchOn);
		list.add(view);
		
		title = MoreListItemView.TITLE_WIFI_ONLY;
		type = MoreListItemView.TYPE_SWITCH_ITEM;
		switchOn = false;
		view = new MoreListItemView(title,type,null,switchOn);
		list.add(view);
		
		title = MoreListItemView.TITLE_NEW_APP_NOTIFICATION;
		type = MoreListItemView.TYPE_SWITCH_ITEM;
		switchOn = false;
		view = new MoreListItemView(title,type,NotifyMailManageActivity.class,switchOn);
		list.add(view);

		title = MoreListItemView.TITLE_UNINSTALL_APP_NOTIFICATION;
		type = MoreListItemView.TYPE_SWITCH_ITEM;
		switchOn = false;
		view = new MoreListItemView(title,type,NotifyMailManageActivity.class,switchOn);
		list.add(view);
		
		/*title = "Exceed Monthly Data Usage Notification";
		type = MoreListItemView.TYPE_ARROW_ITEM;
		switchOn = null;
		view = new MoreListItemView(title,type,NotifyMailManageActivity.class,switchOn);
		list.add(view);*/
		
		title = MoreListItemView.TITLE_LOCK_UNLOCK_NOTIFICATION;
		type = MoreListItemView.TYPE_SWITCH_ITEM;
		switchOn = false;
		view = new MoreListItemView(title,type,NotifyMailManageActivity.class,switchOn);
		list.add(view);
		
		title = MoreListItemView.TITLE_SPEEDING_NOTIFICATION;
		type = MoreListItemView.TYPE_SWITCH_ITEM;
		switchOn = false;
		view = new MoreListItemView(title,type,NotifyMailManageActivity.class,switchOn);
		list.add(view);
		
		
		return new MoreListAdapter(this.getActivity(),list);
	}
}
