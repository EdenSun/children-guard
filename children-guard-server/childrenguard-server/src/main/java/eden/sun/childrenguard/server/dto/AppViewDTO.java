package eden.sun.childrenguard.server.dto;

import java.util.Date;

public class AppViewDTO {
	private Integer id;

    private String name;

    private Boolean lockStatus;

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

	public Boolean getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(Boolean lockStatus) {
		this.lockStatus = lockStatus;
	}

}
