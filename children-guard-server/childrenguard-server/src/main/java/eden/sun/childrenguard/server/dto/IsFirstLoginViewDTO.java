package eden.sun.childrenguard.server.dto;

public class IsFirstLoginViewDTO {
	/**
	 * parent 登录 mobile
	 */
	private String mobile;
	/**
	 * 是否是第一次登录
	 */
	private boolean firstLogin;
	
	/**
	 * 法律协议，若第一次登录，显示法律协议
	 */
	private String legalInfo;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public boolean isFirstLogin() {
		return firstLogin;
	}
	public void setFirstLogin(boolean firstLogin) {
		this.firstLogin = firstLogin;
	}
	public String getLegalInfo() {
		return legalInfo;
	}
	public void setLegalInfo(String legalInfo) {
		this.legalInfo = legalInfo;
	}
}
