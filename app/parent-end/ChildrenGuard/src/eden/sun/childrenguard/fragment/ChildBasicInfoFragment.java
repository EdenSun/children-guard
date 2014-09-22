package eden.sun.childrenguard.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import eden.sun.childrenguard.R;

public class ChildBasicInfoFragment extends Fragment{
	private final String TAG = "ChildBasicInfoFragment";
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_child_basic_info, container, false);
    }
}
