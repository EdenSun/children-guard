package eden.sun.childrenguard.server.dto;

public class IsFirstLoginViewDTO {
	/**
	 * parent 登录 email
	 */
	private String email;
	/**
	 * 是否是第一次登录
	 */
	private boolean firstLogin;
	
	/**
	 * 法律协议，若第一次登录，显示法律协议
	 */
	private String legalInfo;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
