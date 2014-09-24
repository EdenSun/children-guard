package eden.sun.childrenguard.dto;

public class MoreListItemView {
	public static final int TYPE_ARROW_ITEM = 0;
	public static final int TYPE_SWITCH_ITEM = 1;
	
	/*public static final int ITEM_TYPE_NOTIFY = 0;
	public static final int ITEM_TYPE_MODIFY_PASSWORD = 1;
	public static final int ITEM_TYPE_EXCEPTION_PHONE = 2;*/
	
	private String title;
	//arraw item or switch item
	private int type;
	private Class nextActivity;
	private Boolean switchOn;
	
	public MoreListItemView() {
		super();
	}
	
	public MoreListItemView(String title, int type,Class nextActivity ,Boolean switchOn) {
		super();
		this.title = title;
		this.type = type;
		this.nextActivity = nextActivity;
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

	public Class getNextActivity() {
		return nextActivity;
	}

	public void setNextActivity(Class nextActivity) {
		this.nextActivity = nextActivity;
	}

	/*public int getItemType() {
		return itemType;
	}

	public void setItemType(int itemType) {
		this.itemType = itemType;
	}*/

}
