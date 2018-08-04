package cn.wzz.atcrowdfunding.service;

import java.util.List;
import java.util.Map;

import cn.wzz.atcrowdfunding.bean.Datas;
import cn.wzz.atcrowdfunding.bean.Permission;
import cn.wzz.atcrowdfunding.bean.User;

public interface UserService {

	User queryUser(Integer id);

	/**登录用户查询*/
	User queryUser4Login(User user);

	/**查询分页的用户集合*/
	List<User> queryPageUsers(Map<String, Object> map);

	/**查询用户总记录数*/
	int queryTotalSize(Map<String, Object> paramMap);

	/**增加一个用户的信息*/
	int insertUser(User user);

	/**根据id查询一个用户的信息*/
	User queryById(Integer id);

	/**更新一个用户的信息*/
	int updateUser(User user);

	/**删除一个用户*/
	int deleteUserById(Integer id);

	/**删除选中的多个用户*/
	int deleteUsers(Datas datas);

	/**增加用户和角色关系*/
	void insertUserRoles(Map<String, Object> paramMap);

	/**删除用户和角色关系*/
	int deleteUserRoles(Map<String, Object> paramMap);

	/**根据用户的id获取已经分配的角色id*/
	List<Integer> queryRoleidsByUserid(Integer id);

	/**根据用户找到角色，再找分配到的权限*/
	List<Permission> queryPermissionsByUser(User dbUser);

}
