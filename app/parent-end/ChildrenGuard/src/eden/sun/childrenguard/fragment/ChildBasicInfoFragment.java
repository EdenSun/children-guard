package eden.sun.childrenguard.fragment;

import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;

import eden.sun.childrenguard.R;
import eden.sun.childrenguard.activity.ChildrenManageActivity;
import eden.sun.childrenguard.errhandler.DefaultVolleyErrorHandler;
import eden.sun.childrenguard.helper.RequestHelper;
import eden.sun.childrenguard.server.dto.ChildBasicInfoViewDTO;
import eden.sun.childrenguard.server.dto.ChildExtraInfoViewDTO;
import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.util.Callback;
import eden.sun.childrenguard.util.Config;
import eden.sun.childrenguard.util.JSONUtil;
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
        
		loadChildBasicInfo();
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
		
	}

	private void loadChildBasicInfo() {
		String title = "Loading Data";
		String msg = "Please wait...";
		showProgressDialog(title,msg);
		
		String url = String.format(
				Config.BASE_URL_MVC + RequestURLConstants.URL_GET_CHILD_BASIC_INFO + "?childId=%1$s",  
				childId
				);  

		getRequestHelper().doGet(
			url,
			ChildBasicInfoFragment.this.getClass(),
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
			    	
			    	dismissProgressDialog();
				}
			}, 
			new DefaultVolleyErrorHandler(getActivity()));
	}
	
	
	public void doDeletePerson(final Callback successCallback) {
		String url = Config.BASE_URL_MVC + RequestURLConstants.URL_DELETE_CHILD;  

		Map<String,String> params = new HashMap<String,String>();
		params.put("childId", childId.toString());
		
		getRequestHelper().doPost(
			url,
			params,
			ChildBasicInfoFragment.this.getClass(),
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					final ViewDTO<ChildViewDTO> view = JSONUtil.getDeleteChildView(response);
							
					if( view.getMsg().equals(ViewDTO.MSG_SUCCESS) ){
						Callback.CallbackResult<ChildViewDTO> result = new Callback.CallbackResult<ChildViewDTO>();
						result.setSuccess(true);
						result.setData(view.getData());
						successCallback.execute(result);
					}else{
						String title = "Error";
						String msg = view.getInfo();
						String btnText = "OK";
						
						AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
							getActivity(),
							title,
							msg,
							btnText,
							new DialogInterface.OnClickListener() {
					            @Override
					            public void onClick(DialogInterface dialog, int which) {
					            	dialog.dismiss();
					            }
					        }
						);
						
						dialog.show();
					}
				}
			}, 
			new DefaultVolleyErrorHandler(getActivity()));
	}

}
