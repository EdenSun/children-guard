package eden.sun.childrenguard.adapter;

import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import eden.sun.childrenguard.R;
import eden.sun.childrenguard.dto.RelationshipItemView;
import eden.sun.childrenguard.server.dto.RelationshipViewDTO;

public class RelationshipSpinnerAdapter extends BaseAdapter {
    private Activity context;
    private List<RelationshipItemView> data;
    private static LayoutInflater inflater=null;
 
    public RelationshipSpinnerAdapter(Activity context, List<RelationshipItemView> data) {
        this.context = context;
        this.data=data;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    
    
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		 View vi = convertView;
        if(convertView==null){
        	vi = inflater.inflate(R.layout.relationship_spinner_list_row, null);
        }

        TextView label = (TextView) vi.findViewById(R.id.label);
        
        RelationshipItemView relationship = data.get(position);
 
        label.setText(relationship.getRelationName());
        return vi;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if(convertView==null){
        	vi = inflater.inflate(android.R.layout.simple_spinner_item, null);
        }

        TextView label = (TextView) vi.findViewById(android.R.id.text1);
        
        RelationshipItemView relationship = data.get(position);
 
        label.setText(relationship.getRelationName());
        return vi;
    }

	public void reloadData(List<RelationshipViewDTO> relationshipViewDTOList) {
		if( relationshipViewDTOList == null || relationshipViewDTOList.size() == 0){
			return ;
		}
		this.data.clear();
		for(Iterator<RelationshipViewDTO> it = relationshipViewDTOList.iterator();it.hasNext();){
			RelationshipViewDTO relationship = it.next();
			addRelationshipItem(relationship);
		}
		
		this.notifyDataSetChanged();
	}

	private void addRelationshipItem(RelationshipViewDTO relationship) {
		RelationshipItemView view = new RelationshipItemView();
		
		view.setId( relationship.getId() );
		view.setRelationName(relationship.getRelationName());

		data.add(view);
	}
    
    
}