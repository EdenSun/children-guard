package eden.sun.childrenguard.server.dto;

import java.util.Date;

public class ScheduleLockListItemViewDTO {
	private Integer id;
	/**
	 * Ԥ����������
	 */
	private Boolean presetOnOff;
	/**
	 * ������ʼʱ��
	 */
	private Date startTime;

	/**
	 * ��������ʱ��
	 */
	private Date endTime;
	
	/**
	 * repeat ����
	 */
	private String repeatSummary;

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

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getRepeatSummary() {
		return repeatSummary;
	}

	public void setRepeatSummary(String repeatSummary) {
		this.repeatSummary = repeatSummary;
	}
	
	
}
