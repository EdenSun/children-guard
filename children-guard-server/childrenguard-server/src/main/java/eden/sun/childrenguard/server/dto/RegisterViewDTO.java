package eden.sun.childrenguard.server.dto;

public class RegisterViewDTO {
	/**
	 * ע��� email
	 */
	private String email;
	/**
	 * ע����û���access token
	 */
	private String accessToken;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
}
