package com.soloappinfo.client.util.gps;

import android.location.Location;

public interface GPSCallback {
	void onGPSUpdate(Location location);
}
