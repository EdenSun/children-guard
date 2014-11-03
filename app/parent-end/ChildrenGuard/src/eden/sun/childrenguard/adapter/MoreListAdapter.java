package eden.sun.childrenguard.adapter;

import java.util.ArrayList;

import org.jraf.android.backport.switchwidget.Switch;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import eden.sun.childrenguard.R;
import eden.sun.childrenguard.dto.MoreListItemView;

public class MoreListAdapter extends BaseAdapter{
	 
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
        return data.get(position);
    }
 
    public long getItemId(int position) {
        return position;
    }
    
    @Override
    public int getItemViewType(int position) {
    	MoreListItemView child = data.get(position);
    	return child.getType();
    }

    @Override
    public int getViewTypeCount() {
        return 2; 
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
    	MoreListItemView child = data.get(position);
    	int type = getItemViewType(position);
        View vi = convertView;
        ArrowViewHolder arrowHolder = new ArrowViewHolder();
        SwitchViewHolder switchViewHolder = new SwitchViewHolder();
        EditTextViewHolder editTextViewHolder = new EditTextViewHolder();
        
        if( convertView == null ){
        	if( type == MoreListItemView.TYPE_ARROW_ITEM ){
        		vi = inflater.inflate(R.layout.list_row_arrow_child_manage_more_list, null);
        		arrowHolder.titleTextView = (TextView)vi.findViewById(R.id.title);
        		
        		vi.setTag(arrowHolder);
        	}else if( type == MoreListItemView.TYPE_SWITCH_ITEM ){
        		vi = inflater.inflate(R.layout.list_row_switch_child_manage_more_list, null);
        		
        		switchViewHolder.titleTextView = (TextView)vi.findViewById(R.id.title);
        		switchViewHolder.switchCmp = (Switch)vi.findViewById(R.id.switchCmp);
        		
        		vi.setTag(switchViewHolder);
        	}else if( type == MoreListItemView.TYPE_EDITTEXT_ITEM ){
        		vi = inflater.inflate(R.layout.list_row_edittext_child_manage_more_list, null);
        		
        		editTextViewHolder.titleTextView = (TextView)vi.findViewById(R.id.title);
        		editTextViewHolder.editText = (EditText)vi.findViewById(R.id.speedingLimitEditText);
        		
        		vi.setTag(editTextViewHolder);
        	}
        }else{
        	if( type == MoreListItemView.TYPE_ARROW_ITEM ){
        		arrowHolder = (ArrowViewHolder) convertView.getTag();
        	}else if( type == MoreListItemView.TYPE_SWITCH_ITEM ){
        		switchViewHolder = (SwitchViewHolder) convertView.getTag();
        	}else if( type == MoreListItemView.TYPE_EDITTEXT_ITEM ){
        		editTextViewHolder = (EditTextViewHolder) convertView.getTag();
        		
        	}
        }
        
        if( type == MoreListItemView.TYPE_ARROW_ITEM ){
    		arrowHolder.titleTextView.setText(child.getTitle());
    	}else if( type == MoreListItemView.TYPE_SWITCH_ITEM ){
    		switchViewHolder.titleTextView.setText(child.getTitle());
    		switchViewHolder.switchCmp.setChecked(child.getSwitchOn());
    	}else if( type == MoreListItemView.TYPE_EDITTEXT_ITEM ){
    		editTextViewHolder.titleTextView.setText(child.getTitle());
    		editTextViewHolder.editText.setText(child.getSpeedingLimit());
    	}
        
        return vi;
    }
    
    static class ArrowViewHolder {
    	TextView titleTextView;
    }
    
    static class SwitchViewHolder {
        TextView titleTextView;
        Switch switchCmp;
    }
    
    static class EditTextViewHolder {
    	TextView titleTextView;
        EditText editText;
    }

	public void appendItem(MoreListItemView item) {
		data.add(item);
		this.notifyDataSetChanged();
	}

	public void removeItem(MoreListItemView speedingLimitItem) {
		data.remove(speedingLimitItem);
		this.notifyDataSetChanged();
	}
 
}