package cn.wzz.atcrowdfunding.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import cn.wzz.atcrowdfunding.bean.Permission;

public interface PermissionDao {

	public Permission queryRootPermission();

	public List<Permission> queryChildPermission(Integer id);

	@Select("select * from t_permission")
	public List<Permission> queryAll();

	public void insertPermission(Permission permission);

	@Select("select * from t_permission where id = #{id}")
	public Permission queryById(Integer id);

	public int updatePermission(Permission permission);

	public int deletePermission(Integer id);

	public List<Integer> queryPermissionidsByRoleid(Integer roleid);
}
