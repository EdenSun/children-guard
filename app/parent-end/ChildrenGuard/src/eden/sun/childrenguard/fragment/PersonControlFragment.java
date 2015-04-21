package eden.sun.childrenguard.fragment;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

import eden.sun.childrenguard.R;
import eden.sun.childrenguard.activity.ChildrenListActivity;
import eden.sun.childrenguard.activity.ChildrenManageActivity;
import eden.sun.childrenguard.adapter.PersonControlListAdapter;
import eden.sun.childrenguard.dto.MoreListItemView;
import eden.sun.childrenguard.errhandler.DefaultVolleyErrorHandler;
import eden.sun.childrenguard.helper.IApplyInterface;
import eden.sun.childrenguard.helper.IPersonControlFragmentInterface;
import eden.sun.childrenguard.server.dto.ChildSettingViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.ControlSettingApplyParam;
import eden.sun.childrenguard.server.dto.param.IParamObject;
import eden.sun.childrenguard.util.Config;
import eden.sun.childrenguard.util.JSONUtil;
import eden.sun.childrenguard.util.RequestURLConstants;
import eden.sun.childrenguard.util.ShareDataKey;
import eden.sun.childrenguard.util.UIUtil;

public class PersonControlFragment extends CommonFragment implements IApplyInterface,IPersonControlFragmentInterface{
	private final String TAG = "PersonControlFragment";
	private Integer childId;
	
	private ListView moreList ;
	
	private PersonControlListAdapter personControlListAdapter;
	private TextView nicknameTextView;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_person_control, container, false);

		Intent intent = this.getActivity().getIntent();
		childId = intent.getIntExtra("childId",0);
		
		nicknameTextView = (TextView)v.findViewById(R.id.nicknameTextView);
		nicknameTextView.setText(getStringShareData( ShareDataKey.CHILD_NICKNAME ));

		moreList = (ListView)v.findViewById(R.id.list);
		
		moreList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ListView listView = (ListView)parent;
				MoreListItemView item = (MoreListItemView)listView.getItemAtPosition(position);
				Log.i(TAG,item.toString());
				
				if( item.getType() == MoreListItemView.TYPE_ARROW_ITEM ){
					Class nextStep = item.getNextActivity();
					Intent intent = new Intent(getActivity(),nextStep);
					
					intent.putExtra("childId", childId);
					startActivity(intent);
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
		
		
        personControlListAdapter = new PersonControlListAdapter(this.getActivity(),this,childId);
        moreList.setAdapter(personControlListAdapter);
        
		return v;
    }

	@Override
	public void loadSettingData() {
		String url = String.format(
				Config.BASE_URL_MVC + RequestURLConstants.URL_LOAD_CHILD_SETTING + "?childId=%1$s",  
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
			    			PersonControlFragment.this.getMoreListAdapter().initData(view.getData());
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
	
	public PersonControlListAdapter getMoreListAdapter() {
		return personControlListAdapter;
	}

	@Override
	public void onResume() {
		// load setting data
		//loadSettingData();
		super.onResume();
	}


	@Override
	public void doApply(IParamObject param) {
		Toast t = Toast.makeText(getActivity(), "updating...", 0);
		t.setGravity(Gravity.FILL_HORIZONTAL|Gravity.BOTTOM, 0, 0);
		t.show();
		
		ControlSettingApplyParam controlSettingApplyParam = (ControlSettingApplyParam)param;
		
		String url = Config.BASE_URL_MVC + RequestURLConstants.URL_APPLY_CONTROL_SETTING;  

		Map<String,String> params = new HashMap<String,String>();
		params.put("settingJson", JSONUtil.transControlSettingApplyParam2String(controlSettingApplyParam));
		
		getRequestHelper().doPost(
			url,
			params,
			getActivity().getClass(),
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					final ViewDTO<Boolean> view = JSONUtil.getApplySettingView(response);
							
					if( view.getMsg().equals(ViewDTO.MSG_SUCCESS) ){
						Toast t = Toast.makeText(getActivity(), "done", Toast.LENGTH_SHORT);
						t.setGravity(Gravity.FILL_HORIZONTAL|Gravity.BOTTOM, 0, 0);
						t.show();
					}else{
						Toast t = Toast.makeText(getActivity(), "fail", Toast.LENGTH_SHORT);
						t.setGravity(Gravity.FILL_HORIZONTAL|Gravity.BOTTOM, 0, 0);
						t.show();
					}
				}
			}, 
			new DefaultVolleyErrorHandler(getActivity()));
		
	}


	@Override
	public void onAttach(Activity activity) {
		((ChildrenManageActivity)activity).setPersonControlFragmentInterface(this);
		
		super.onAttach(activity);
	}
	
	
}
