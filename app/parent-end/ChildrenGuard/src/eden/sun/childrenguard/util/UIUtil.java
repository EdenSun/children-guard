package eden.sun.childrenguard.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class UIUtil {

	public static Toast getImageToast(Context context,String msg,int imageResId) {
		Toast toast = Toast.makeText(context, msg,Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		LinearLayout toastView = (LinearLayout) toast.getView();
		ImageView imageCodeProject = new ImageView(context);
		imageCodeProject.setImageResource(imageResId);
		toastView.addView(imageCodeProject, 0);
		return toast;
	}

	
	public static Toast getToast(Context context,String msg) {
		Toast toast = Toast.makeText(context, msg,Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		return toast;
	}


	public static AlertDialog.Builder getAlertDialogWithOneBtn(Activity context,
			String title, String msg,
			String btnText,
			android.content.DialogInterface.OnClickListener listener) {
		return new AlertDialog.Builder(context)
	        .setIcon(android.R.drawable.ic_dialog_alert)
	        .setTitle(title)
	        .setMessage(msg)
	        .setPositiveButton(btnText, listener);
	}
}
