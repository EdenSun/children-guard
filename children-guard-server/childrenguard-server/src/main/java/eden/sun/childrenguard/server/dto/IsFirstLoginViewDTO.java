package eden.sun.childrenguard.server.dto;

public class IsFirstLoginViewDTO {
	private String email;
	private boolean firstLogin;
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
