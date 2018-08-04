package cn.wzz.atcrowdfunding.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wzz.atcrowdfunding.bean.Datas;
import cn.wzz.atcrowdfunding.bean.Permission;
import cn.wzz.atcrowdfunding.bean.User;
import cn.wzz.atcrowdfunding.dao.UserDao;
import cn.wzz.atcrowdfunding.service.UserService;

@Service
public class UserServiceImpl implements  UserService{

	@Autowired
	private UserDao userDao;
	
	public User queryUser(Integer id) {
		return userDao.queryUser(id);
	}

	public User queryUser4Login(User user) {
		return userDao.queryUser4Login(user);
	}

	public List<User> queryPageUsers(Map<String, Object> paramMap) {
		return userDao.queryPageUsers(paramMap);
	}

	public int queryTotalSize(Map<String, Object> paramMap) {
		return userDao.queryTotalSize(paramMap);
	}

	public int insertUser(User user) {
		return userDao.insertUser(user);
	}

	public User queryById(Integer id) {
		return userDao.queryUser(id);
	}

	public int updateUser(User user) {
		return userDao.updateUser(user);
	}

	public int deleteUserById(Integer id) {
		return userDao.deleteUserById(id);
	}

	public int deleteUsers(Datas datas) {
		return userDao.deleteUsers(datas);
	}

	public void insertUserRoles(Map<String, Object> paramMap) {
		userDao.insertUserRoles(paramMap);
	}

	public int deleteUserRoles(Map<String, Object> paramMap) {
		return userDao.deleteUserRoles(paramMap);
	}

	public List<Integer> queryRoleidsByUserid(Integer id) {
		return userDao.queryRoleidsByUserid(id);
	}

	public List<Permission> queryPermissionsByUser(User dbUser) {
		return userDao.queryPermissionsByUser(dbUser);
	}

}
