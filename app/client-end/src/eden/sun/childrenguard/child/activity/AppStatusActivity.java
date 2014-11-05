package eden.sun.childrenguard.child.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import eden.sun.childrenguard.child.R;
import eden.sun.childrenguard.child.dto.AppInfo;

public class AppStatusActivity extends CommonBindServiceActivity {
	private ListView list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_status);
		
		initComponent();
		
		list.setAdapter(new AppLockAdapter());
	}
	
	
	 private void initComponent() {
		 list = (ListView)findViewById(R.id.list);		
	}


	private class AppLockAdapter extends BaseAdapter {
		private List<AppInfo> appList ;
		
		public AppLockAdapter() {
			super();
			appList = new ArrayList<AppInfo>();
		}

		@Override
		public int getCount() {
			return appList.size();
		}

		@Override
		public Object getItem(int position) {
			return appList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			AppInfo info = appList.get(position);
			if (convertView == null) {
				View view = View.inflate(AppStatusActivity.this,
						R.layout.app_status_list_item, null);
				AppView appView = new AppView();
				appView.appName = (TextView) view.findViewById(R.id.appName);
				appView.lockStatus = (TextView) view
						.findViewById(R.id.lockStatus);

				appView.appName.setText(info.getAppName());
				appView.lockStatus.setText(String.valueOf(info.isLockStatus()));
				view.setTag(view);
				return view;
			} else {
				AppView appView = (AppView) convertView.getTag();
				appView.appName.setText(info.getAppName());
				appView.lockStatus.setText(String.valueOf(info.isLockStatus()));
				return convertView;
			}
		}

	}

	private class AppView {
		TextView appName;
		TextView lockStatus;
	} 
}
