package cn.wzz.atcrowdfunding.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import cn.wzz.atcrowdfunding.bean.Datas;
import cn.wzz.atcrowdfunding.bean.Role;

public interface RoleDao {

	List<Role> queryPageRoles(Map<String, Object> paramMap);

	int queryTotalSize(Map<String, Object> paramMap);

	int updateRole(Role role);

	void insertRloe(Role role);

	@Select("select * from t_role where id = #{id}")
	Role queryById(Integer id);

	int deleteRoleById(Integer id);

	int deleteRoles(Datas datas);

	@Select("select * from t_role")
	List<Role> queryAll();

	void insertRolePermissions(Map<String, Object> paramMap);

	void deleteRolePermissions(Map<String, Object> paramMap);

}
