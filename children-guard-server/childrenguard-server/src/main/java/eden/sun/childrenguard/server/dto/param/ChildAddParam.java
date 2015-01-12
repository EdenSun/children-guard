package eden.sun.childrenguard.server.dto.param;

public class ChildAddParam {
	private String mobile ;
	private String name ;
	private String password;
	private String parentAccessToken;
	private String photoImage;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getParentAccessToken() {
		return parentAccessToken;
	}
	public void setParentAccessToken(String parentAccessToken) {
		this.parentAccessToken = parentAccessToken;
	}
	public String getPhotoImage() {
		return photoImage;
	}
	public void setPhotoImage(String photoImage) {
		this.photoImage = photoImage;
	}
	
}
