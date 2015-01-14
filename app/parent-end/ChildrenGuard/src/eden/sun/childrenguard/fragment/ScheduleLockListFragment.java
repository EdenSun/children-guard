package eden.sun.childrenguard.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import eden.sun.childrenguard.R;

public class ScheduleLockListFragment extends CommonFragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View vi = inflater.inflate(R.layout.fragment_schedule_lock_list, container, false);
		
		return vi;
	}

}
