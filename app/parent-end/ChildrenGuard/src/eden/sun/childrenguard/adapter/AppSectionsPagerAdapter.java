package eden.sun.childrenguard.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import eden.sun.childrenguard.fragment.ChildAppManageFragment;
import eden.sun.childrenguard.fragment.ChildBasicInfoFragment;
import eden.sun.childrenguard.fragment.ChildManageMoreFragment;

public class AppSectionsPagerAdapter extends FragmentPagerAdapter {
	public static final int FRAGMENT_INDEX_APP_MANAGE = 1;
	public static final int FRAGMENT_INDEX_MORE_MANAGE = 2;
	private Fragment[] fragments ;
	
    public AppSectionsPagerAdapter(FragmentManager fm) {
        super(fm);
        
        /*fragments.add(new ChildBasicInfoFragment());
        fragments.add(new ChildAppManageFragment());
        fragments.add(new ChildManageMoreFragment());*/
        fragments = new Fragment[3];
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                // The first section of the app is the most interesting -- it offers
                // a launchpad into the other demonstrations in this example application.
            	if( fragments[0] == null ){
            		fragments[0] = new ChildBasicInfoFragment();
            	}
                return fragments[0];
            case 1:
                // The first section of the app is the most interesting -- it offers
                // a launchpad into the other demonstrations in this example application.
            	if( fragments[1] == null ){
            		fragments[1] = new ChildAppManageFragment();
            	}
                return fragments[1];
            default:
                // The other sections of the app are dummy placeholders.
            	if( fragments[2] == null ){
            		fragments[2] = new ChildManageMoreFragment();
            	}
                return fragments[2];
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

