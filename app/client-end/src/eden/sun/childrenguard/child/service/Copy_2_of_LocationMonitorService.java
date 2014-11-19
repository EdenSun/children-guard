package eden.sun.childrenguard.child.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import eden.sun.childrenguard.child.db.dao.ChildInfoDao;
import eden.sun.childrenguard.child.db.model.ChildInfo;
import eden.sun.childrenguard.child.util.Config;
import eden.sun.childrenguard.child.util.DateUtil;
import eden.sun.childrenguard.child.util.RequestHelper;
import eden.sun.childrenguard.child.util.RequestURLConstants;
import eden.sun.childrenguard.child.util.gps.GPSManager;

public class Copy_2_of_LocationMonitorService extends Service {
	public static final String TAG = "LocationMonitorService";
	private static final long LOCATION_UPLOAD_DELAY = 10000;
	private LocalBinder binder = new LocalBinder();
	private Timer timer;
	private ChildInfoDao dao ;
	private LocationManager locationManager;
	private LocationListener locationListener;
	//2000ms
    private static final long minTime = 2000;
    //最小变更距离 10m
    private static final float minDistance = 10;
    
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
        
        timer.schedule(task, LOCATION_UPLOAD_DELAY);
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
		locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locationListener = new GPSServiceListener();
        
        Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setSpeedRequired(true);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		
        String bestProvider = locationManager.getBestProvider(
				criteria, true);

		if (bestProvider != null && bestProvider.length() > 0) {
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, locationListener);
		}/* else {
			final List<String> providers = GPSManager.locationManager
					.getProviders(true);

			for (final String provider : providers) {
				GPSManager.locationManager.requestLocationUpdates(provider,
						GPSManager.gpsMinTime, GPSManager.gpsMinDistance,
						GPSManager.locationListener);
			}
		}*/
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, locationListener);
        
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
        
        if(locationManager != null && locationListener != null)
        {
            locationManager.removeUpdates(locationListener);
        }
	}

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}
	
	 public class LocalBinder extends Binder {
		public Copy_2_of_LocationMonitorService getService() {
			return Copy_2_of_LocationMonitorService.this;
		}
	}
	 
	public class GPSServiceListener implements LocationListener {

		private static final String tag = "GPSServiceListener";
		private static final float minAccuracyMeters = 35;
		private static final String hostUrl = "http://doandroid.info/gpsservice/position.php?";
		private static final String user = "huzhangyou";
		private static final String pass = "123456";
		private static final int duration = 10;
		private final DateFormat timestampFormat = new SimpleDateFormat(
				"yyyyMMddHHmmss");

		public int GPSCurrentStatus;

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			if (location != null) {
				if (location.hasAccuracy()
						&& location.getAccuracy() <= minAccuracyMeters) {
					// 获取时间参数,将时间一并Post到服务器端
					GregorianCalendar greg = new GregorianCalendar();
					TimeZone tz = greg.getTimeZone();
					int offset = tz.getOffset(System.currentTimeMillis());
					greg.add(Calendar.SECOND, (offset / 1000) * -1);
					StringBuffer strBuffer = new StringBuffer();
					strBuffer.append(hostUrl);
					strBuffer.append("user=");
					strBuffer.append(user);
					strBuffer.append("&pass=");
					strBuffer.append(pass);
					strBuffer.append("&Latitude=");
					strBuffer.append(location.getLatitude());
					strBuffer.append("&Longitude=");
					strBuffer.append(location.getLongitude());
					strBuffer.append("&Time=");
					strBuffer.append(timestampFormat.format(greg.getTime()));
					strBuffer.append("&Speed=");
					strBuffer.append(location.hasSpeed());
					doGet(strBuffer.toString());
					Log.v(tag, strBuffer.toString());
				}
			}
		}

		// 将数据通过get的方式发送到服务器,服务器可以根据这个数据进行跟踪用户的行走状态
		private void doGet(String string) {
			// TODO Auto-generated method stub
			//
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			GPSCurrentStatus = status;
		}

	}
}