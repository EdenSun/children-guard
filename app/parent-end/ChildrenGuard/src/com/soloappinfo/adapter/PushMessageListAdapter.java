package com.soloappinfo.adapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.soloappinfo.R;
import com.soloappinfo.dto.PushMessageListItemView;
import com.soloappinfo.util.DateUtil;

import eden.sun.childrenguard.server.dto.PushMessageViewDTO;

public class PushMessageListAdapter extends ArrayAdapter<PushMessageListItemView> {
    private Activity context;
    private ArrayList<PushMessageListItemView> data;
    private static LayoutInflater inflater=null;
    private SparseBooleanArray mSelectedItemsIds;
    
    public PushMessageListAdapter(Activity context, int resourceId,ArrayList<PushMessageListItemView> data) {
    	super(context, resourceId, data);
        this.context = context;
        this.data=data;
        mSelectedItemsIds = new SparseBooleanArray();
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
 
    public int getCount() {
        return data.size();
    }
 
    public PushMessageListItemView getItem(int position) {
        return data.get(position);
    }
 
    public long getItemId(int position) {
        return position;
    }
    
    private class ViewHolder {
		TextView titleTv;
		TextView contentTv;
		TextView timeTv;
	}
    
    public View getView(int position, View view, ViewGroup parent) {
    	final ViewHolder holder;
        if(view==null){
        	holder = new ViewHolder();
        	view = inflater.inflate(R.layout.list_row_push_message_list, null);
        	
			holder.titleTv = (TextView) view.findViewById(R.id.titleTv);
			holder.contentTv = (TextView) view.findViewById(R.id.contentTv);
			holder.timeTv = (TextView) view.findViewById(R.id.timeTv);
			view.setTag(holder);
        } else {
			holder = (ViewHolder) view.getTag();
		}
 
        PushMessageListItemView pushMsg = data.get(position);
 
        holder.titleTv.setText(pushMsg.getTitle());
        holder.contentTv.setText(pushMsg.getContent());
        holder.timeTv.setText(pushMsg.getCreateTime());
        
        return view;
    }

	public void reloadData(List<PushMessageViewDTO> msgList) {
		if( msgList == null ){
			return ;
		}
		this.data.clear();
		for(Iterator<PushMessageViewDTO> it = msgList.iterator();it.hasNext();){
			PushMessageViewDTO msg = it.next();
			addPushMessageItem(msg);
		}
		
		this.notifyDataSetChanged();
	}

	private void addPushMessageItem(PushMessageViewDTO viewDto) {
		PushMessageListItemView itemView = new PushMessageListItemView();
		itemView.setTitle(viewDto.getTitle());
		itemView.setContent(viewDto.getContent());
		itemView.setCreateTime(DateUtil.dateToString( viewDto.getCreateTime() ) );
		itemView.setId(viewDto.getId());
		
		data.add(itemView);
	}

	public void delete(PushMessageViewDTO deletedMsg) {
		if( deletedMsg != null && data != null && data.size() > 0){
			for(Iterator<PushMessageListItemView> it=data.iterator();it.hasNext();){
				PushMessageListItemView item = (PushMessageListItemView)it.next();
				if( item.getId() != null && item.getId().equals(deletedMsg.getId())){
					it.remove();
				}
			}
			
			refresh();
		}
	}

	public void refresh() {
		this.notifyDataSetChanged();
	}
 
	
	public void toggleSelection(int position) {
		selectView(position, !mSelectedItemsIds.get(position));
	}
 
	public void removeSelection() {
		mSelectedItemsIds = new SparseBooleanArray();
		notifyDataSetChanged();
	}
 
	public void selectView(int position, boolean value) {
		if (value)
			mSelectedItemsIds.put(position, value);
		else
			mSelectedItemsIds.delete(position);
		notifyDataSetChanged();
	}
	
	public int getSelectedCount() {
		return mSelectedItemsIds.size();
	}
 
	public SparseBooleanArray getSelectedIds() {
		return mSelectedItemsIds;
	}
	
}