package com.soloappinfo.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.soloappinfo.R;
import com.soloappinfo.dto.NotifyMailListItemView;

public class NotifyMailListAdapter extends BaseAdapter{

	private Activity context;
    private ArrayList<NotifyMailListItemView> data;
    private static LayoutInflater inflater=null;
 
    public NotifyMailListAdapter(Activity context, ArrayList<NotifyMailListItemView> data) {
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
    	
    	NotifyMailListItemView item = data.get(position);
        View vi = convertView;
        ViewHolder viewHolder = new ViewHolder();
        if( convertView == null ){
    		vi = inflater.inflate(R.layout.list_row_notify_mail_list, null);
    		viewHolder.nameTextView = (TextView)vi.findViewById(R.id.nameTextView);
    		viewHolder.mailTextView = (TextView)vi.findViewById(R.id.mailTextView);
    		
    		vi.setTag(viewHolder);
        }else{
        	viewHolder = (ViewHolder) convertView.getTag();
        }
        
        viewHolder.nameTextView.setText(item.getName());
        viewHolder.mailTextView.setText(item.getMail());
        
        return vi;
        
    }

    static class ViewHolder{
    	TextView nameTextView;
    	TextView mailTextView;
    }
}
