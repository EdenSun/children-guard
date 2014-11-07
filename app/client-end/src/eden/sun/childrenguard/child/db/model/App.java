package eden.sun.childrenguard.child.db.model;

public class App {
	private Integer id;

    private String name;

    private Boolean lockStatus;
    
    private String packageName;

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

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
    
}
