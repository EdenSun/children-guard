package com.soloappinfo.adapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.soloappinfo.R;
import com.soloappinfo.dto.AppManageListItemView;
import com.soloappinfo.util.DataTypeUtil;

import eden.sun.childrenguard.server.dto.AppViewDTO;

public class PresetLockAppListAdapter extends BaseAdapter{
	private Activity context;
    private ArrayList<AppManageListItemView> data;
    private static LayoutInflater inflater=null;
    //public ImageLoader imageLoader; 
    private List<Integer> appIdList;
 
    public PresetLockAppListAdapter(Activity context, ArrayList<AppManageListItemView> data) {
        this.context = context;
        this.data=data;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
 
    public int getCount() {
        return data.size();
    }
 
    public Object getItem(int position) {
        return data.get(position);
    }
 
    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if(convertView==null){
        	vi = inflater.inflate(R.layout.list_row_preset_lock_app_list, null);
        }
        CheckBox appLockCheckbox = (CheckBox)vi.findViewById(R.id.appLockCheckbox);
        final AppManageListItemView app = data.get(position);
        
        appLockCheckbox.setText(app.getAppName());
        appLockCheckbox.setChecked(app.isLock());
        
        final int finalPos = position;
        appLockCheckbox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CheckBox curCheckbox = (CheckBox)v;
				boolean isChecked = curCheckbox.isChecked();
				app.setLock(isChecked);
				
				if( isChecked ){
					onAppChecked(app.getAppId());
				}else{
					onAppUnchecked(app.getAppId());
				}
			}
			
		});
        return vi;
    }
    
    
    public void reloadData(List<AppViewDTO> appList) {
		if( appList == null || appList.size() == 0){
			return ;
		}
		this.data.clear();
		
		initAppIdList();
		appIdList.clear();
		for(Iterator<AppViewDTO> it = appList.iterator();it.hasNext();){
			AppViewDTO app = it.next();
			addAppItem(app);
			if( DataTypeUtil.getNonNullBoolean(app.getLockStatus()) == true ){
				this.onAppChecked(app.getId());
			}
		}
		
		this.notifyDataSetChanged();
	}

	private void addAppItem(AppViewDTO app) {
		AppManageListItemView view = new AppManageListItemView();
		view.setAppId(app.getId());
		view.setAppName(app.getName());
		view.setLock(app.getLockStatus());
		
		data.add(view);
	}

	public void onAppChecked(Integer appId){
		initAppIdList();
		if( !appIdList.contains(appId) ){
			appIdList.add(appId);
		}
	}
	
	public void onAppUnchecked(Integer appId) {
		initAppIdList();
		if( appIdList.contains(appId) ){
			appIdList.remove(appId);
		}
	}


	private void initAppIdList(){
		if( appIdList == null ){
			appIdList = new ArrayList<Integer>();
		}
	}

	public List<Integer> getAppIdList() {
		return appIdList;
	}
	

}
