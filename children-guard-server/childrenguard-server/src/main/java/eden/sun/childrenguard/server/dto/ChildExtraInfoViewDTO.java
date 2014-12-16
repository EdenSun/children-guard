package eden.sun.childrenguard.server.dto;

import java.util.Date;

public class ChildExtraInfoViewDTO {
	/**
	 * 额外数据对象id(与person id对象一样)
	 */
	private Integer id;

	/**
	 * 经度
	 */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 坐标最后更新时间
     */
    private Date locationUpdateTime;

    /**
     * 最新速度
     */
    private Double speed;

    /**
     * 速度最后更新时间
     */
    private Date speedUpdateTime;

    /**
     * **未使用
     */
    private Double networkTrafficUsed;

    /**
     * **未使用
     */
    private Date networkTrafficUpdateTime;

    /**
     * **未使用
     */
    private String onlineStatus;
	
    /**
     * **未使用
     */
    private String location;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Date getLocationUpdateTime() {
		return locationUpdateTime;
	}

	public void setLocationUpdateTime(Date locationUpdateTime) {
		this.locationUpdateTime = locationUpdateTime;
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

	public Double getNetworkTrafficUsed() {
		return networkTrafficUsed;
	}

	public void setNetworkTrafficUsed(Double networkTrafficUsed) {
		this.networkTrafficUsed = networkTrafficUsed;
	}

	public Date getNetworkTrafficUpdateTime() {
		return networkTrafficUpdateTime;
	}

	public void setNetworkTrafficUpdateTime(Date networkTrafficUpdateTime) {
		this.networkTrafficUpdateTime = networkTrafficUpdateTime;
	}

	public String getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(String onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
    
    
}

