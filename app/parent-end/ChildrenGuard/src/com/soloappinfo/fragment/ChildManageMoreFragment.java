package com.soloappinfo.fragment;

import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.soloappinfo.R;
import com.soloappinfo.activity.ChildrenListActivity;
import com.soloappinfo.adapter.MoreListAdapter;
import com.soloappinfo.dto.MoreListItemView;
import com.soloappinfo.errhandler.DefaultVolleyErrorHandler;
import com.soloappinfo.helper.IApplyInterface;
import com.soloappinfo.util.Config;
import com.soloappinfo.util.JSONUtil;
import com.soloappinfo.util.RequestURLConstants;
import com.soloappinfo.util.ShareDataKey;
import com.soloappinfo.util.UIUtil;

import eden.sun.childrenguard.server.dto.ChildSettingViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.IParamObject;
import eden.sun.childrenguard.server.dto.param.SettingApplyParam;


public class ChildManageMoreFragment extends CommonFragment implements IApplyInterface{
	private Integer childId;
	protected static final String TAG = "ChildManageMoreFragment";

	private ListView moreList ;
	
	private MoreListAdapter moreListAdapter;
	private TextView nicknameTextView;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_child_manage_more, container, false);
        Intent intent = this.getActivity().getIntent();
		childId = intent.getIntExtra("childId",0);
		
		nicknameTextView = (TextView)v.findViewById(R.id.nicknameTextView);
		
        moreList = (ListView)v.findViewById(R.id.list);
        
        moreListAdapter = new MoreListAdapter(this.getActivity(),this,childId);
        moreList.setAdapter(moreListAdapter);
        moreList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ListView listView = (ListView)parent;
				MoreListItemView item = (MoreListItemView)listView.getItemAtPosition(position);
				Log.i(TAG,item.toString());
				
				if( item.getType() == MoreListItemView.TYPE_ARROW_ITEM ){
					Class nextStep = item.getNextActivity();
					Intent intent = new Intent(ChildManageMoreFragment.this.getActivity(),nextStep);
					
					intent.putExtra("childId", childId);
					ChildManageMoreFragment.this.getActivity().startActivity(intent);
				}/*else if( item.getType() == MoreListItemView.TYPE_SWITCH_ITEM ){
					if( item.getTitle().equals(MoreListItemView.TITLE_SPEEDING_NOTIFICATION) ){
						
						String title = MoreListItemView.TITLE_MODITY_LOCK_PASSWORD;
						int type = MoreListItemView.TYPE_EDITTEXT_ITEM;
						Boolean switchOn = null;
						MoreListItemView speedingLimitItem = new MoreListItemView(title,type,null, switchOn);
						if( item.getSwitchOn() ){
							moreListAdapter.appendItem(speedingLimitItem);
						}else{
							moreListAdapter.removeItem(speedingLimitItem);
						}
						
					}
				}*/
				
			}
			
		});
		
        nicknameTextView.setText(getStringShareData( ShareDataKey.CHILD_NICKNAME ));
        
        // load setting data
        //loadSettingData();
		return v;
    }

	private void loadSettingData() {
		String url = String.format(
				Config.getInstance().BASE_URL_MVC + RequestURLConstants.URL_LOAD_CHILD_SETTING + "?childId=%1$s",  
				childId);  

		getRequestHelper().doGet(
			url,
			ChildrenListActivity.class,
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
			    	final ViewDTO<ChildSettingViewDTO> view = JSONUtil.getLoadChildSettingView(response);
			    	
			    	if( view != null && view.getMsg().equals(ViewDTO.MSG_SUCCESS) ){
			    		if( view.getData() != null ){
			    			ChildManageMoreFragment.this.getMoreListAdapter().initData(view.getData());
			    		}
						
					}else{
						AlertDialog.Builder dialog = UIUtil.getErrorDialog(getActivity(),view.getInfo());
			    		
						dialog.show();
					}
					
				}
			}, 
			new DefaultVolleyErrorHandler(getActivity())
		);
		
	}

	public MoreListAdapter getMoreListAdapter() {
		return moreListAdapter;
	}

	@Override
	public void onResume() {
		// load setting data
        loadSettingData();
		super.onResume();
	}

	@Override
	public void doApply(IParamObject param) {
		SettingApplyParam settingApplyParam = (SettingApplyParam)param;
		
		String url = Config.getInstance().BASE_URL_MVC + RequestURLConstants.URL_APPLY_SETTING;  

		Map<String,String> params = new HashMap<String,String>();
		params.put("settingJson", JSONUtil.transSettingApplyParam2String(settingApplyParam));
		
		getRequestHelper().doPost(
			url,
			params,
			getActivity().getClass(),
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					final ViewDTO<Boolean> view = JSONUtil.getApplySettingView(response);
							
					if( view.getMsg().equals(ViewDTO.MSG_SUCCESS) ){
						Toast.makeText(getActivity(), "done", Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(getActivity(), "fail", Toast.LENGTH_SHORT).show();
					}
				}
			}, 
			new DefaultVolleyErrorHandler(getActivity()));
		
	}

}
