package eden.sun.childrenguard.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import eden.sun.childrenguard.R;
import eden.sun.childrenguard.adapter.AppManageListAdapter;
import eden.sun.childrenguard.dto.AppManageListItemView;
import eden.sun.childrenguard.server.dto.AppViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.util.Config;
import eden.sun.childrenguard.util.JSONUtil;
import eden.sun.childrenguard.util.RequestHelper;
import eden.sun.childrenguard.util.RequestURLConstants;
import eden.sun.childrenguard.util.UIUtil;

public class ChildAppManageFragment extends CommonFragment{
	private final String TAG = "ChildAppManageFragment";
	private Integer childId;
	
	private ListView appList;
	private AppManageListAdapter appListAdapter;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		Intent intent = this.getActivity().getIntent();
		childId = intent.getIntExtra("childId",0);
		
		View v = inflater.inflate(R.layout.fragment_child_app_manage, container, false);
		
		initComponent(v);
		
		appList.setAdapter(getAppListAdapter());
		appList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		loadAppList();
		
		return v;
    }

	private void initComponent(View v) {
		appList = (ListView)v.findViewById(R.id.list);		
	}

	private void loadAppList() {
		RequestHelper helper = getRequestHelper();
		String url = String.format(
				Config.BASE_URL_MVC + RequestURLConstants.URL_LIST_CHILD_APP + "?childId=%1$s",  
				childId
				);  

		helper.doGet(
			url,
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					ViewDTO<List<AppViewDTO>> view = JSONUtil.getListChildAppView(response);
			    	
			    	if( view.getMsg().equals(ViewDTO.MSG_SUCCESS)){
			    		appListAdapter.reloadData(view.getData());
			    		
			    	}else{
			    		AlertDialog.Builder dialog = UIUtil.getErrorDialog(ChildAppManageFragment.this.getActivity(),view.getInfo());
			    		
						dialog.show();
			    	}
			    	
				}
			}, 
			new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					Log.e("TAG", error.getMessage(), error);
			}
		});		
	}

	private AppManageListAdapter getAppListAdapter() {
		ArrayList<AppManageListItemView> list = new ArrayList<AppManageListItemView>();
		/*AppManageListItemView view = null;
		
		for(int i=1;i<=20 ;i++){
			view = new AppManageListItemView();
			view.setAppName("App-000" + i);
			if( i%3 == 0 ){
				view.setLock(true);
			}else{
				view.setLock(false);
			}
			list.add(view);
		}*/
		appListAdapter = new AppManageListAdapter(this.getActivity(),list);
		return appListAdapter;
	}
	
}
