package eden.sun.childrenguard.server.dto;


public class RelationshipViewDTO {
	/**
	 * ��ϵID
	 */
	private Integer id;

	/**
	 * ��ϵ����(�� Father�� Mother ...)
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
