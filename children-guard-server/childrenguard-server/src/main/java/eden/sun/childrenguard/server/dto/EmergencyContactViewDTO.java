package eden.sun.childrenguard.server.dto;

public class EmergencyContactViewDTO {
	/**
	 * 紧急联系人ID
	 */
	private Integer id;

	/**
	 * 紧急联系人姓名
	 */
    private String name;

    /**
     * 紧急联系人电话
     */
    private String phone;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
    
}
