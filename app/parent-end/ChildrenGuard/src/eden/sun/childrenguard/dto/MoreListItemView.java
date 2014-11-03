package eden.sun.childrenguard.dto;

public class MoreListItemView {
	public static final int TYPE_ARROW_ITEM = 0;
	public static final int TYPE_SWITCH_ITEM = 1;
	public static final int TYPE_EDITTEXT_ITEM = 2;
	public static final String TITLE_MODITY_LOCK_PASSWORD = "Modify Lock Password";
	public static final String TITLE_EMERGENCY_CONTACTS = "Emergency Contacts (calls only)";
	public static final String TITLE_LOCK_CALLS = "Lock Calls";
	public static final String TITLE_LOCK_TEXT_MESSAGING = "Lock Text Messaging";
	public static final String TITLE_WIFI_ONLY = "Wifi Only";
	public static final String TITLE_NEW_APP_NOTIFICATION = "New App Notification";
	public static final String TITLE_UNINSTALL_APP_NOTIFICATION = "Uninstall App Notification";
	public static final String TITLE_SPEEDING_NOTIFICATION = "Speeding Notification";
	public static final String TITLE_LOCK_UNLOCK_NOTIFICATION = "Lock/Unlock Notification";
	
	/*public static final int ITEM_TYPE_NOTIFY = 0;
	public static final int ITEM_TYPE_MODIFY_PASSWORD = 1;
	public static final int ITEM_TYPE_EXCEPTION_PHONE = 2;*/
	
	private String title;
	//arraw item or switch item
	private int type;
	private Class nextActivity;
	private Boolean switchOn;
	private Integer speedingLimit;
	
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

	public Integer getSpeedingLimit() {
		return speedingLimit;
	}

	public void setSpeedingLimit(Integer speedingLimit) {
		this.speedingLimit = speedingLimit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		MoreListItemView other = (MoreListItemView) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}
