package eden.sun.childrenguard.fragment;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import eden.sun.childrenguard.R;
import eden.sun.childrenguard.errhandler.DefaultVolleyErrorHandler;
import eden.sun.childrenguard.helper.MapHelper;
import eden.sun.childrenguard.helper.RequestHelper;
import eden.sun.childrenguard.server.dto.ChildBasicInfoViewDTO;
import eden.sun.childrenguard.server.dto.ChildExtraInfoViewDTO;
import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.util.BitmapCache;
import eden.sun.childrenguard.util.Callback;
import eden.sun.childrenguard.util.Config;
import eden.sun.childrenguard.util.JSONUtil;
import eden.sun.childrenguard.util.RequestURLConstants;
import eden.sun.childrenguard.util.ShareDataKey;
import eden.sun.childrenguard.util.UIUtil;

public class ChildBasicInfoFragment extends CommonFragment{
	private final String TAG = "ChildBasicInfoFragment";
	private Integer childId;
	/*private TextView mobileTextView;
	private TextView fullNameTextView;
	private TextView networkTrafficTextView;*/
	//private TextView locationTextView;
	private TextView speedTextView;
	private TextView nicknameTextView;
	private NetworkImageView locationMapImage;
	private RequestQueue mQueue;
    private ImageLoader imageLoader ;
    private Timer timer;
    
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startTimer();
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		View vi = inflater.inflate(R.layout.fragment_child_basic_info, container, false);
		Intent intent = this.getActivity().getIntent();
		childId = intent.getIntExtra("childId",0);
		
		mQueue = RequestHelper.getInstance(getActivity()).getImageQueue();
	    imageLoader = new ImageLoader(mQueue, new BitmapCache());
	    
		initComponent(vi);
        
		return vi;
    }
	
	private void initComponent(View vi) {
		//mobileTextView = (TextView)vi.findViewById(R.id.mobileTextView);
		nicknameTextView = (TextView)vi.findViewById(R.id.nicknameTextView);
		//fullNameTextView = (TextView)vi.findViewById(R.id.fullNameTextView);
		//networkTrafficTextView = (TextView)vi.findViewById(R.id.networkTrafficTextView);
		//locationTextView = (TextView)vi.findViewById(R.id.locationTextView);
		
		locationMapImage = (NetworkImageView)vi.findViewById(R.id.locationMapImage);
		
        /*locationTextView.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				showMapViewDialog();
			}
		});*/
		speedTextView = (TextView)vi.findViewById(R.id.speedTextView);
	}
	
	/*private void showMapViewDialog() {
		FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();  
        // Create and show the dialog.  
		MapViewDialogFragment newFragment  = new MapViewDialogFragment(0,0);
        
        newFragment.show(ft, "mapViewDialog");  		
	}*/
	

	
	@Override
	public void onResume() {
		super.onResume();
		loadChildBasicInfo();
	}

	private void loadChildBasicInfo() {
		/*String title = "Loading Data";
		String msg = "Please wait...";
		showProgressDialog(title,msg);*/
		String url = String.format(
				Config.getInstance().BASE_URL_MVC + RequestURLConstants.URL_GET_CHILD_BASIC_INFO + "?childId=%1$s",  
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
			    			//mobileTextView.setText(text);
			    			
			    			text = UIUtil.formatTextForView(child.getNickname());
			    			nicknameTextView.setText(text);
			    			
			    			/*String firstName = UIUtil.formatTextForView(child.getFirstName());
			    			String lastName = UIUtil.formatTextForView( child.getLastName());
			    			text = firstName + "," + lastName;
			    			fullNameTextView.setText(text);*/
			    		}
			    		
			    		if( childExtra != null && ( (childExtra.getLongitude()!=null && childExtra.getLatitude() !=null) || childExtra.getSpeed() != null ) ){
			    			/*String text = UIUtil.formatTextForView(childExtra.getNetworkTrafficUsed());
		    				networkTrafficTextView.setText(text + "Used");*/
			    			
		    				/*String text = UIUtil.formatTextForView(childExtra.getLocation());
		    				locationTextView.setText(text);*/
			    			
			    			locationMapImage.setImageUrl(MapHelper.getMapboxStaticMapUrl(childExtra.getLongitude(),childExtra.getLatitude()) , imageLoader);
			    			
		    				String text = UIUtil.formatTextForView(childExtra.getSpeed());
		    				speedTextView.setText(text + "km/s");
			    		}else{
		    				//networkTrafficTextView.setText("N/A Used");
		    				//locationTextView.setText("N/A");
		    				speedTextView.setText("N/A km/s");
			    		}
			    	}else{
			    		AlertDialog.Builder dialog = UIUtil.getErrorDialog(ChildBasicInfoFragment.this.getActivity(),view.getInfo());
			    		
						dialog.show();
			    	}
			    	
			    	//dismissProgressDialog();
				}
			}, 
			new DefaultVolleyErrorHandler(getActivity()));
	}
	
	
	public void doDeletePerson(final Callback successCallback) {
		String url = Config.getInstance().BASE_URL_MVC + RequestURLConstants.URL_DELETE_CHILD;  

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

	
	private void startTimer() {
    	if( timer == null ){
    		timer = new Timer();
    	}

    	timer.schedule(new RefreshTask(), 15*60*1000 ,15*60*1000);
    }
    
    public void cancelTimer() {
    	if( timer != null ){
    		timer.cancel();
    		timer = null;
    	}
    }
    
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		cancelTimer();
	}


	class RefreshTask extends TimerTask {
		@Override
		public void run() {
			/*getActivity().runOnUiThread(new Runnable(){

				@Override
				public void run() {
					Toast.makeText(getActivity(), "Rrrrrrrr......", Toast.LENGTH_SHORT).show();
				}
				
			});*/
			loadChildBasicInfo();
		}
		
	}
	
}
