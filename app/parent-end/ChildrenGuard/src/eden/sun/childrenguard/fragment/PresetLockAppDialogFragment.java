package eden.sun.childrenguard.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import eden.sun.childrenguard.R;
import eden.sun.childrenguard.adapter.PresetLockAppListAdapter;
import eden.sun.childrenguard.dto.AppManageListItemView;
import eden.sun.childrenguard.util.Callback;

public class PresetLockAppDialogFragment extends DialogFragment{
	private Callback<List<Integer>> callback;
	
	private ListView appListView;
	private PresetLockAppListAdapter appListAdapter;
	
	
	public PresetLockAppDialogFragment(Callback<List<Integer>> callback) {
		super();
		this.callback = callback;
	}

    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	private PresetLockAppListAdapter getAppListAdapter() {
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
		appListAdapter = new PresetLockAppListAdapter(this.getActivity(),list);
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
                        	List<Integer> appIdList = getAppIdList();
                        	result.setData(appIdList);
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
	
}