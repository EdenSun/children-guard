package eden.sun.childrenguard.server.dto;

import java.util.Date;

public class ChildInfoViewDTO {
	/**
	 * person id
	 */
	private Integer id;

	/**
	 * �ƶ��绰
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
     * person �ǳ�
     */
    private String nickname;

    /**
     * ����ʱ��
     */
    private Date activateTime;

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
     * �ٶ�
     */
    private Double speed;

    /**
     * �ض�������ʱ��
     */
    private Date speedUpdateTime;

    /**
     * δʹ��
     */
    private Double networkTrafficUsed;

    /**
     * δʹ��
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
