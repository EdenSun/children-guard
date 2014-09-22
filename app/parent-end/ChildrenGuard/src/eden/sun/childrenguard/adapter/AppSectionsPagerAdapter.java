package eden.sun.childrenguard.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import eden.sun.childrenguard.fragment.ChildAppManageFragment;
import eden.sun.childrenguard.fragment.ChildBasicInfoFragment;
import eden.sun.childrenguard.fragment.ChildManageMoreFragment;

public class AppSectionsPagerAdapter extends FragmentPagerAdapter {

    public AppSectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                // The first section of the app is the most interesting -- it offers
                // a launchpad into the other demonstrations in this example application.
                return new ChildBasicInfoFragment();
            case 1:
                // The first section of the app is the most interesting -- it offers
                // a launchpad into the other demonstrations in this example application.
                return new ChildAppManageFragment();
            default:
                // The other sections of the app are dummy placeholders.
                return new ChildManageMoreFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Section " + (position + 1);
    }
}

