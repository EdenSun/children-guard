package eden.sun.childrenguard.server.dto;

import java.util.Date;
import java.util.List;

public class PresetLockViewDTO {
	/**
	 * 预设锁定ID（同 person id )
	 */
	private Integer id;
	/**
	 * 预设锁定开关
	 */
	private Boolean presetOnOff;
	/**
	 * 锁定开始时间
	 */
	private Date startTime;

	/**
	 * 开始时间描述
	 */
	private String startTimeSummary;
	
	/**
	 * 锁定结束时间
	 */
	private Date endTime;
	
	/**
	 * 结束时间描述
	 */
	private String endTimeSummary;
	
	/**
	 * 锁定repeat设置
	 * 格式: [true,true,true,false,false,false,false]
	 * 七个值分别表示周一到周日是否进行锁定 (若在锁定开关打开情况下，示例中表示周一，周二，周三时进行锁定（开始到结束时间断）)
	 */
	private List<Boolean> repeat;
	
	/**
	 * repeat 描述
	 */
	private String repeatSummary;
	
	/**
	 * 拨出电话锁定开关
	 */
    private Boolean lockCallStatus;
    
    /**
     * 预设锁定的app列表
     */
    private List<AppViewDTO> appList;
    
    /**
     * app锁定 描述
     */
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
