package eden.sun.childrenguard.server.dto;

public class ChildLoginViewDTO {
	/**
	 * person µç»°
	 */
	private String mobile;
	/**
	 * person µÄ access token
	 */
	private String accessToken;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}
