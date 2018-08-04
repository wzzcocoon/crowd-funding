package cn.wzz.atcrowdfunding.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import cn.wzz.atcrowdfunding.bean.Datas;
import cn.wzz.atcrowdfunding.bean.Permission;
import cn.wzz.atcrowdfunding.bean.User;

public interface UserDao {	

	@Select("select * from t_user where id = #{id}")
	User queryUser(Integer id);

	@Select("select * from t_user where loginacct = #{loginacct} and userpswd = #{userpswd}")
	User queryUser4Login(User user);

	List<User> queryPageUsers(Map<String, Object> map);

	int queryTotalSize(Map<String, Object> paramMap);

	int insertUser(User user);

	int updateUser(User user);

	int deleteUserById(Integer id);

	int deleteUsers(Datas datas);

	void insertUserRoles(Map<String, Object> paramMap);

	int deleteUserRoles(Map<String, Object> paramMap);

	List<Integer> queryRoleidsByUserid(Integer id);

	List<Permission> queryPermissionsByUser(User dbUser);

}
