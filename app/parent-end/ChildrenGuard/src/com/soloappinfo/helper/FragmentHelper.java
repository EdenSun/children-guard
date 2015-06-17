package com.soloappinfo.helper;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

public class FragmentHelper {

	public static void replace(FragmentManager fm, int framelayoutId,
			Fragment fragment) {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(framelayoutId, fragment);
        transaction.commit();		
	}

}
