package eden.sun.childrenguard.server.dto;

public class RegisterViewDTO {
	/**
	 * 注册的 email
	 */
	private String email;
	/**
	 * 注册的用户的access token
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
