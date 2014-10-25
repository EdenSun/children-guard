package eden.sun.childrenguard.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class RequestHelper {
	private static final String TAG = "RequestHelper";
	private RequestQueue mQueue ;  
	private volatile static RequestHelper requestHelper;
	 
	private RequestHelper() {
		Log.i(TAG, "Init RequestHelper.");
	}
	
	public static RequestHelper getInstance(Activity activity) {
		if (requestHelper == null) {
			synchronized (RequestHelper.class) {
				if (requestHelper == null) {
					requestHelper = new RequestHelper();
				}
			}
		}

		requestHelper.mQueue = Volley.newRequestQueue(activity);
		return requestHelper;
	}
	
	public void doGet(String url,Response.Listener successListener,Response.ErrorListener errListener){
		StringRequest stringRequest = new StringRequest(
			Method.GET,
			url,
			successListener, 
			errListener);
		mQueue.add(stringRequest);
		
	}

	public void doPost(
			String url,
			Map<String,String> params,
			Listener<String> successlistener,
			ErrorListener errorListener) {
		final Map<String,String> finalParams = params;
		StringRequest myReq = new StringRequest(
				Method.POST,
                url,
                successlistener,
		        errorListener) {
		
			protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
				return finalParams;
			};
		};
		
		mQueue.add(myReq);
		
	}
	
	/*public String getParameterUrl(String url,Map<String,String> param){
		if( param != null && param.size() > 0 ){
			url += "?";
			int paramCnt = 1;
			for(Iterator<Entry<String,String>> it = param.entrySet().iterator(); it.hasNext(); ){
				Entry<String,String> entry = it.next();
				if( it.hasNext() ){
					url += entry.getKey() + "=%" + paramCnt + "$s&";
				}else{
					url += entry.getKey() + "=%" + paramCnt + "$s";
				}
				paramCnt++;
			}
		}
		
		url = String.format(url,  
                num1,  
                num2);
		
		return uri;
	}*/
	
	
	
}
