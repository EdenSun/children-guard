package eden.sun.childrenguard.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import eden.sun.childrenguard.R;
import eden.sun.childrenguard.dto.MoreListItemView;

public class MoreListAdapter extends BaseAdapter {
	 
    private Activity context;
    private ArrayList<MoreListItemView> data;
    private static LayoutInflater inflater=null;
    //public ImageLoader imageLoader; 
 
    public MoreListAdapter(Activity context, ArrayList<MoreListItemView> data) {
        this.context = context;
        this.data=data;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
 
    public int getCount() {
        return data.size();
    }
 
    public Object getItem(int position) {
        return position;
    }
 
    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if(convertView==null){
        	vi = inflater.inflate(R.layout.list_row_arrow_child_manage_more_list, null);
        }
 
//        TextView childNameTextView = (TextView)vi.findViewById(R.id.childName);
//        TextView onlineStatusTextView = (TextView)vi.findViewById(R.id.onlineStatus);
        
        MoreListItemView child = data.get(position);
 
        // Setting all values in listview
  
//        childNameTextView.setText(child.getChildName());
//        onlineStatusTextView.setText(child.getOnlineStatus());
        return vi;
    }
 
}