package com.soloappinfo.adapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.soloappinfo.R;
import com.soloappinfo.dto.ExceptionPhoneListItemView;

import eden.sun.childrenguard.server.dto.EmergencyContactViewDTO;

public class ExceptionPhoneListAdapter extends ArrayAdapter<ExceptionPhoneListItemView>{
	private Activity context;
    private List<ExceptionPhoneListItemView> data;
    private static LayoutInflater inflater=null;
    //public ImageLoader imageLoader; 
    
    public ExceptionPhoneListAdapter(Activity context, int resourceId,ArrayList<ExceptionPhoneListItemView> data) {
    	super(context, resourceId, data);
        this.context = context;
        this.data=data;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
 
    public int getCount() {
        return data.size();
    }
 
    public ExceptionPhoneListItemView getItem(int position) {
        return data.get(position);
    }
 
    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
    	ExceptionPhoneListItemView item = data.get(position);
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
    	ExceptionPhoneListItemView item = new ExceptionPhoneListItemView();
		item.setId(contact.getId());
		item.setName(contact.getName());
		item.setPhone(contact.getPhone());
		
		data.add(item);
	}


	public void deleteById(Integer emergencyContactId) {
		if( data != null ){
			for(Iterator<ExceptionPhoneListItemView> it = data.iterator();it.hasNext();){
				ExceptionPhoneListItemView item = it.next();
				if(item.getId() != null && item.getId().equals(emergencyContactId) ){
					it.remove();
				}
			}
		}
		this.notifyDataSetChanged();
	}
	
}
