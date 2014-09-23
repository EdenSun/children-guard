package eden.sun.childrenguard.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import eden.sun.childrenguard.R;
import eden.sun.childrenguard.adapter.AppManageListAdapter;
import eden.sun.childrenguard.adapter.MoreListAdapter;
import eden.sun.childrenguard.dto.AppManageListItemView;
import eden.sun.childrenguard.dto.MoreListItemView;

public class ChildManageMoreFragment extends Fragment{
	private ListView moreList ;
	
	private MoreListAdapter moreListAdapter;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_child_manage_more, container, false);
        
        moreList = (ListView)v.findViewById(R.id.list);
        moreList.setAdapter(getMoreListAdapter());
        moreList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		return v;
    }

	private MoreListAdapter getMoreListAdapter() {
		ArrayList<MoreListItemView> list = new ArrayList<MoreListItemView>();
		MoreListItemView view = null;
		
		String title = null;
		int type = 0;
		Boolean switchOn = null;
		
		title = "Lock Phone";
		type = MoreListItemView.TYPE_SWITCH_ITEM;
		switchOn = false;
		view = new MoreListItemView(title,type,switchOn);
		list.add(view);
		
		title = "Lock Message";
		type = MoreListItemView.TYPE_SWITCH_ITEM;
		switchOn = false;
		view = new MoreListItemView(title,type,switchOn);
		list.add(view);
		
		title = "Wifi Only";
		type = MoreListItemView.TYPE_SWITCH_ITEM;
		switchOn = false;
		view = new MoreListItemView(title,type,switchOn);
		list.add(view);
		
		title = "Exception Phone";
		type = MoreListItemView.TYPE_ARROW_ITEM;
		switchOn = null;
		view = new MoreListItemView(title,type,switchOn);
		list.add(view);
		
		title = "Modify Lock Password";
		type = MoreListItemView.TYPE_ARROW_ITEM;
		switchOn = null;
		view = new MoreListItemView(title,type,switchOn);
		list.add(view);
		
		title = "New App Notify";
		type = MoreListItemView.TYPE_ARROW_ITEM;
		switchOn = null;
		view = new MoreListItemView(title,type,switchOn);
		list.add(view);

		title = "Uninstall App Notify";
		type = MoreListItemView.TYPE_ARROW_ITEM;
		switchOn = null;
		view = new MoreListItemView(title,type,switchOn);
		list.add(view);
		
		title = "Network Traffic Exceed Notify";
		type = MoreListItemView.TYPE_ARROW_ITEM;
		switchOn = null;
		view = new MoreListItemView(title,type,switchOn);
		list.add(view);
		
		title = "Speeding Notify";
		type = MoreListItemView.TYPE_ARROW_ITEM;
		switchOn = null;
		view = new MoreListItemView(title,type,switchOn);
		list.add(view);
		
		title = "Lock/Unlock Notify";
		type = MoreListItemView.TYPE_ARROW_ITEM;
		switchOn = null;
		view = new MoreListItemView(title,type,switchOn);
		list.add(view);
		
		return new MoreListAdapter(this.getActivity(),list);
	}
}
