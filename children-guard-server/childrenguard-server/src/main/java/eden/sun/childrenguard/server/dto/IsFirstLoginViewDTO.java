package eden.sun.childrenguard.server.dto;

public class IsFirstLoginViewDTO {
	/**
	 * parent ��¼ email
	 */
	private String email;
	/**
	 * �Ƿ��ǵ�һ�ε�¼
	 */
	private boolean firstLogin;
	
	/**
	 * ����Э�飬����һ�ε�¼����ʾ����Э��
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
