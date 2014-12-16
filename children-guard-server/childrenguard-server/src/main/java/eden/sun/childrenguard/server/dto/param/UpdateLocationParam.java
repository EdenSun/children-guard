package eden.sun.childrenguard.server.dto.param;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class UpdateLocationParam {
	/**
	 * 纬度
	 */
	private Double latitude;
	/**
	 * 经度
	 */
	private Double longitude;
	
	/**
	 * 位置更新时间(yyyy-MM-dd HH:mm:ss)
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date LocationUpdateTime;
	
	/**
	 * 速度
	 */
	private Double speed;
	
	/**
	 * 速度更新时间(yyyy-MM-dd HH:mm:ss)
	 */
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
