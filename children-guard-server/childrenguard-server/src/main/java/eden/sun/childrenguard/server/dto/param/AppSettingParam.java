package eden.sun.childrenguard.server.dto.param;

public class AppSettingParam {
	private Integer appId;
	private boolean lockStatus;
	public Integer getAppId() {
		return appId;
	}
	public void setAppId(Integer appId) {
		this.appId = appId;
	}
	public boolean isLockStatus() {
		return lockStatus;
	}
	public void setLockStatus(boolean lockStatus) {
		this.lockStatus = lockStatus;
	}
	
}
