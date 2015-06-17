package com.soloappinfo.client.db.model;

public class PresetLockApp {
    private Integer id;

    private Integer presetLockId;

    private Integer appId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPresetLockId() {
        return presetLockId;
    }

    public void setPresetLockId(Integer presetLockId) {
        this.presetLockId = presetLockId;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }
}