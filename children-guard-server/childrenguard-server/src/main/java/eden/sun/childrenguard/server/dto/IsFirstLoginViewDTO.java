package eden.sun.childrenguard.server.dto;

public class IsFirstLoginViewDTO {
	/**
	 * parent ��¼ mobile
	 */
	private String mobile;
	/**
	 * �Ƿ��ǵ�һ�ε�¼
	 */
	private boolean firstLogin;
	
	/**
	 * ����Э�飬����һ�ε�¼����ʾ����Э��
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
