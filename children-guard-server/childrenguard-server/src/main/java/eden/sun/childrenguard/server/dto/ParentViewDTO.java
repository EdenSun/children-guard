package eden.sun.childrenguard.server.dto;

import java.util.Date;

public class ParentViewDTO {
	/**
	 * parent����ID
	 */
	 private Integer id;

	 /**
	  * parent��email
	  */
    private String email;

    /**
     * parent��first name
     */
    private String firstName;

    /**
     * parent�� last name
     */
    private String lastName;

    /**
     * ��¼����
     */
    private String password;

    /**
     * ע��ʱ��
     */
    private Date createTime;

    /**
     * ����¼ʱ��
     */
    private Date lastLoginTime;

    /**
     * access token
     */
    private String accessToken;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
    
}
