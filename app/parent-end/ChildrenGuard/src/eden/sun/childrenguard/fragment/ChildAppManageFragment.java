package eden.sun.childrenguard.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;

import eden.sun.childrenguard.R;
import eden.sun.childrenguard.adapter.AppManageListAdapter;
import eden.sun.childrenguard.dto.AppManageListItemView;
import eden.sun.childrenguard.errhandler.DefaultVolleyErrorHandler;
import eden.sun.childrenguard.server.dto.AppViewDTO;
import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.util.Callback;
import eden.sun.childrenguard.util.Config;
import eden.sun.childrenguard.util.JSONUtil;
import eden.sun.childrenguard.util.RequestURLConstants;
import eden.sun.childrenguard.util.ShareDataKey;
import eden.sun.childrenguard.util.UIUtil;

public class ChildAppManageFragment extends CommonFragment{
	private final String TAG = "ChildAppManageFragment";
	private Integer childId;
	
	private ListView appList;
	private AppManageListAdapter appListAdapter;
	private TextView nicknameTextView;
	
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
		
		nicknameTextView.setText(getStringShareData( ShareDataKey.CHILD_NICKNAME ));
		return v;
    }

	
	private void initComponent(View v) {
		appList = (ListView)v.findViewById(R.id.list);		
		nicknameTextView = (TextView)v.findViewById(R.id.nicknameTextView);
	}

	private void loadAppList() {
		String title = "App Manage";
		String msg = "Loading applications, please wait...";
		showProgressDialog(title,msg);
		
		String url = String.format(
				Config.BASE_URL_MVC + RequestURLConstants.URL_LIST_CHILD_APP + "?childId=%1$s",  
				childId
				);  

		getRequestHelper().doGet(
			url,
			ChildAppManageFragment.this.getClass(),
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					dismissProgressDialog();
					ViewDTO<List<AppViewDTO>> view = JSONUtil.getListChildAppView(response);
			    	
			    	if( view.getMsg().equals(ViewDTO.MSG_SUCCESS)){
			    		appListAdapter.reloadData(view.getData());
			    		
			    	}else{
			    		AlertDialog.Builder dialog = UIUtil.getErrorDialog(ChildAppManageFragment.this.getActivity(),view.getInfo());
			    		
						dialog.show();
			    	}
			    	
				}
			}, 
			new DefaultVolleyErrorHandler(getActivity())
		);		
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
	
	public List<AppManageListItemView> getChangesApp(){
		if( appListAdapter != null ){
			return appListAdapter.getChangesData();
		}
		return null;
	}


	public void clearChangesApp() {
		if( appListAdapter != null ){
			appListAdapter.clearChangesData();
		}
	}


	public void reloadApplications() {
		loadAppList();
	}

	public void doLockAllApp(final Callback<Boolean> successCallback) {
		String url = Config.BASE_URL_MVC + RequestURLConstants.URL_LOCK_ALL_APP;  

		Map<String,String> params = new HashMap<String,String>();
		params.put("childId", childId.toString());
		
		getRequestHelper().doPost(
			url,
			params,
			ChildAppManageFragment.this.getClass(),
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					final ViewDTO<Boolean> view = JSONUtil.getLockAllAppView(response);
							
					if( view.getMsg().equals(ViewDTO.MSG_SUCCESS) ){
						Callback.CallbackResult<Boolean> result = new Callback.CallbackResult<Boolean>();
						result.setSuccess(true);
						result.setData(view.getData());
						successCallback.execute(result);
					}else{
						String title = "Error";
						String msg = view.getInfo();
						String btnText = "OK";
						
						AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
							getActivity(),
							title,
							msg,
							btnText,
							new DialogInterface.OnClickListener() {
					            @Override
					            public void onClick(DialogInterface dialog, int which) {
					            	dialog.dismiss();
					            }
					        }
						);
						
						dialog.show();
					}
				}
			}, 
			new DefaultVolleyErrorHandler(getActivity()));		
	}


	public void doUnlockAllApp(final Callback<Boolean> successCallback) {
		String url = Config.BASE_URL_MVC + RequestURLConstants.URL_UNLOCK_ALL_APP;  

		Map<String,String> params = new HashMap<String,String>();
		params.put("childId", childId.toString());
		
		getRequestHelper().doPost(
			url,
			params,
			ChildAppManageFragment.this.getClass(),
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					final ViewDTO<Boolean> view = JSONUtil.getLockAllAppView(response);
							
					if( view.getMsg().equals(ViewDTO.MSG_SUCCESS) ){
						Callback.CallbackResult<Boolean> result = new Callback.CallbackResult<Boolean>();
						result.setSuccess(true);
						result.setData(view.getData());
						successCallback.execute(result);
					}else{
						String title = "Error";
						String msg = view.getInfo();
						String btnText = "OK";
						
						AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
							getActivity(),
							title,
							msg,
							btnText,
							new DialogInterface.OnClickListener() {
					            @Override
					            public void onClick(DialogInterface dialog, int which) {
					            	dialog.dismiss();
					            }
					        }
						);
						
						dialog.show();
					}
				}
			}, 
			new DefaultVolleyErrorHandler(getActivity()));		
	}
}
