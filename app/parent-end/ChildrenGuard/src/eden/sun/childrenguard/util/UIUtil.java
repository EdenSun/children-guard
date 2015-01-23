package eden.sun.childrenguard.util;

import org.jraf.android.backport.switchwidget.Switch;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import eden.sun.childrenguard.activity.LoginActivity;
import eden.sun.childrenguard.dto.RelationshipItemView;

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
	
	public static AlertDialog.Builder getAlertDialogWithTwoBtn(Activity context,
			String title, String msg,
			String leftBtnText,
			String rightBtnText,
			android.content.DialogInterface.OnClickListener leftBtnListener,
			android.content.DialogInterface.OnClickListener rightBtnListener
			) {
		return new AlertDialog.Builder(context)
	        .setIcon(android.R.drawable.ic_dialog_alert)
	        .setTitle(title)
	        .setMessage(msg)
	        .setPositiveButton(leftBtnText, leftBtnListener)
	        .setNegativeButton(rightBtnText, rightBtnListener)
	        .setCancelable(false);
	}


	public static String getEditTextValue(EditText emailEditText) {
		if( emailEditText != null ){
			return emailEditText.getText().toString().trim();
		}
		return null;
	}


	public static Builder getErrorDialog(Activity context,String errorInfo) {
		String title = "Error";
		String msg = errorInfo;
		String btnText = "OK";
		
		AlertDialog.Builder dialog = getAlertDialogWithOneBtn(
			context,
			title,
			msg,
			btnText,
			new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	            	dialog.dismiss();
	            }
	        }
		);
		return dialog;
	}
	
	public static Builder getServerErrorDialog(Activity context) {
		String title = "Error";
		String msg = "Server error,please try later.";
		String btnText = "OK";
		
		AlertDialog.Builder dialog = getAlertDialogWithOneBtn(
			context,
			title,
			msg,
			btnText,
			new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	            	dialog.dismiss();
	            }
	        }
		);
		return dialog;
	}


	public static Builder getLegalInfoDialog(Activity context, String legalInfo,final Callback loginSuccessCallback) {
		String title = "Legal Infomation";
		String msg = legalInfo;
		String leftBtnText = "Agree";
		String rightBtnText = "Disagree";
		
		final LoginActivity finalContext = (LoginActivity)context;
		AlertDialog.Builder dialog = UIUtil.getAlertDialogWithTwoBtn(
			context,
			title,
			msg,
			leftBtnText,
			rightBtnText,
			new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	            	dialog.dismiss();
	            	
	            	loginSuccessCallback.execute(null);
	            }
	        },
	        new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	            	dialog.dismiss();
	            }
	        }
		);
		
		return dialog;
	}


	public static String getSpinnerValue(Spinner spinner) {
		if( spinner.getSelectedItem() != null ){
			return ((RelationshipItemView)spinner.getSelectedItem()).getId().toString();
		}
		
		return null;
	}


	public static String formatTextForView(Double value) {
		if( value == null ){
			return "N/A";
		}
		
		return value.toString();
	}


	public static String formatTextForView(String value) {
		if( value == null ){
			return "N/A";
		}
		
		return value;
	}


	public static boolean getSwitchValue(Switch lockCallSwitch) {
		if( lockCallSwitch == null ){
			return false;
		}
		
		return lockCallSwitch.isChecked();
	}
}
