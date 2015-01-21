package eden.sun.childrenguard.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import eden.sun.childrenguard.R;
import eden.sun.childrenguard.helper.RequestHelper;
import eden.sun.childrenguard.util.BitmapCache;

public class MapViewDialogFragment extends DialogFragment {
	private static final String TAG = "MapViewDialogFragment";
	private NetworkImageView imageView;
	private double lat;
	private double lon;
	private String mapImageUrl = "http://api.tiles.mapbox.com/v4/examples.map-zr0njcqy/pin-s-bus+f44(-73.99,40.70,13)/-83.99,40.70,13/500x300.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6IlhHVkZmaW8ifQ.hAMX5hSW-QnTeRCMAy9A8Q";
	
	
	public MapViewDialogFragment(double lat,double lon) {
		super();
		this.lat = lat;
		this.lon = lon;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater mInflater = LayoutInflater.from(getActivity());  
        View v = mInflater.inflate(R.layout.dialog_map_view , null);  
        
        initComponent(v);
        
        Dialog dialog = new AlertDialog.Builder(getActivity())  
                .setTitle("Location")  
                .setView(v)  
                .setNegativeButton("Close",  
                    new DialogInterface.OnClickListener() {  
                        public void onClick(DialogInterface dialog, int whichButton) {
                        	dialog.dismiss();
                        }
                    }
                )  
                .create();  
        dialog.setCancelable(false);
        return dialog;
	}
	
	
	private void initComponent(View v) {
		imageView = (NetworkImageView)v.findViewById(R.id.imageView);
		
		RequestQueue mQueue = RequestHelper.getInstance(getActivity()).getImageQueue();
        ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());
		imageView.setImageUrl(mapImageUrl , imageLoader);
	}
	
}
