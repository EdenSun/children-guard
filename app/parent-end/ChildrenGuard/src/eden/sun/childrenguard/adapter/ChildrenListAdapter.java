package eden.sun.childrenguard.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import eden.sun.childrenguard.R;
import eden.sun.childrenguard.dto.ChildrenListItemView;

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
        return position;
    }
 
    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if(convertView==null){
        	vi = inflater.inflate(R.layout.list_row, null);
        }
 
        TextView childNameTextView = (TextView)vi.findViewById(R.id.childName);
        TextView onlineStatusTextView = (TextView)vi.findViewById(R.id.onlineStatus);
        
        ChildrenListItemView child = data.get(position);
 
        // Setting all values in listview
  
        childNameTextView.setText(child.getChildName());
        onlineStatusTextView.setText(child.getOnlineStatus());
        return vi;
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