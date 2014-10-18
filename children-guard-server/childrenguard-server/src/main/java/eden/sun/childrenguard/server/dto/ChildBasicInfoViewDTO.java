package eden.sun.childrenguard.server.dto;


public class ChildBasicInfoViewDTO {
	private ChildViewDTO child;
	
	private String onlineStatus;
	
    private String mobile;

    private Double networkTrafficUsed;

    private String location;
    
    private Double speed;

	public ChildViewDTO getChild() {
		return child;
	}

	public void setChild(ChildViewDTO child) {
		this.child = child;
	}

	public String getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(String onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Double getNetworkTrafficUsed() {
		return networkTrafficUsed;
	}

	public void setNetworkTrafficUsed(Double networkTrafficUsed) {
		this.networkTrafficUsed = networkTrafficUsed;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

}
