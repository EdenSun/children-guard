package com.soloappinfo.errhandler;

import android.app.Activity;
import android.app.AlertDialog;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.soloappinfo.helper.DialogHolder;
import com.soloappinfo.util.UIUtil;

public class DefaultVolleyErrorHandler implements Response.ErrorListener{
	private Activity context;
	
	public DefaultVolleyErrorHandler(Activity context) {
		super();
		this.context = context;
	}

	@Override
	public void onErrorResponse(VolleyError arg0) {
		((DialogHolder)context).dismissProgressDialog();
		AlertDialog.Builder dialog = UIUtil.getServerErrorDialog(context);
		
		dialog.show();
	}

}
