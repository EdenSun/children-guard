package eden.sun.childrenguard.server.dto.param;

public class ChildAddParam {
	String mobile ;
	String firstName ;
	String lastName ;
	String nickname ;
	Integer relationshipId ;
	String parentAccessToken;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Integer getRelationshipId() {
		return relationshipId;
	}
	public void setRelationshipId(Integer relationshipId) {
		this.relationshipId = relationshipId;
	}
	public String getParentAccessToken() {
		return parentAccessToken;
	}
	public void setParentAccessToken(String parentAccessToken) {
		this.parentAccessToken = parentAccessToken;
	}
}
