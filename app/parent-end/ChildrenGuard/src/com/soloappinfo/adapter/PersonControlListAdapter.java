package com.soloappinfo.adapter;

import java.util.ArrayList;
import java.util.List;

import org.jraf.android.backport.switchwidget.Switch;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.soloappinfo.R;
import com.soloappinfo.activity.PersonAppManageActivity;
import com.soloappinfo.dto.MoreListItemView;
import com.soloappinfo.helper.IApplyInterface;
import com.soloappinfo.util.Constants;
import com.soloappinfo.util.DataTypeUtil;

import eden.sun.childrenguard.server.dto.ChildSettingViewDTO;
import eden.sun.childrenguard.server.dto.param.ControlSettingApplyParam;

public class PersonControlListAdapter extends BaseAdapter{
	 
    private static final String TAG = "PersonControlListAdapter";
	private Activity context;
    private IApplyInterface applyControlSettingInterface;
    private List<MoreListItemView> data;
    private static LayoutInflater inflater=null;
    private Integer childId;
    //public ImageLoader imageLoader; 
 
    public PersonControlListAdapter(Activity context,IApplyInterface applyControlSettingInterface,Integer childId) {
		super();
		this.context = context;
		this.applyControlSettingInterface = applyControlSettingInterface;
		this.data = new ArrayList<MoreListItemView>();
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.childId = childId;
	}

	/*public PersonControlListAdapter(Activity context, ArrayList<MoreListItemView> data) {
        this.context = context;
        this.data=data;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }*/
 
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
        return 3; 
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
    	MoreListItemView childSetting = data.get(position);
    	int type = getItemViewType(position);
        View vi = convertView;
        ArrowViewHolder arrowHolder = new ArrowViewHolder();
        SwitchViewHolder switchViewHolder = new SwitchViewHolder();
        EditTextViewHolder editTextViewHolder = new EditTextViewHolder();
        final int finalPos = position;
        
