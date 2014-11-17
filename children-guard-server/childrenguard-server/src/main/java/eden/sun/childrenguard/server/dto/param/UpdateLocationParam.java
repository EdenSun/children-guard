package eden.sun.childrenguard.server.dto.param;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class UpdateLocationParam {
	private Double latitude;
	private Double longitude;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date LocationUpdateTime;
	private Double speed;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date speedUpdateTime;
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Date getLocationUpdateTime() {
		return LocationUpdateTime;
	}
	public void setLocationUpdateTime(Date locationUpdateTime) {
		LocationUpdateTime = locationUpdateTime;
	}
	public Double getSpeed() {
		return speed;
	}
	public void setSpeed(Double speed) {
		this.speed = speed;
	}
	public Date getSpeedUpdateTime() {
		return speedUpdateTime;
	}
	public void setSpeedUpdateTime(Date speedUpdateTime) {
		this.speedUpdateTime = speedUpdateTime;
	}
	@Override
	public String toString() {
		return "UpdateLocationParam [latitude=" + latitude + ", longitude="
				+ longitude + ", LocationUpdateTime=" + LocationUpdateTime
				+ ", speed=" + speed + ", speedUpdateTime=" + speedUpdateTime
				+ "]";
	}
	
}
