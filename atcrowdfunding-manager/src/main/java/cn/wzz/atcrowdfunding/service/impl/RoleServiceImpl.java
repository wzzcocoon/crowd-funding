package cn.wzz.atcrowdfunding.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wzz.atcrowdfunding.bean.Datas;
import cn.wzz.atcrowdfunding.bean.Role;
import cn.wzz.atcrowdfunding.dao.RoleDao;
import cn.wzz.atcrowdfunding.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;
	
	public List<Role> queryPageRoles(Map<String, Object> paramMap) {
		return roleDao.queryPageRoles(paramMap);
	}

	public int queryTotalSize(Map<String, Object> paramMap) {
		return roleDao.queryTotalSize(paramMap);
	}

	public int updateRole(Role role) {
		return roleDao.updateRole(role);
	}

	public void insertRole(Role role) {
		roleDao.insertRloe(role);
	}

	public Role queryById(Integer id) {
		return roleDao.queryById(id);
	}

	public int deleteRoleById(Integer id) {
		return roleDao.deleteRoleById(id);
	}

	public int deleteRoles(Datas datas) {
		return roleDao.deleteRoles(datas);
	}

	public List<Role> queryAll() {
		return roleDao.queryAll();
	}

	public void insertRolePermissions(Map<String, Object> paramMap) {
		//清除之前角色和许可的关系
		roleDao.deleteRolePermissions(paramMap);
		//增加刚刚传来的角色和许可的关系
		roleDao.insertRolePermissions(paramMap);
	}

}
