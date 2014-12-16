package eden.sun.childrenguard.server.dto;

import java.util.Date;

public class ChildInfoViewDTO {
	/**
	 * person id
	 */
	private Integer id;

	/**
	 * 移动电话
	 */
    private String mobile;

    /**
     * person first name
     */
    private String firstName;

    /**
     * person last name
     */
    private String lastName;

    /**
     * person 昵称
     */
    private String nickname;

    /**
     * 激活时间
     */
    private Date activateTime;

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
     * 速度
     */
    private Double speed;

    /**
     * 素对最后更新时间
     */
    private Date speedUpdateTime;

    /**
     * 未使用
     */
    private Double networkTrafficUsed;

    /**
     * 未使用
     */
    private Date networkTrafficUpdateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Date getActivateTime() {
		return activateTime;
	}

	public void setActivateTime(Date activateTime) {
		this.activateTime = activateTime;
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
    
}
