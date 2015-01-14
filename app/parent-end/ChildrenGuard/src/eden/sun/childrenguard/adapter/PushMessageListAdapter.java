package eden.sun.childrenguard.adapter;

import java.util.ArrayList;
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
import eden.sun.childrenguard.dto.PushMessageListItemView;
import eden.sun.childrenguard.server.dto.PushMessageViewDTO;
import eden.sun.childrenguard.util.DateUtil;

public class PushMessageListAdapter extends BaseAdapter {
    private Activity context;
    private ArrayList<PushMessageListItemView> data;
    private static LayoutInflater inflater=null;
 
    public PushMessageListAdapter(Activity context, ArrayList<PushMessageListItemView> data) {
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
        	vi = inflater.inflate(R.layout.list_row_push_message_list, null);
        }
 
        TextView titleTv = (TextView)vi.findViewById(R.id.titleTv);
        TextView contentTv = (TextView)vi.findViewById(R.id.contentTv);
        TextView timeTv = (TextView)vi.findViewById(R.id.timeTv);
        
        PushMessageListItemView pushMsg = data.get(position);
 
        titleTv.setText(pushMsg.getTitle());
        contentTv.setText(pushMsg.getContent());
        timeTv.setText(pushMsg.getCreateTime());
        
        return vi;
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
	
}