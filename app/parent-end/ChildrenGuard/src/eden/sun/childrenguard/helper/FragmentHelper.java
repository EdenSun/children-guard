package eden.sun.childrenguard.helper;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import eden.sun.childrenguard.R;

public class FragmentHelper {

	public static void replace(FragmentManager fm, int framelayoutId,
			Fragment fragment) {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(framelayoutId, fragment);
        transaction.commit();		
	}

}
