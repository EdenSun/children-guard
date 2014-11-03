package eden.sun.childrenguard.server.model.generated;

public class ChildSetting {
    private Integer id;

    private Boolean lockCallsSwitch;

    private Boolean lockTextMessageSwitch;

    private Boolean wifiOnlySwitch;

    private Boolean newAppNotificationSwitch;

    private Boolean uninstallAppNotificationSwitch;

    private Boolean speedingNotificationSwitch;

    private Integer speedingLimit;

    private Boolean appLockUnlockNotificationSwitch;

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

    public void setUninstallAppNotificationSwitch(Boolean uninstallAppNotificationSwitch) {
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

    public void setAppLockUnlockNotificationSwitch(Boolean appLockUnlockNotificationSwitch) {
        this.appLockUnlockNotificationSwitch = appLockUnlockNotificationSwitch;
    }

    public String getAppLockPassword() {
        return appLockPassword;
    }

    public void setAppLockPassword(String appLockPassword) {
        this.appLockPassword = appLockPassword == null ? null : appLockPassword.trim();
    }
}