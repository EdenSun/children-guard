package eden.sun.childrenguard.child.util.gps;

import java.math.BigDecimal;
import java.math.RoundingMode;

import android.app.Service;
import android.location.Location;
import android.os.Handler;
import android.widget.Toast;

public class GPSHelper implements GPSCallback {
	public static final int TEXT_SIZE_SMALL = 15;
    public static final int TEXT_SIZE_LARGE = 80;
    public static final int DEFAULT_SPEED_LIMIT = 80;
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

		Toast.makeText(context, (latitude + "," + longitude), 2000).show();
		Toast.makeText(context, speedString + " " + unitString, 2000).show();
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

	public void onDestroy() {
		gpsManager.stopListening();
		gpsManager.setGPSCallback(null);

		gpsManager = null;
	}
}
