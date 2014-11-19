package eden.sun.childrenguard.server.dto.param;

public class UploadApplicationInfoParam {
	private String appName;
	private String packageName;
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	@Override
	public String toString() {
		return "UploadApplicationInfoParam [appName=" + appName
				+ ", packageName=" + packageName + "]";
	}
	
	
}
