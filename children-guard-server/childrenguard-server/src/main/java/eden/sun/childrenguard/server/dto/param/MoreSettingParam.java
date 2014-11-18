package eden.sun.childrenguard.server.dto.param;

public class MoreSettingParam {
	private Integer settingType;
	private Boolean switchOn;
	private String inputText;

	public Integer getSettingType() {
		return settingType;
	}
	public void setSettingType(Integer settingType) {
		this.settingType = settingType;
	}
	public Boolean getSwitchOn() {
		return switchOn;
	}
	public void setSwitchOn(Boolean switchOn) {
		this.switchOn = switchOn;
	}
	public String getInputText() {
		return inputText;
	}
	public void setInputText(String inputText) {
		this.inputText = inputText;
	}
}
