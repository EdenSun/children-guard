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
import eden.sun.childrenguard.dto.AppManageListItemView;

public class ChildAppManageFragment extends Fragment{
	private final String TAG = "ChildAppManageFragment";
	
	private ListView appList;
	private AppManageListAdapter appListAdapter;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_child_app_manage, container, false);
		
		appList = (ListView)v.findViewById(R.id.list);
		appList.setAdapter(getAppListAdapter());
		appList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
			}
			
		});
		return v;
    }

	private AppManageListAdapter getAppListAdapter() {
		ArrayList<AppManageListItemView> list = new ArrayList<AppManageListItemView>();
		AppManageListItemView view = null;
		
		for(int i=1;i<=20 ;i++){
			view = new AppManageListItemView();
			view.setAppName("App-000" + i);
			if( i%3 == 0 ){
				view.setLock(true);
			}else{
				view.setLock(false);
			}
			list.add(view);
		}
		
		return new AppManageListAdapter(this.getActivity(),list);
	}
	
}
