package eden.sun.childrenguard.server.dto;

import java.util.Date;

public class ChildExtraInfoViewDTO {
	/**
	 * �������ݶ���id(��person id����һ��)
	 */
	private Integer id;

	/**
	 * ����
	 */
    private Double longitude;

    /**
     * γ��
     */
    private Double latitude;

    /**
     * ����������ʱ��
     */
    private Date locationUpdateTime;

    /**
     * �����ٶ�
     */
    private Double speed;

    /**
     * �ٶ�������ʱ��
     */
    private Date speedUpdateTime;

    /**
     * **δʹ��
     */
    private Double networkTrafficUsed;

    /**
     * **δʹ��
     */
    private Date networkTrafficUpdateTime;

    /**
     * **δʹ��
     */
    private String onlineStatus;
	
    /**
     * **δʹ��
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

