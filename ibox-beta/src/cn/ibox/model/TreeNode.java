package cn.ibox.model;

public class TreeNode {
	private String name;
	private String dirId;
	private String pid;
	private boolean async;
	private String isParent;
	private String icon;
	public String Icon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}


	

	public String getIcon() {
		return icon;
	}

	public String getName() {
		return name;
	}

	public String getIsParent() {
		return isParent;
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public boolean isAsync() {
		return async;
	}
	public void setAsync(boolean async) {
		this.async = async;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDirId() {
		return dirId;
	}
	public void setDirId(String dirId) {
		this.dirId = dirId;
	}

}
