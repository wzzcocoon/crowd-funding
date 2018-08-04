package cn.wzz.atcrowdfunding.bean;

import java.util.List;

/**
 * 数据包装类
 * @author 王子政
 */
public class Datas {
	
	private List<User> users;
	private List<Role> roles;
	private List<Integer> ids;
	

	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public List<Integer> getIds() {
		return ids;
	}
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	@Override
	public String toString() {
		return "UserDatas [users=" + users + "]";
	}
	
}
