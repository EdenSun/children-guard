package eden.sun.childrenguard.server.dto;


public class ChildBasicInfoViewDTO {
	/**
	 * person对象数据
	 */
	private ChildViewDTO child;
	
	/**
	 * 额外信息
	 */
	private ChildExtraInfoViewDTO extraInfo;

	public ChildViewDTO getChild() {
		return child;
	}

	public void setChild(ChildViewDTO child) {
		this.child = child;
	}

	public ChildExtraInfoViewDTO getExtraInfo() {
		return extraInfo;
	}

	public void setExtraInfo(ChildExtraInfoViewDTO extraInfo) {
		this.extraInfo = extraInfo;
	}
	
}
