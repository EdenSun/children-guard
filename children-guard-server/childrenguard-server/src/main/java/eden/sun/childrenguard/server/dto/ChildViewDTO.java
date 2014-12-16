package eden.sun.childrenguard.server.dto;

import java.util.Date;


public class ChildViewDTO {
	/**
	 * person id
	 */
	private Integer id;

	/**
	 * person �ƶ��绰
	 */
    private String mobile;

    /**
     * person first name
     */
    private String firstName;

    /**
     * person last name
     */
    private String lastName;
    
    /**
     * person �ǳ�
     */
    private String nickname;
    
    /**
     * ����ʱ��
     */
    private Date activateTime;
    
    /**
     * �û�ͷ��
     */
    private String photoImage;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public Date getActivateTime() {
		return activateTime;
	}

	public void setActivateTime(Date activateTime) {
		this.activateTime = activateTime;
	}

	public String getPhotoImage() {
		return photoImage;
	}

	public void setPhotoImage(String photoImage) {
		this.photoImage = photoImage;
	}

}
