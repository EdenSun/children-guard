package eden.sun.childrenguard.adapter;

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
import android.widget.TextView;
import eden.sun.childrenguard.R;
import eden.sun.childrenguard.dto.AppManageListItemView;
import eden.sun.childrenguard.server.dto.AppViewDTO;
import android.widget.CheckBox;

public class PresetLockAppListAdapter extends BaseAdapter{
	private Activity context;
    private ArrayList<AppManageListItemView> data;
    private static LayoutInflater inflater=null;
    //public ImageLoader imageLoader; 
    private List<AppManageListItemView> changesData;
 
    public PresetLockAppListAdapter(Activity context, ArrayList<AppManageListItemView> data) {
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
				
			}
			
		
		});
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

}
