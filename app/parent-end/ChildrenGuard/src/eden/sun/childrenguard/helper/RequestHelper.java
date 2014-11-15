package eden.sun.childrenguard.helper;

import java.util.Map;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.RequestQueue.RequestFilter;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class RequestHelper {
	private static final String TAG = "RequestHelper";
	private RequestQueue mQueue ;  
	private volatile static RequestHelper requestHelper;

	private RequestHelper() {
		Log.i(TAG, "Init RequestHelper.");
	}
	
	public static RequestHelper getInstance(Context context) {
		if (requestHelper == null) {
			synchronized (RequestHelper.class) {
				if (requestHelper == null) {
					requestHelper = new RequestHelper();
				}
			}
		}

		requestHelper.mQueue = Volley.newRequestQueue(context);
		return requestHelper;
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
	
	
	public void doGet(String url,Object tag,Response.Listener successListener,Response.ErrorListener errListener){
		StringRequest stringRequest = new StringRequest(
			Method.GET,
			url,
			successListener, 
			errListener
			){
			
				@Override
				public RetryPolicy getRetryPolicy() {
					RetryPolicy retryPolicy = new DefaultRetryPolicy(5000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT); 
			        return retryPolicy; 
				};
			
		};
		
		stringRequest.setTag(tag);
		mQueue.add(stringRequest);
		
	}

	public void doPost(
			String url,
			Map<String,String> params,
			Object tag,
			Listener<String> successlistener,
			ErrorListener errorListener) {
		final Map<String,String> finalParams = params;
		StringRequest myReq = new StringRequest(
				Method.POST,
                url,
                successlistener,
		        errorListener) {
		
			@Override
			public RetryPolicy getRetryPolicy() {
				RetryPolicy retryPolicy = new DefaultRetryPolicy(5000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT); 
		        return retryPolicy; 
			};

			protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
				return finalParams;
			};
		};
		myReq.setTag(tag);
		mQueue.add(myReq);
		
	}

	public void cancelAll(final Object tag) {
		if (tag == null) {  
	        throw new IllegalArgumentException("Cannot cancelAll with a null tag");  
	    }  
		mQueue.cancelAll(new RequestFilter() {  
	        @Override  
	        public boolean apply(Request<?> request) {  
	            return request.getTag().equals(tag);  
	        }  
	    }); 
	}
}
