package com.soloappinfo.client.dto;

public class AppInfo {
	private String appName ;
	private String packageName ;
	private String versionName ;
	private int versonCode ;
	private boolean lockStatus;
	private boolean isSysApp;
	
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
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	public int getVersonCode() {
		return versonCode;
	}
	public void setVersonCode(int versonCode) {
		this.versonCode = versonCode;
	}
	
	public boolean isSysApp() {
		return isSysApp;
	}
	public void setSysApp(boolean isSysApp) {
		this.isSysApp = isSysApp;
	}
	public boolean isLockStatus() {
		return lockStatus;
	}
	public void setLockStatus(boolean lockStatus) {
		this.lockStatus = lockStatus;
	}
	@Override
	public String toString() {
		return "AppInfo [appName=" + appName + ", packageName=" + packageName
				+ ", versionName=" + versionName + ", versonCode=" + versonCode
				+ ", lockStatus=" + lockStatus + ", isSysApp=" + isSysApp + "]";
	}
	

}
