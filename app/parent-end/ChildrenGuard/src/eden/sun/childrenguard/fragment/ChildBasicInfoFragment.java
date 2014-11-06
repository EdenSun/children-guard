package eden.sun.childrenguard.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import eden.sun.childrenguard.R;
import eden.sun.childrenguard.server.dto.ChildBasicInfoViewDTO;
import eden.sun.childrenguard.server.dto.ChildExtraInfoViewDTO;
import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.util.Config;
import eden.sun.childrenguard.util.JSONUtil;
import eden.sun.childrenguard.util.RequestHelper;
import eden.sun.childrenguard.util.RequestURLConstants;
import eden.sun.childrenguard.util.ShareDataKey;
import eden.sun.childrenguard.util.UIUtil;

public class ChildBasicInfoFragment extends CommonFragment{
	private final String TAG = "ChildBasicInfoFragment";
	private Integer childId;
	private TextView mobileTextView;
	private TextView fullNameTextView;
	private TextView networkTrafficTextView;
	private TextView locationTextView;
	private TextView speedTextView;
	private TextView nicknameTextView;
	

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		View vi = inflater.inflate(R.layout.fragment_child_basic_info, container, false);
		Intent intent = this.getActivity().getIntent();
		childId = intent.getIntExtra("childId",0);
		
		initComponent(vi);
        
		return vi;
    }
	

	private void initComponent(View vi) {
		mobileTextView = (TextView)vi.findViewById(R.id.mobileTextView);
		nicknameTextView = (TextView)vi.findViewById(R.id.nicknameTextView);
		fullNameTextView = (TextView)vi.findViewById(R.id.fullNameTextView);
		networkTrafficTextView = (TextView)vi.findViewById(R.id.networkTrafficTextView);
		locationTextView = (TextView)vi.findViewById(R.id.locationTextView);
		speedTextView = (TextView)vi.findViewById(R.id.speedTextView);
	}


	@Override
	public void onResume() {
		super.onResume();
		Log.i(TAG, "onresume");
		
		loadChildBasicInfo();
	}

	private void loadChildBasicInfo() {
		RequestHelper helper = getRequestHelper();
		String url = String.format(
				Config.BASE_URL_MVC + RequestURLConstants.URL_GET_CHILD_BASIC_INFO + "?childId=%1$s",  
				childId
				);  

		helper.doGet(
			url,
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					ViewDTO<ChildBasicInfoViewDTO> view = JSONUtil.getChildBasicInfoView(response);
			    	
			    	if( view.getMsg().equals(ViewDTO.MSG_SUCCESS)){
			    		putStringShareData( ShareDataKey.CHILD_NICKNAME ,view.getData().getChild().getNickname());
			    		
			    		ChildViewDTO child = view.getData().getChild();
			    		ChildExtraInfoViewDTO childExtra = view.getData().getExtraInfo();
			    		
			    		if( child != null ){
			    			String text = UIUtil.formatTextForView(child.getMobile());
			    			mobileTextView.setText(text);
			    			
			    			text = UIUtil.formatTextForView(child.getNickname());
			    			nicknameTextView.setText(text);
			    			
			    			String firstName = UIUtil.formatTextForView(child.getFirstName());
			    			String lastName = UIUtil.formatTextForView( child.getLastName());
			    			text = firstName + "," + lastName;
			    			fullNameTextView.setText(text);
			    		}
			    		
			    		if( childExtra != null ){
			    			String text = UIUtil.formatTextForView(childExtra.getNetworkTrafficUsed());
		    				networkTrafficTextView.setText(text + "Used");
			    			
		    				text = UIUtil.formatTextForView(childExtra.getLocation());
		    				locationTextView.setText(text);
			    			
		    				text = UIUtil.formatTextForView(childExtra.getSpeed());
		    				speedTextView.setText(text + "km/s");
			    		}else{
		    				networkTrafficTextView.setText("N/A Used");
		    				locationTextView.setText("N/A");
		    				speedTextView.setText("N/A km/s");
			    		}
			    	}else{
			    		AlertDialog.Builder dialog = UIUtil.getErrorDialog(ChildBasicInfoFragment.this.getActivity(),view.getInfo());
			    		
						dialog.show();
			    	}
			    	
				}
			}, 
			new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					Log.e("TAG", error.getMessage(), error);
			}
		});
		

	}

	@Override
	public void onStart() {
		super.onStart();
		Log.i(TAG, "onstart");
	}

}
