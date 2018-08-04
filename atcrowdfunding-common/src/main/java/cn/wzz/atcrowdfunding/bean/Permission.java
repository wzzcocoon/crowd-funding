package cn.wzz.atcrowdfunding.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 许可类
 * @author 王子政
 *
 */
public class Permission {
	
	private Integer id,pid;
	private String name,url,icon;
	//树的打开状态
	private boolean open = true;
	//节点的勾选状态
	private boolean checked = false;
	//准备空集合，可以防备空指针异常	
	private List<Permission> children = new ArrayList<Permission>();
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public List<Permission> getChildren() {
		return children;
	}
	public void setChildren(List<Permission> children) {
		this.children = children;
	}

	
}