        if( convertView == null ){
        	if( type == MoreListItemView.TYPE_ARROW_ITEM ){
        		vi = inflater.inflate(R.layout.list_row_arrow_child_manage_more_list, null);
        		arrowHolder.titleTextView = (TextView)vi.findViewById(R.id.title);
        		
        		vi.setTag(arrowHolder);
        	}else if( type == MoreListItemView.TYPE_SWITCH_ITEM ){
        		vi = inflater.inflate(R.layout.list_row_switch_child_manage_more_list, null);
        		
        		switchViewHolder.titleTextView = (TextView)vi.findViewById(R.id.title);
        		switchViewHolder.switchCmp = (Switch)vi.findViewById(R.id.switchCmp);
        		
        		switchViewHolder.switchCmp.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						//((ChildrenManageActivity)context).setConfigChanges(true);
						Switch curSwitch = (Switch)v;
						boolean isChecked = curSwitch.isChecked();
						MoreListItemView curSetting = data.get(finalPos);
						curSetting.setSwitchOn(isChecked);
						
						// apply setting
						ControlSettingApplyParam settingApplyParam = getControlSettingApplyParam(curSetting);
						
						applyControlSettingInterface.doApply(settingApplyParam);
					}

        		});
        		
        		vi.setTag(switchViewHolder);
        	}else if( type == MoreListItemView.TYPE_EDITTEXT_ITEM ){
        		vi = inflater.inflate(R.layout.list_row_edittext_child_manage_more_list, null);
        		
        		editTextViewHolder.titleTextView = (TextView)vi.findViewById(R.id.title);
        		editTextViewHolder.editText = (EditText)vi.findViewById(R.id.speedingLimitEditText);
        		
        		editTextViewHolder.editText.setOnFocusChangeListener(new OnFocusChangeListener(){

					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if( hasFocus == false){
							// when blur 
							//((ChildrenManageActivity)context).setConfigChanges(true);
							EditText editText = (EditText)v;
							MoreListItemView curSetting = data.get(finalPos);
							curSetting.setInputText(editText.getText().toString());
							
							// apply setting
							ControlSettingApplyParam settingApplyParam = getControlSettingApplyParam(curSetting);
							
							applyControlSettingInterface.doApply(settingApplyParam);
						}
					}
        			
        		});
        		
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
    		arrowHolder.titleTextView.setText(childSetting.getTitle());
    	}else if( type == MoreListItemView.TYPE_SWITCH_ITEM ){
    		switchViewHolder.titleTextView.setText(childSetting.getTitle());
    		switchViewHolder.switchCmp.setChecked(childSetting.getSwitchOn());
    	}else if( type == MoreListItemView.TYPE_EDITTEXT_ITEM ){
    		editTextViewHolder.titleTextView.setText(childSetting.getTitle());
    		editTextViewHolder.editText.setText(childSetting.getInputText());
    	}
        
        return vi;
    }
    
    private ControlSettingApplyParam getControlSettingApplyParam(
			MoreListItemView curSetting) {
    	if( curSetting == null ){
    		return null;
    	}
    	
    	ControlSettingApplyParam param = new ControlSettingApplyParam();
    	param.setChildId(childId);
    	
    	if( curSetting.getTitle().equals(Constants.TITLE_LOCK_CALLS) ){
    		param.setLockCallsSwitch(curSetting.getSwitchOn());
    	}else if( curSetting.getTitle().equals(Constants.TITLE_WIFI_ONLY) ){
    		param.setWifiOnlySwitch(curSetting.getSwitchOn());
    	}else if( curSetting.getTitle().equals(Constants.TITLE_SPEEDING_LIMIT) ){
    		try {
				Integer speedingLimit = Integer.parseInt(curSetting.getInputText());
				param.setSpeedingLimit(speedingLimit);
			} catch (NumberFormatException e) {
				Log.e(TAG,e.getMessage());
			}
    	}
    	
		return param;
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

	public void initData(ChildSettingViewDTO setting) {
		ArrayList<MoreListItemView> list = new ArrayList<MoreListItemView>();
		MoreListItemView view = null;
		
		String title = null;
		int type = 0;
		int settingType = 0;
		Boolean switchOn = null;
		String inputText = null;
		
		/*title = MoreListItemView.TITLE_MODITY_LOCK_PASSWORD;
		type = MoreListItemView.TYPE_ARROW_ITEM;
		switchOn = null;
		view = new MoreListItemView(title,type,ModifyLockPasswordActivity.class, switchOn);
		list.add(view);
		
		title = MoreListItemView.TITLE_EMERGENCY_CONTACTS;
		type = MoreListItemView.TYPE_ARROW_ITEM;
		switchOn = null;
		view = new MoreListItemView(title,type,EmergencyContactManageActivity.class,switchOn);
		list.add(view);*/

		title = Constants.TITLE_LOCK_CALLS;
		type = MoreListItemView.TYPE_SWITCH_ITEM;
		settingType = 1;
		switchOn = DataTypeUtil.getNonNullBoolean(setting.getLockCallsSwitch());
		view = new MoreListItemView(title,type,settingType,switchOn);
		list.add(view);
		
		
		title = Constants.TITLE_APP_LOCK_MANAGE;
		type = MoreListItemView.TYPE_ARROW_ITEM;
		switchOn = null;
		view = new MoreListItemView(title,type,PersonAppManageActivity.class, switchOn);
		list.add(view);
		
		
		title = Constants.TITLE_WIFI_ONLY;
		type = MoreListItemView.TYPE_SWITCH_ITEM;
		settingType = 3;
		switchOn = DataTypeUtil.getNonNullBoolean(setting.getWifiOnlySwitch());
		view = new MoreListItemView(title,type,settingType,switchOn);
		list.add(view);
		
		/*title = MoreListItemView.TITLE_NEW_APP_NOTIFICATION;
		type = MoreListItemView.TYPE_SWITCH_ITEM;
		settingType = 4;
		switchOn = DataTypeUtil.getNonNullBoolean(setting.getNewAppNotificationSwitch());
		view = new MoreListItemView(title,type,settingType,switchOn);
		list.add(view);

		title = MoreListItemView.TITLE_UNINSTALL_APP_NOTIFICATION;
		type = MoreListItemView.TYPE_SWITCH_ITEM;
		settingType = 5;
		switchOn = DataTypeUtil.getNonNullBoolean(setting.getUninstallAppNotificationSwitch());
		view = new MoreListItemView(title,type,settingType,switchOn);
		list.add(view);
		
		title = MoreListItemView.TITLE_LOCK_UNLOCK_NOTIFICATION;
		type = MoreListItemView.TYPE_SWITCH_ITEM;
		settingType = 8;
		switchOn = DataTypeUtil.getNonNullBoolean(setting.getAppLockUnlockNotificationSwitch());
		view = new MoreListItemView(title,type,settingType,switchOn);
		list.add(view);
		
		title = MoreListItemView.TITLE_SPEEDING_NOTIFICATION;
		type = MoreListItemView.TYPE_SWITCH_ITEM;
		settingType = 6;
		switchOn = DataTypeUtil.getNonNullBoolean(setting.getSpeedingNotificationSwitch());
		view = new MoreListItemView(title,type,settingType,switchOn);
		list.add(view);*/
		
		title = Constants.TITLE_SPEEDING_LIMIT;
		type = MoreListItemView.TYPE_EDITTEXT_ITEM;
		settingType = 7;
		switchOn = null;
		inputText = DataTypeUtil.int2String(setting.getSpeedingLimit(),"");
		view = new MoreListItemView(title,type,settingType,inputText);
		list.add(view);

		this.reloadData(list);
	}

	private void reloadData(ArrayList<MoreListItemView> list) {
		data = list;
		this.notifyDataSetChanged();
	}
 
}