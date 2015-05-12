package cn.ibox.model;

public class RescTreeNode {
	public RescTreeNode() {
		super();
	}
	private String name;
	private int id;
	private Boolean isParent;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Boolean getIsParent() {
		return isParent;
	}
	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	
	
}
