package eden.sun.childrenguard.server.dto;

public class ChildSettingViewDTO {
	/**
	 * person 的设置对象ID（同person id）
	 */
	private Integer id;

	/**
	 * 电话功能锁定开关
	 */
    private Boolean lockCallsSwitch;

    /**
     * 未使用
     */
    private Boolean lockTextMessageSwitch;

    
    /**
     * 仅适用wifi开关
     */
    private Boolean wifiOnlySwitch;

    /**
     * 新安装app通知开关
     */
    private Boolean newAppNotificationSwitch;

    /**
     * 卸载app通知开关
     */
    private Boolean uninstallAppNotificationSwitch;

    /**
     * 速度超限制通知开关
     */
    private Boolean speedingNotificationSwitch;

    /**
     * 速度限制
     */
    private Integer speedingLimit;

    /**
     * app锁定/解锁通知开关
     */
    private Boolean appLockUnlockNotificationSwitch;

    /**
     * app 锁定密码
     */
    private String appLockPassword;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getLockCallsSwitch() {
		return lockCallsSwitch;
	}

	public void setLockCallsSwitch(Boolean lockCallsSwitch) {
		this.lockCallsSwitch = lockCallsSwitch;
	}

	public Boolean getLockTextMessageSwitch() {
		return lockTextMessageSwitch;
	}

	public void setLockTextMessageSwitch(Boolean lockTextMessageSwitch) {
		this.lockTextMessageSwitch = lockTextMessageSwitch;
	}

	public Boolean getWifiOnlySwitch() {
		return wifiOnlySwitch;
	}

	public void setWifiOnlySwitch(Boolean wifiOnlySwitch) {
		this.wifiOnlySwitch = wifiOnlySwitch;
	}

	public Boolean getNewAppNotificationSwitch() {
		return newAppNotificationSwitch;
	}

	public void setNewAppNotificationSwitch(Boolean newAppNotificationSwitch) {
		this.newAppNotificationSwitch = newAppNotificationSwitch;
	}

	public Boolean getUninstallAppNotificationSwitch() {
		return uninstallAppNotificationSwitch;
	}

	public void setUninstallAppNotificationSwitch(
			Boolean uninstallAppNotificationSwitch) {
		this.uninstallAppNotificationSwitch = uninstallAppNotificationSwitch;
	}

	public Boolean getSpeedingNotificationSwitch() {
		return speedingNotificationSwitch;
	}

	public void setSpeedingNotificationSwitch(Boolean speedingNotificationSwitch) {
		this.speedingNotificationSwitch = speedingNotificationSwitch;
	}

	public Integer getSpeedingLimit() {
		return speedingLimit;
	}

	public void setSpeedingLimit(Integer speedingLimit) {
		this.speedingLimit = speedingLimit;
	}

	public Boolean getAppLockUnlockNotificationSwitch() {
		return appLockUnlockNotificationSwitch;
	}

	public void setAppLockUnlockNotificationSwitch(
			Boolean appLockUnlockNotificationSwitch) {
		this.appLockUnlockNotificationSwitch = appLockUnlockNotificationSwitch;
	}

	public String getAppLockPassword() {
		return appLockPassword;
	}

	public void setAppLockPassword(String appLockPassword) {
		this.appLockPassword = appLockPassword;
	}
    
}
