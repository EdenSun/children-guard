package eden.sun.childrenguard.server.dto;


public class ChildBasicInfoViewDTO {
	/**
	 * person��������
	 */
	private ChildViewDTO child;
	
	/**
	 * ������Ϣ
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
