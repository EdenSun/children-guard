package eden.sun.childrenguard.server.dto.param;

public class SettingApplyParam implements IParamObject {
	private Integer childId;
	
	private Boolean newAppNotificationSwitch;

    private Boolean uninstallAppNotificationSwitch;

    private Boolean speedingNotificationSwitch;

    private Boolean appLockUnlockNotificationSwitch;

	public Integer getChildId() {
		return childId;
	}

	public void setChildId(Integer childId) {
		this.childId = childId;
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

	public Boolean getAppLockUnlockNotificationSwitch() {
		return appLockUnlockNotificationSwitch;
	}

	public void setAppLockUnlockNotificationSwitch(
			Boolean appLockUnlockNotificationSwitch) {
		this.appLockUnlockNotificationSwitch = appLockUnlockNotificationSwitch;
	}
    
}
