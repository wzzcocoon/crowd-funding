package cn.wzz.atcrowdfunding.service;

import java.util.List;
import java.util.Map;

import cn.wzz.atcrowdfunding.bean.Datas;
import cn.wzz.atcrowdfunding.bean.Role;

public interface RoleService {

	/**查询分页的后的角色*/
	List<Role> queryPageRoles(Map<String, Object> paramMap);

	/**查询总共的记录数*/
	int queryTotalSize(Map<String, Object> paramMap);

	/**更新一个角色的信息 （参数中传递的是要修改的信息）*/
	int updateRole(Role role);

	/**新增一个角色信息*/
	void insertRole(Role role);

	/**根据id查询一个角色信息*/
	Role queryById(Integer id);

	/**根据id删除一个角色信息*/
	int deleteRoleById(Integer id);

	/**根据多个id批量删除角色*/
	int deleteRoles(Datas datas);

	/**查询全部的角色*/
	List<Role> queryAll();

	/**增加(清除之前的)角色和许可之间的关系*/
	void insertRolePermissions(Map<String, Object> paramMap);

}
