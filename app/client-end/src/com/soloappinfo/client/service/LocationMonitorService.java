package com.soloappinfo.client.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.soloappinfo.client.db.dao.ChildInfoDao;
import com.soloappinfo.client.db.model.ChildInfo;
import com.soloappinfo.client.util.Config;
import com.soloappinfo.client.util.DateUtil;
import com.soloappinfo.client.util.DeviceHelper;
import com.soloappinfo.client.util.RequestHelper;
import com.soloappinfo.client.util.RequestURLConstants;
import com.soloappinfo.client.util.gps.GPSHelper;

public class LocationMonitorService extends Service {
	public static final String TAG = "LocationMonitorService";
	private static final long LOCATION_UPLOAD_PERIOD = 30000;
	private LocalBinder binder = new LocalBinder();
	private Handler handler;
	private GPSHelper gpsHelper;
	private Timer timer;
	private ChildInfoDao dao ;
	
	@Override
	public void onCreate() {
		Log.d(TAG, "onCreate() executed");
		timer = new Timer(true);
		
        startGps();
        
        scheduleLocationUpload();
	}

	private void scheduleLocationUpload() {
        TimerTask task = new TimerTask() {  
           public void run() {  
        	   Log.i(TAG, "Now upload location info");
        	   ChildInfoDao childInfoDao = getChildDao();
        	   ChildInfo childInfo = childInfoDao.getChildInfo();
        	   if( childInfo != null ){
        		   if( childInfo.getLatitude() != null || childInfo.getLongitude() != null || childInfo.getLocationUpdateTime() != null 
        				   || childInfo.getSpeed() != null || childInfo.getSpeedUpdateTime() != null ){
        			   uploadLocation(childInfo);
        		   }
        	   }
        	   
        	   Log.i(TAG, "Upload location info --- done");
           }

        }; 
        
        timer.schedule(task, new Date(), LOCATION_UPLOAD_PERIOD);
	}

	
	private void uploadLocation(ChildInfo childInfo) {
		// upload location 
		if( childInfo != null ){
			String url = Config.BASE_URL_MVC + RequestURLConstants.URL_UPLOAD_LOCATION;  
			Map<String, String> params = getUploadLocationParam(childInfo);
			
			RequestHelper requestHelper = RequestHelper.getInstance(this);
			requestHelper.doPost(
				url,
				params,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.i(TAG, "Upload location success." + response);
					}
				}, 
				new Response.ErrorListener(){
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e(TAG, "Upload location fail: " + error.getMessage());
					}
				});
		}
		
	}
	
	private Map<String, String> getUploadLocationParam(ChildInfo childInfo) {
		Double latitude = childInfo.getLatitude();
		Double longitude = childInfo.getLongitude();
		Date locationUpdateTime = childInfo.getLocationUpdateTime();
		Double speed = childInfo.getSpeed();
		Date speedUpdateTime = childInfo.getSpeedUpdateTime();
		
		Map<String, String> param = new HashMap<String,String>();
		param.put("imei", DeviceHelper.getIMEI(this));
		if( latitude != null ){
			param.put("latitude", latitude.toString());
		}
		if( longitude != null ){
			param.put("longitude", longitude.toString());
		}
		if( locationUpdateTime != null && DateUtil.dateToString(locationUpdateTime) != null ){
			param.put("locationUpdateTime", DateUtil.dateToString(locationUpdateTime));
		}
		if( speed != null ){
			param.put("speed", speed.toString());
		}
		if( speedUpdateTime != null && DateUtil.dateToString(speedUpdateTime) != null ){
			param.put("speedUpdateTime", DateUtil.dateToString(speedUpdateTime));
		}
		
		return param;
	}

	private ChildInfoDao getChildDao() {
		if( dao == null ){
			return new ChildInfoDao(this);
		}
		return dao;
	}
	
	private void startGps() {
		handler = new Handler();
		gpsHelper = new GPSHelper(this,handler);
		gpsHelper.start();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStartCommand() executed");
		
		return START_STICKY; 
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy() executed");
		
        // Tell the user we stopped. 
        Toast.makeText(this, "local_service_stopped", Toast.LENGTH_SHORT).show(); 
        
        gpsHelper.stop();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}
	
	 public class LocalBinder extends Binder {
		public LocationMonitorService getService() {
			return LocationMonitorService.this;
		}
	}
}