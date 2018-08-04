package cn.wzz.atcrowdfunding.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wzz.atcrowdfunding.bean.Permission;
import cn.wzz.atcrowdfunding.dao.PermissionDao;
import cn.wzz.atcrowdfunding.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService {
	
	@Autowired
	private PermissionDao permissionDao;

	public Permission queryRootPermission() {
		return permissionDao.queryRootPermission();
	}

	public List<Permission> queryChildPermission(Integer id) {
		return permissionDao.queryChildPermission(id);
	}

	public List<Permission> queryAll() {
		return permissionDao.queryAll();
	}

	public void insertPermission(Permission permission) {
		permissionDao.insertPermission(permission);
	}

	public Permission queryById(Integer id) {
		return permissionDao.queryById(id);
	}

	public int updatePermission(Permission permission) {
		return permissionDao.updatePermission(permission);
	}

	public int deletePermission(Integer id) {
		return permissionDao.deletePermission(id);
	}

	public List<Integer> queryPermissionidsByRoleid(Integer roleid) {
		return permissionDao.queryPermissionidsByRoleid(roleid);
	}
}
