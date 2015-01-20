package eden.sun.childrenguard.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import eden.sun.childrenguard.fragment.ChildAppManageFragment;
import eden.sun.childrenguard.fragment.ChildBasicInfoFragment;
import eden.sun.childrenguard.fragment.ChildManageMoreFragment;
import eden.sun.childrenguard.fragment.PersonControlFragment;
import eden.sun.childrenguard.fragment.PresetLockListFragment;
import eden.sun.childrenguard.fragment.PushMessageListFragment;

public class AppSectionsPagerAdapter extends FragmentPagerAdapter {
	public static final int FRAGMENT_INDEX_INFO = 0;
	public static final int FRAGMENT_INDEX_CONTROL = 1;
	public static final int FRAGMENT_INDEX_SCHEDULE = 2;
	public static final int FRAGMENT_INDEX_SETTING = 3;
	private Fragment[] fragments ;
	
    public AppSectionsPagerAdapter(FragmentManager fm) {
    	super(fm);
        
        /*fragments.add(new ChildBasicInfoFragment());
        fragments.add(new ChildAppManageFragment());
        fragments.add(new ChildManageMoreFragment());*/
        fragments = new Fragment[4];
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case FRAGMENT_INDEX_INFO:
                // The first section of the app is the most interesting -- it offers
                // a launchpad into the other demonstrations in this example application.
            	if( fragments[FRAGMENT_INDEX_INFO] == null ){
            		fragments[FRAGMENT_INDEX_INFO] = new ChildBasicInfoFragment();
            	}
                return fragments[FRAGMENT_INDEX_INFO];
            case FRAGMENT_INDEX_CONTROL:
                // The first section of the app is the most interesting -- it offers
                // a launchpad into the other demonstrations in this example application.
            	if( fragments[FRAGMENT_INDEX_CONTROL] == null ){
            		fragments[FRAGMENT_INDEX_CONTROL] = new PersonControlFragment();
            	}
                return fragments[FRAGMENT_INDEX_CONTROL];
            case FRAGMENT_INDEX_SCHEDULE:
                // The first section of the app is the most interesting -- it offers
                // a launchpad into the other demonstrations in this example application.
            	if( fragments[FRAGMENT_INDEX_SCHEDULE] == null ){
            		fragments[FRAGMENT_INDEX_SCHEDULE] = new PresetLockListFragment();
            	}
                return fragments[FRAGMENT_INDEX_SCHEDULE];
            case FRAGMENT_INDEX_SETTING:
                // The other sections of the app are dummy placeholders.
            	if( fragments[FRAGMENT_INDEX_SETTING] == null ){
            		fragments[FRAGMENT_INDEX_SETTING] = new ChildManageMoreFragment();
            	}
                return fragments[FRAGMENT_INDEX_SETTING];
            default:
            	return null;
        }
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Section " + (position + 1);
    }
}

