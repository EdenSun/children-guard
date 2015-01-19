package eden.sun.childrenguard.adapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.jraf.android.backport.switchwidget.Switch;

import android.app.Activity;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import eden.sun.childrenguard.R;
import eden.sun.childrenguard.dto.ScheduleLockListItemView;
import eden.sun.childrenguard.server.dto.PresetLockListItemViewDTO;
import eden.sun.childrenguard.server.dto.PresetLockViewDTO;

public class PresetLockListAdapter  extends ArrayAdapter<ScheduleLockListItemView> {
    private Activity context;
    private ArrayList<ScheduleLockListItemView> data;
    private static LayoutInflater inflater=null;
    private SparseBooleanArray mSelectedItemsIds;
    
    public PresetLockListAdapter(Activity context, int resourceId,ArrayList<ScheduleLockListItemView> data) {
    	super(context, resourceId, data);
        this.context = context;
        this.data=data;
        mSelectedItemsIds = new SparseBooleanArray();
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
 
    public int getCount() {
        return data.size();
    }
 
    public ScheduleLockListItemView getItem(int position) {
        return data.get(position);
    }
 
    public long getItemId(int position) {
        return position;
    }
    
    private class ViewHolder {
    	TextView periodTv;
    	TextView repeatTv;
    	Switch switchBtn;
	}
    
    public View getView(int position, View view, ViewGroup parent) {
    	final ViewHolder holder;
        if(view==null){
        	holder = new ViewHolder();
        	view = inflater.inflate(R.layout.list_row_preset_lock_list, null);
        	
			holder.periodTv = (TextView) view.findViewById(R.id.periodTv);
			holder.repeatTv = (TextView) view.findViewById(R.id.repeatTv);
			holder.switchBtn = (Switch) view.findViewById(R.id.switchBtn);
			view.setTag(holder);
        } else {
			holder = (ViewHolder) view.getTag();
		}
 
        ScheduleLockListItemView item = data.get(position);
 
        holder.periodTv.setText(getPeriodTimeSummary(item.getStartTime(),item.getEndTime()));
        holder.repeatTv.setText(item.getRepeatSummary());
        holder.switchBtn.setChecked(item.getPresetOnOff());
        
        return view;
    }

	private CharSequence getPeriodTimeSummary(Date startTime, Date endTime) {
		if( startTime == null || endTime == null ){
			return "";
		}
		
		DateFormat df = new SimpleDateFormat("HH:mm");
		
		return df.format(startTime) + "-" + df.format(endTime);
	}

	public void reloadData(List<PresetLockListItemViewDTO> msgList) {
		if( msgList == null ){
			return ;
		}
		this.data.clear();
		for(Iterator<PresetLockListItemViewDTO> it = msgList.iterator();it.hasNext();){
			PresetLockListItemViewDTO msg = it.next();
			addScheduleLockListItem(msg);
		}
		
		this.notifyDataSetChanged();
	}

	private void addScheduleLockListItem(PresetLockListItemViewDTO viewDto) {
		ScheduleLockListItemView itemView = trans2ScheduleLockListItemView(viewDto);
		
		data.add(itemView);
	}

	
	
	private ScheduleLockListItemView trans2ScheduleLockListItemView(
			PresetLockListItemViewDTO viewDto) {
		ScheduleLockListItemView itemView = new ScheduleLockListItemView();
		itemView.setId(viewDto.getId());
		itemView.setPresetOnOff(viewDto.getPresetOnOff());
		itemView.setRepeatSummary(viewDto.getRepeatSummary());
		itemView.setStartTime(viewDto.getStartTime());
		itemView.setEndTime(viewDto.getEndTime());
		return itemView;
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

	public void delete(PresetLockViewDTO deleted) {
		data.remove(trans2ScheduleLockListItemView(deleted));
		notifyDataSetChanged();
	}

	private ScheduleLockListItemView trans2ScheduleLockListItemView(PresetLockViewDTO deleted) {
		ScheduleLockListItemView view = new ScheduleLockListItemView();
		view.setEndTime(deleted.getEndTime());
		view.setId(deleted.getId());
		view.setPresetOnOff(deleted.getPresetOnOff());
		view.setRepeatSummary(deleted.getRepeatSummary());
		view.setStartTime(deleted.getStartTime());
		
		return view;
	}
	
}