package com.soloappinfo.adapter;

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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.soloappinfo.R;
import com.soloappinfo.dto.ChildrenListItemView;
import com.soloappinfo.helper.RequestHelper;
import com.soloappinfo.util.BitmapCache;
import com.soloappinfo.util.Config;
import com.soloappinfo.util.Constants;

import eden.sun.childrenguard.server.dto.ChildViewDTO;

public class ChildrenListAdapter extends BaseAdapter {
    private Activity context;
    private ArrayList<ChildrenListItemView> data;
    private static LayoutInflater inflater=null;
    //public ImageLoader imageLoader; 
 
    public ChildrenListAdapter(Activity context, ArrayList<ChildrenListItemView> data) {
        this.context = context;
        this.data=data;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //imageLoader=new ImageLoader(context.getApplicationContext());
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
        	vi = inflater.inflate(R.layout.list_row_children_list, null);
        }
 
        TextView childNameTextView = (TextView)vi.findViewById(R.id.childName);
        TextView mobileTextView = (TextView)vi.findViewById(R.id.mobile);
        TextView onlineState = (TextView)vi.findViewById(R.id.onlineState);
        TextView lockState = (TextView)vi.findViewById(R.id.lockState);
        final NetworkImageView photoImageView = (NetworkImageView)vi.findViewById(R.id.list_image);
        
        ChildrenListItemView child = data.get(position);
 
        // Setting all values in listview
        childNameTextView.setText(child.getNickname());
        mobileTextView.setText(child.getMobile());
        onlineState.setText(child.getOnlineState());
        lockState.setText(child.getLockState());
        
        
        RequestQueue mQueue = RequestHelper.getInstance(context).getImageQueue();
        ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());
    	if( child.getPhotoImage() != null ){
    		photoImageView.setImageUrl(Config.getInstance().BASE_URL + child.getPhotoImage(),imageLoader);
    		
    	}else{
    		photoImageView.setImageUrl(null,imageLoader);
    	}
    	
    	photoImageView.setDefaultImageResId(R.drawable.default_head);
    	photoImageView.setErrorImageResId(R.drawable.default_head);
        
        return vi;
    }

	public void reloadData(List<ChildViewDTO> childList) {
		if( childList == null ){
			return ;
		}
		this.data.clear();
		for(Iterator<ChildViewDTO> it = childList.iterator();it.hasNext();){
			ChildViewDTO child = it.next();
			addChildItem(child);
		}
		
		this.notifyDataSetChanged();
	}

	private void addChildItem(ChildViewDTO child) {
		ChildrenListItemView view = new ChildrenListItemView();
		view.setId(child.getId());
		view.setFirstName(child.getFirstName());
		view.setLastName(child.getLastName());
		view.setMobile(child.getMobile());
		view.setNickname(child.getNickname());
		if( child.isInLockState() == true ){
			view.setLockState(Constants.TEXT_LOCK);
		}else{
			view.setLockState(Constants.TEXT_UNLOCK);
		}
		
		if( child.isOnline() == true ){
			view.setOnlineState(Constants.TEXT_ONLINE);
		}else{
			view.setOnlineState(Constants.TEXT_OFFLINE);
		}
		
		view.setPhotoImage(child.getPhotoImage());
		
		data.add(view);
	}

	public void removeAll() {
		data.clear();
		notifyDataSetChanged();
	}
    
    
 
    /*public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row, null);
 
        TextView title = (TextView)vi.findViewById(R.id.title); // title
        TextView artist = (TextView)vi.findViewById(R.id.artist); // artist name
        TextView duration = (TextView)vi.findViewById(R.id.duration); // duration
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
 
        HashMap<String,String> song = new HashMap<String, String>();
        song = data.get(position);
 
        // Setting all values in listview
        title.setText(song.get(ChildrenListActivity.KEY_TITLE));
        artist.setText(song.get(ChildrenListActivity.KEY_ARTIST));
        duration.setText(song.get(ChildrenListActivity.KEY_DURATION));
        imageLoader.DisplayImage(song.get(ChildrenListActivity.KEY_THUMB_URL), thumb_image);
        return vi;
    }*/
}