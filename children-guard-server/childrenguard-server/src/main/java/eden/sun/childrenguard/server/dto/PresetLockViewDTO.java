package eden.sun.childrenguard.server.dto;

import java.util.Date;
import java.util.List;

public class PresetLockViewDTO {
	private Integer id;
	private Boolean presetOnOff;
	private Date startTime;
	private String startTimeSummary;
	private Date endTime;
	private String endTimeSummary;
	private List<Boolean> repeat;
	private String repeatSummary;
    private Boolean lockCallStatus;
    private List<AppViewDTO> appList;
    private String appLockSummary;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Boolean getPresetOnOff() {
		return presetOnOff;
	}
	public void setPresetOnOff(Boolean presetOnOff) {
		this.presetOnOff = presetOnOff;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public String getStartTimeSummary() {
		return startTimeSummary;
	}
	public void setStartTimeSummary(String startTimeSummary) {
		this.startTimeSummary = startTimeSummary;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getEndTimeSummary() {
		return endTimeSummary;
	}
	public void setEndTimeSummary(String endTimeSummary) {
		this.endTimeSummary = endTimeSummary;
	}
	public List<Boolean> getRepeat() {
		return repeat;
	}
	public void setRepeat(List<Boolean> repeat) {
		this.repeat = repeat;
	}
	public String getRepeatSummary() {
		return repeatSummary;
	}
	public void setRepeatSummary(String repeatSummary) {
		this.repeatSummary = repeatSummary;
	}
	public Boolean getLockCallStatus() {
		return lockCallStatus;
	}
	public void setLockCallStatus(Boolean lockCallStatus) {
		this.lockCallStatus = lockCallStatus;
	}
	public List<AppViewDTO> getAppList() {
		return appList;
	}
	public void setAppList(List<AppViewDTO> appList) {
		this.appList = appList;
	}
	public String getAppLockSummary() {
		return appLockSummary;
	}
	public void setAppLockSummary(String appLockSummary) {
		this.appLockSummary = appLockSummary;
	}
}
