package eden.sun.childrenguard.server.dto.param;

public class AppManageSettingParam {
	private Integer appId;
	private Boolean lockStatus;
	public Integer getAppId() {
		return appId;
	}
	public void setAppId(Integer appId) {
		this.appId = appId;
	}
	public Boolean getLockStatus() {
		return lockStatus;
	}
	public void setLockStatus(Boolean lockStatus) {
		this.lockStatus = lockStatus;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appId == null) ? 0 : appId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AppManageSettingParam other = (AppManageSettingParam) obj;
		if (appId == null) {
			if (other.appId != null)
				return false;
		} else if (!appId.equals(other.appId))
			return false;
		return true;
	}
	
}
