package cn.wzz.atcrowdfunding.service;

import java.util.List;

import cn.wzz.atcrowdfunding.bean.Permission;

public interface PermissionService {

	/** 查找根节点的方法(使用递归后，将根菜单的id由null改为0，共同使用queryChildPermission方法)*/
	Permission queryRootPermission();
	
	/**根据父节点查找子节点的方法*/
	List<Permission> queryChildPermission(Integer id);

	/**查询所有的节点*/
	List<Permission> queryAll();

	/**插入一个节点的方法*/
	void insertPermission(Permission permission);

	/**根据id查找一个Permission*/
	Permission queryById(Integer id);

	/**根据id修改一个Permission的信息*/
	int updatePermission(Permission permission);

	/**根据id删除一个节点*/
	int deletePermission(Integer id);

	/**根据角色的id查找已经分配的所有许可的id*/
	List<Integer> queryPermissionidsByRoleid(Integer roleid);

}
