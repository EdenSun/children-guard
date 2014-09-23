package eden.sun.childrenguard.dto;

public class MoreListItemView {
	public static final int TYPE_ARROW_ITEM = 0;
	public static final int TYPE_SWITCH_ITEM = 1;
	
	private String title;
	//arraw item or switch item
	private int type;	
	private Boolean switchOn;
	
	public MoreListItemView() {
		super();
	}
	
	public MoreListItemView(String title, int type, Boolean switchOn) {
		super();
		this.title = title;
		this.type = type;
		this.switchOn = switchOn;
	}

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
