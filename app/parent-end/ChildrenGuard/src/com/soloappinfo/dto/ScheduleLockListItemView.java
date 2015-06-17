package com.soloappinfo.dto;

import java.util.Date;

public class ScheduleLockListItemView {
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
	 * 锁定结束时间
	 */
	private Date endTime;
	
	/**
	 * repeat 描述
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScheduleLockListItemView other = (ScheduleLockListItemView) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
