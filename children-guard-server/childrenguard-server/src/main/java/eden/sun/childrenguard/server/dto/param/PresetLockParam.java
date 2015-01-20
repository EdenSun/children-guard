package eden.sun.childrenguard.server.dto.param;

import java.util.Date;
import java.util.List;

public class PresetLockParam {
	private Boolean presetOnOff;
	private Date startTime;
	private Date endTime;
	private List<Integer> appIdList;
	private List<Boolean> reapeat;
	private Boolean lockCallStatus;
	private Integer childId;
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
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public List<Integer> getAppIdList() {
		return appIdList;
	}
	public void setAppIdList(List<Integer> appIdList) {
		this.appIdList = appIdList;
	}
	public List<Boolean> getReapeat() {
		return reapeat;
	}
	public void setReapeat(List<Boolean> reapeat) {
		this.reapeat = reapeat;
	}
	public Boolean getLockCallStatus() {
		return lockCallStatus;
	}
	public void setLockCallStatus(Boolean lockCallStatus) {
		this.lockCallStatus = lockCallStatus;
	}
	public Integer getChildId() {
		return childId;
	}
	public void setChildId(Integer childId) {
		this.childId = childId;
	}
}
