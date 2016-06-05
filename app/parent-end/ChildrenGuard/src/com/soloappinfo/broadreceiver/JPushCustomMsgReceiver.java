package com.soloappinfo.broadreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

public class JPushCustomMsgReceiver extends BroadcastReceiver {
	private static final String TAG = "JPushCustomMsgReceiver";

	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		Log.d(TAG, "onReceive - " + intent.getAction());

		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
			/*String registionId = JPushInterface.getRegistrationID(context);
			if( registionId != null ){
				String imei = DeviceHelper.getIMEI(context);
				saveRegistionId(context,imei,registionId);
			}*/
		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent
				.getAction())) {
			Log.d(TAG, "�յ����Զ�����Ϣ����Ϣ�����ǣ�"
					+ bundle.getString(JPushInterface.EXTRA_MESSAGE));
			// �Զ�����Ϣ����չʾ��֪ͨ������ȫҪ������д����ȥ����
			
			Toast.makeText(context, bundle.getString(JPushInterface.EXTRA_MESSAGE) , 3000);
		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
				.getAction())) {
			System.out.println("�յ���֪ͨ");
			// �����������Щͳ�ƣ�������Щ��������
		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
				.getAction())) {
			System.out.println("�û��������֪ͨ");
			// ����������Լ�д����ȥ�����û���������Ϊ
			/*Intent i = new Intent(context, TestActivity.class); // �Զ���򿪵Ľ���
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);*/

		} else {
			Log.d(TAG, "Unhandled intent - " + intent.getAction());
		}
	}

	/*private void saveRegistionId(Context context, String imei,
			String registionId) {
		RequestHelper helper = RequestHelper.getInstance(context);	
		String url = Config.BASE_URL_MVC + RequestURLConstants.URL_SAVE_REGISTION_ID;

		Map<String,String> param = new HashMap<String,String>();
		param.put("imei", imei);
		param.put("registionId", registionId);
		helper.doPost(
			url,
			param,
			JPushCustomMsgReceiver.this.getClass(),
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					ViewDTO<Boolean> view = JSONUtil.getSaveRegistionIdView(response);
			    	
			    	if( view.getMsg().equals(ViewDTO.MSG_SUCCESS)){
			    		Log.i(TAG, "Save registion id success.");
			    	}else{
			    		Log.e(TAG, "Save registion id failure.");
			    	}
				}
			}, 
			new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					Log.e(TAG, error.getMessage(), error);
			}
		});
		
	}*/
}