package eden.sun.childrenguard.dto;

public class MoreListItemView {
	private String title;
	//arraw item or switch item
	private int type;	
	private Boolean switchOn;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Boolean getSwitchOn() {
		return switchOn;
	}
	public void setSwitchOn(Boolean switchOn) {
		this.switchOn = switchOn;
	}
	
	
}
