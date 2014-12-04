package eden.sun.childrenguard.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Response;

import eden.sun.childrenguard.R;
import eden.sun.childrenguard.adapter.PresetLockAppListAdapter;
import eden.sun.childrenguard.dto.AppManageListItemView;
import eden.sun.childrenguard.errhandler.DefaultVolleyErrorHandler;
import eden.sun.childrenguard.helper.RequestHelper;
import eden.sun.childrenguard.server.dto.AppViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.util.Callback;
import eden.sun.childrenguard.util.Config;
import eden.sun.childrenguard.util.JSONUtil;
import eden.sun.childrenguard.util.RequestURLConstants;
import eden.sun.childrenguard.util.UIUtil;

public class PresetLockAppDialogFragment extends DialogFragment{
	private Callback<List<Integer>> callback;
	private ListView appListView;
	private PresetLockAppListAdapter appListAdapter;
	
	List<AppViewDTO> appList ;
	
	public PresetLockAppDialogFragment(List<AppViewDTO> appList,Callback<List<Integer>> callback) {
		super();
		this.callback = callback;
		this.appList = appList;
	}
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	private PresetLockAppListAdapter getAppListAdapter() {
		if( appListAdapter == null ){
			ArrayList<AppManageListItemView> list = new ArrayList<AppManageListItemView>();
			appListAdapter = new PresetLockAppListAdapter(this.getActivity(),list);
			appListAdapter.reloadData(appList);
		}
		
		return appListAdapter;
	}
	

	private void initComponent(View v) {
		appListView = (ListView)v.findViewById(R.id.appListView);
	}


	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater mInflater = LayoutInflater.from(getActivity());  
        View v = mInflater.inflate(R.layout.fragment_preset_lock_app,null);  
        
        initComponent(v);
        appListView.setAdapter(getAppListAdapter());
        
        return new AlertDialog.Builder(getActivity())  
                .setTitle("Lock App Setting")  
                .setView(v)  
                .setPositiveButton("Confirm",  
                    new DialogInterface.OnClickListener() {  
                        public void onClick(DialogInterface dialog, int whichButton) { 
                        	Callback.CallbackResult<List<Integer>> result = new Callback.CallbackResult<List<Integer>>();
                        	result.setSuccess(true);
                        	result.setData(appListAdapter.getAppIdList());
                        	callback.execute(result);
                        }

                    }  
                )  
                .setNegativeButton("Cancel",  
                    new DialogInterface.OnClickListener() {  
                        public void onClick(DialogInterface dialog, int whichButton) {  
                        	dialog.dismiss();
                        }  
                    }  
                )  
                .create();  
	}
	
	
	private List<Integer> getAppIdList() {
		List<Integer> appIdList = new ArrayList<Integer>();
		
		// TODO Auto-generated method stub
		return appIdList;
	}
	
	
	private ProgressDialog progress;
	public void showProgressDialog(String title, String msg){
		this.progress = ProgressDialog.show(getActivity(), title,
			    msg, true);
		this.progress.setCancelable(true);
	}
	
	public void dismissProgressDialog(){
		if( progress != null ){
			progress.dismiss();
		}
	}
	
}