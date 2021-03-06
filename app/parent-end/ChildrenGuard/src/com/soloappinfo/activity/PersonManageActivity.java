package com.soloappinfo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.soloappinfo.R;

public class PersonManageActivity extends CommonFragmentActivity {
	public static final String TAG = "PersonManageActivity";
	private ViewPager viewPager;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_manage);
       
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new ViewPagerAdapter());
    }
	
	public class ViewPagerAdapter extends FragmentPagerAdapter
	{
		private final int PAGE_COUNT = 5;
		private final String[] tabTitles = new String[]{"info","control","schedule","setting","notification"};

	    public ViewPagerAdapter() {
	        super(getSupportFragmentManager());
	    }

	    @Override
	    public int getCount() {
	        return PAGE_COUNT;
	    }

	    @Override
	    public Fragment getItem(int position) {
	    	/*switch(position){
	    	case 0:
	    		return new ChildBasicInfoFragment();
	    	case 1:
	    		return new ChildBasicInfoFragment();
	    	case 2:
	    		return new PresetLockListFragment();
	    	case 3:
	    		return new ChildManageMoreFragment();
	    	case 4:
	    		return new PushMessageListFragment();
	    	}*/
	    	
	    	return null;
	    }
	    
	    @Override
		public CharSequence getPageTitle(int position) {
	    	return tabTitles[position];
		}
	}
	

}
