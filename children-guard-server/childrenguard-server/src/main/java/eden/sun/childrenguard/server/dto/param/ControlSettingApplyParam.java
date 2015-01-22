package eden.sun.childrenguard.server.dto.param;

public class ControlSettingApplyParam implements IParamObject {
	private Integer childId;
	private Boolean lockCallsSwitch;
	private Boolean wifiOnlySwitch;
	private Integer speedingLimit;
	public Integer getChildId() {
		return childId;
	}
	public void setChildId(Integer childId) {
		this.childId = childId;
	}
	public Boolean getLockCallsSwitch() {
		return lockCallsSwitch;
	}
	public void setLockCallsSwitch(Boolean lockCallsSwitch) {
		this.lockCallsSwitch = lockCallsSwitch;
	}
	public Boolean getWifiOnlySwitch() {
		return wifiOnlySwitch;
	}
	public void setWifiOnlySwitch(Boolean wifiOnlySwitch) {
		this.wifiOnlySwitch = wifiOnlySwitch;
	}
	public Integer getSpeedingLimit() {
		return speedingLimit;
	}
	public void setSpeedingLimit(Integer speedingLimit) {
		this.speedingLimit = speedingLimit;
	}
	
}
