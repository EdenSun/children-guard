package eden.sun.childrenguard.server.dto;


public class RelationshipViewDTO {
	/**
	 * 关系ID
	 */
	private Integer id;

	/**
	 * 关系名称(如 Father， Mother ...)
	 */
	private String relationName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRelationName() {
		return relationName;
	}

	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}

}
