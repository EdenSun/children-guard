package eden.sun.childrenguard.child.util.gps;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import android.app.Service;
import android.location.Location;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import eden.sun.childrenguard.child.db.dao.ChildInfoDao;
import eden.sun.childrenguard.child.db.model.ChildInfo;

public class GPSHelper implements GPSCallback {
	private static final String TAG = "GPSHelper";
    public static final int HOUR_MULTIPLIER = 3600;
    public static final double UNIT_MULTIPLIERS = 0.001;
    
	private Service context;
	private Handler handler;
	private GPSManager gpsManager;

	public GPSHelper(Service context,Handler handler) {
		super();
		this.context = context;
		this.handler = handler;
	}
	
	public void start(){
		gpsManager = new GPSManager();
		gpsManager.startListening(context);
		gpsManager.setGPSCallback(this);
	}

	@Override
	public void onGPSUpdate(Location location) {
		final double latitude = location.getLatitude();
		final double longitude = location.getLongitude();
		float speed = location.getSpeed();

		final String speedString = String
				.valueOf(roundDecimal(convertSpeed(speed), 2));
		final String unitString = "km/h";

		// save coordinate and speed
		ChildInfoDao dao = new ChildInfoDao(context);
		Date now = new Date();
		
		ChildInfo childInfo = dao.getChildInfo();
		childInfo.setSpeed(Double.valueOf(speed));
		childInfo.setSpeedUpdateTime(now);
		
		childInfo.setLatitude(latitude);
		childInfo.setLongitude(longitude);
		childInfo.setLocationUpdateTime(now);
		
		dao.update(childInfo);
		
		Log.i(TAG, (latitude + "," + longitude) );
		Log.i(TAG, speedString + " " + unitString);
		Toast.makeText(context, "location:" + latitude + "," + longitude , 1000).show();
		Toast.makeText(context, "speed:" + speed, 1000).show();
	}
	
	private double convertSpeed(double speed) {
		return ((speed * HOUR_MULTIPLIER) * UNIT_MULTIPLIERS);
	}
	 
	private double roundDecimal(double value, final int decimalPlace) {
		BigDecimal bd = new BigDecimal(value);

		bd = bd.setScale(decimalPlace, RoundingMode.HALF_UP);
		value = bd.doubleValue();

		return value;
	}

	public void stop() {
		gpsManager.stopListening();
		gpsManager.setGPSCallback(null);

		gpsManager = null;
	}
}
