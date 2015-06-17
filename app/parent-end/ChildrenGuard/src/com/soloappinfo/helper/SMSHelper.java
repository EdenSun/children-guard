package com.soloappinfo.helper;

import java.util.List;

import android.telephony.SmsManager;

public class SMSHelper {
	public static void sendSms(String mobile, String content) {
		SmsManager smsManager = SmsManager.getDefault();  
		List<String> divideContents = smsManager.divideMessage(content);    
		for (String text : divideContents) {    
		    smsManager.sendTextMessage(mobile, null, text, null, null);    
		}				
	}
}
