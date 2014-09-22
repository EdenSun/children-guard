package eden.sun.childrenguard.dto;

public class AppManageListItemView {
	private String appName;
	private boolean isLock;
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public boolean isLock() {
		return isLock;
	}
	public void setLock(boolean isLock) {
		this.isLock = isLock;
	}
}
