package eden.sun.childrenguard.server.dto.param;

import java.util.List;

public class SyncAppSettingParam {
	/**
	 * person id
	 */
	private Integer childId;
	
	/**
	 * app �����б�
	 */
	private List<AppSettingParam> appSettings;
	public Integer getChildId() {
		return childId;
	}
	public void setChildId(Integer childId) {
		this.childId = childId;
	}
	public List<AppSettingParam> getAppSettings() {
		return appSettings;
	}
	public void setAppSettings(List<AppSettingParam> appSettings) {
		this.appSettings = appSettings;
	}
	
}
