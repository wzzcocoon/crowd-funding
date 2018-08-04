package cn.wzz.atcrowdfunding.bean;

/**用户类*/
public class User {
	
	private Integer id;
	private String loginacct;
	private String username;
	private String userpswd;
	private String email;
	private String createtime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLoginacct() {
		return loginacct;
	}

	public void setLoginacct(String loginacct) {
		this.loginacct = loginacct;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserpswd() {
		return userpswd;
	}

	public void setUserpswd(String userpswd) {
		this.userpswd = userpswd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", loginacct=" + loginacct + ", username=" + username + ", userpswd=" + userpswd
				+ ", email=" + email + ", createtime=" + createtime + "]";
	}
	
	
}
