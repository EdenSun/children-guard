package com.soloappinfo.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapUtil {
	public static Bitmap resizeBitmap(String photoPath ,int targetW, int targetH) {
	    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
	    bmOptions.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(photoPath, bmOptions);
	    int photoW = bmOptions.outWidth;
	    int photoH = bmOptions.outHeight;

	    int scaleFactor = 1;
	    if ((targetW > 0) || (targetH > 0)) {
	            scaleFactor = Math.min(photoW/targetW, photoH/targetH);        
	    }

	    bmOptions.inJustDecodeBounds = false;
	    bmOptions.inSampleSize = scaleFactor;
	    bmOptions.inPurgeable = true;

	    return BitmapFactory.decodeFile(photoPath, bmOptions);            
	}
}
