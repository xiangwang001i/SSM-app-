package cn.appInfo.entity;

public class App_Category {

	private Integer id;
	private String categoryCode;
	private String categoryName;
	private Integer parentId;
	private Integer createdBy;
	private String creationTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}
	public App_Category(Integer id, String categoryCode, String categoryName, Integer parentId, Integer createdBy,
			String creationTime) {
		super();
		this.id = id;
		this.categoryCode = categoryCode;
		this.categoryName = categoryName;
		this.parentId = parentId;
		this.createdBy = createdBy;
		this.creationTime = creationTime;
	}
	public App_Category() {
		super();
	}
	
}
