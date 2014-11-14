package eden.sun.childrenguard.adapter;

import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import eden.sun.childrenguard.R;
import eden.sun.childrenguard.dto.ExceptionPahoneListItemView;
import eden.sun.childrenguard.server.dto.EmergencyContactViewDTO;

public class ExceptionPhoneListAdapter extends BaseAdapter{
	private Activity context;
    private List<ExceptionPahoneListItemView> data;
    private static LayoutInflater inflater=null;
    //public ImageLoader imageLoader; 
 
    public ExceptionPhoneListAdapter(Activity context, List<ExceptionPahoneListItemView> data) {
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
    	ExceptionPahoneListItemView item = data.get(position);
        View vi = convertView;
        ViewHolder viewHolder = new ViewHolder();
        if( convertView == null ){
    		vi = inflater.inflate(R.layout.list_row_exception_phone_list, null);
    		viewHolder.nameTextView = (TextView)vi.findViewById(R.id.nameTextView);
    		viewHolder.phoneTextView = (TextView)vi.findViewById(R.id.phoneTextView);
    		
    		vi.setTag(viewHolder);
        }else{
        	viewHolder = (ViewHolder) convertView.getTag();
        }
        
        viewHolder.nameTextView.setText(item.getName());
        viewHolder.phoneTextView.setText(item.getPhone());
        
        return vi;
    }
    
    static class ViewHolder{
    	TextView phoneTextView;
    	TextView nameTextView;
    }
    
    public void reloadData(List<EmergencyContactViewDTO> contactList) {
		if( contactList == null || contactList.size() == 0){
			return ;
		}
		this.data.clear();
		for(Iterator<EmergencyContactViewDTO> it = contactList.iterator();it.hasNext();){
			EmergencyContactViewDTO contact = it.next();
			addItem(contact);
		}
		
		this.notifyDataSetChanged();
	}

    private void addItem(EmergencyContactViewDTO contact) {
    	ExceptionPahoneListItemView item = new ExceptionPahoneListItemView();
		item.setId(contact.getId());
		item.setName(contact.getName());
		item.setPhone(contact.getPhone());
		
		data.add(item);
	}
}
