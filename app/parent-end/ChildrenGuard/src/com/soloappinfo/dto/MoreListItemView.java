package com.soloappinfo.dto;




public class MoreListItemView {
	public static final int TYPE_ARROW_ITEM = 0;
	public static final int TYPE_SWITCH_ITEM = 1;
	public static final int TYPE_EDITTEXT_ITEM = 2;
	
	/*public static final int ITEM_TYPE_NOTIFY = 0;
	public static final int ITEM_TYPE_MODIFY_PASSWORD = 1;
	public static final int ITEM_TYPE_EXCEPTION_PHONE = 2;*/
	
	private String title;
	//arraw item or switch item or input item
	private int type;
	private int settingType;
	private Class nextActivity;
	private Boolean switchOn;
	private String inputText;
	
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
	
	public MoreListItemView(String title, int type, Class nextActivity,
			Boolean switchOn, String inputText) {
		super();
		this.title = title;
		this.type = type;
		this.nextActivity = nextActivity;
		this.switchOn = switchOn;
		this.inputText = inputText;
	}
	
	public MoreListItemView(String title, int type,int settingType, Class nextActivity,
			Boolean switchOn) {
		super();
		this.title = title;
		this.type = type;
		this.nextActivity = nextActivity;
		this.switchOn = switchOn;
		this.settingType = settingType;
	}

	public MoreListItemView(String title, int type, int settingType,
			String inputText) {
		this.title = title;
		this.type = type;
		this.settingType = settingType;
		this.inputText = inputText;
	}

	public MoreListItemView(String title, int type, int settingType,
			Boolean switchOn) {
		this.title = title;
		this.type = type;
		this.settingType = settingType;
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

	public String getInputText() {
		return inputText;
	}

	public void setInputText(String inputText) {
		this.inputText = inputText;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + type;
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
		if (type != other.type)
			return false;
		return true;
	}

}
