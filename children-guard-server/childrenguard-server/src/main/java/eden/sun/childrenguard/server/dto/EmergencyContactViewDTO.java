package eden.sun.childrenguard.server.dto;

public class EmergencyContactViewDTO {
	/**
	 * ������ϵ��ID
	 */
	private Integer id;

	/**
	 * ������ϵ������
	 */
    private String name;

    /**
     * ������ϵ�˵绰
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
