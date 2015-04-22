package eden.sun.childrenguard.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jraf.android.backport.switchwidget.Switch;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

import eden.sun.childrenguard.R;
import eden.sun.childrenguard.dto.AppManageListItemView;
import eden.sun.childrenguard.errhandler.DefaultVolleyErrorHandler;
import eden.sun.childrenguard.helper.RequestHelper;
import eden.sun.childrenguard.server.dto.AppViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.util.Config;
import eden.sun.childrenguard.util.JSONUtil;
import eden.sun.childrenguard.util.RequestURLConstants;

public class AppManageListAdapter extends BaseAdapter{
	private Activity context;
    private ArrayList<AppManageListItemView> data;
    private static LayoutInflater inflater=null;
    //public ImageLoader imageLoader; 
    private List<AppManageListItemView> changesData;
 
    public AppManageListAdapter(Activity context, ArrayList<AppManageListItemView> data) {
        this.context = context;
        this.data=data;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        changesData = new ArrayList<AppManageListItemView>();
        //imageLoader=new ImageLoader(context.getApplicationContext());
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
        	vi = inflater.inflate(R.layout.list_row_app_manage_list, null);
        }
        TextView appNameTextView = (TextView)vi.findViewById(R.id.appNameTextView);
        Switch switchBtn = (Switch)vi.findViewById(R.id.switchBtn);
        final AppManageListItemView app = data.get(position);
        
        appNameTextView.setText(app.getAppName());
        switchBtn.setChecked(app.isLock());
        
        final int finalPos = position;
        
        switchBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Switch curSwitch = (Switch)v;
				AppManageListItemView curApp = data.get(finalPos);
				
				boolean lockStatus = curSwitch.isChecked();
				curApp.setLock(lockStatus);
				
				//do update lock status of clicked app
				updateAppLockStatus(curApp.getAppId(),curApp.getAppName(),lockStatus);
				
				
			}
		});
        /*switchBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {

        	@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				Switch curSwitch = (Switch)buttonView;
				AppManageListItemView curApp = data.get(finalPos);
				
				boolean lockStatus = isChecked;
				curApp.setLock(lockStatus);
				
				//do update lock status of clicked app
				updateAppLockStatus(curApp.getAppId(),curApp.getAppName(),lockStatus);
			}

		});*/
       /* switchBtn.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				Switch curSwitch = (Switch)buttonView;
				((ChildrenManageActivity)context).setConfigChanges(true);
				data.get(finalPos).setLock(true);
				
			}
        	
        });*/
        /*TextView childNameTextView = (TextView)vi.findViewById(R.id.childName);
        TextView onlineStatusTextView = (TextView)vi.findViewById(R.id.onlineStatus);
        
        ChildrenListItemView child = data.get(position);
 
        // Setting all values in listview
  
        childNameTextView.setText(child.getChildName());
        onlineStatusTextView.setText(child.getOnlineStatus());*/
        return vi;
    }
    
    
    public void reloadData(List<AppViewDTO> appList) {
		if( appList == null || appList.size() == 0){
			return ;
		}
		this.data.clear();
		for(Iterator<AppViewDTO> it = appList.iterator();it.hasNext();){
			AppViewDTO app = it.next();
			addAppItem(app);
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

	public List<AppManageListItemView> getChangesData() {
		return changesData;
	}

	public void clearChangesData() {
		if( changesData != null ){
			changesData.clear();
		}
	}
	
	
	private void updateAppLockStatus(Integer appId, final String appName, final boolean lockStatus) {
		String url = Config.getInstance().BASE_URL_MVC + RequestURLConstants.URL_UPDATE_APP_LOCK_STATUS;  

		Map<String, String> params = new HashMap<String,String>();
		params.put("appId", appId.toString());
		params.put("lockStatus", String.valueOf(lockStatus));
		
		RequestHelper.getInstance(context).doPost(
			url,
			params,
			this.getClass(),
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					final ViewDTO<Boolean> view = JSONUtil.getUpdateAppLockStatusView(response);
							
					if( view.getMsg().equals(ViewDTO.MSG_SUCCESS) ){
						String msg = null;
						if( lockStatus == true ){
							msg = "App " + appName + " locked";
						}else{
							msg = "App " + appName + " unlocked";
						}
						Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(context, "fail", Toast.LENGTH_SHORT).show();
					}
				}

			}, 
			new DefaultVolleyErrorHandler(context));		
	}

}
