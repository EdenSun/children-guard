package eden.sun.childrenguard.adapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jraf.android.backport.switchwidget.Switch;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import eden.sun.childrenguard.R;
import eden.sun.childrenguard.dto.AppManageListItemView;
import eden.sun.childrenguard.server.dto.AppViewDTO;

public class AppManageListAdapter extends BaseAdapter{
	private Activity context;
    private ArrayList<AppManageListItemView> data;
    private static LayoutInflater inflater=null;
    //public ImageLoader imageLoader; 
 
    public AppManageListAdapter(Activity context, ArrayList<AppManageListItemView> data) {
        this.context = context;
        this.data=data;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        AppManageListItemView app = data.get(position);
        
        appNameTextView.setText(app.getAppName());
        switchBtn.setChecked(app.isLock());
        
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
}
