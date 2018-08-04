package cn.wzz.atcrowdfunding.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wzz.atcrowdfunding.BaseController;
import cn.wzz.atcrowdfunding.bean.Permission;
import cn.wzz.atcrowdfunding.service.PermissionService;

@RequestMapping("/permission")
@Controller
public class PermissionController extends BaseController {
	
	@Autowired
	private PermissionService permissionService;
	
	/**删除节点信息的功能*/
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(Integer id) {
		start();
		try {
			int count = permissionService.deletePermission(id);
			success(count != -1);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		return end();
	}
	
	/**去修改页面*/
	@RequestMapping("/edit")
	public String editPage(Integer id,Model model) {
		Permission permission = permissionService.queryById(id);
		model.addAttribute("permission", permission);
		return "permission/edit";
	}
	/**修改节点信息的功能*/
	@ResponseBody
	@RequestMapping("/update")
	public Object update(Permission permission) {
		start();
		try {
			int count = permissionService.updatePermission(permission);
			success(count != -1);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		return end();
	}

	/**去新增页面*/
	@RequestMapping("/add")
	public String addPage() {
		return "permission/add";
	}
	/**新增一个节点的功能*/
	@ResponseBody
	@RequestMapping("/insert")
	public Object insert(Permission permission) {
		start();
		try {
			permissionService.insertPermission(permission);
			success();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		return end();
	}
	
	/**异步查询树的方法*/
	@ResponseBody
	@RequestMapping("/loadAssignAsyncData")
	public Object loadAssignAsyncData(Integer roleid) {
		List<Permission> permissions = new ArrayList<Permission>();
		//从数据库中查出所有的菜单
		List<Permission> dbPermission = permissionService.queryAll();
		//*****查询当前角色已经分配过得许可*****
		List<Integer> permissionids = permissionService.queryPermissionidsByRoleid(roleid);
		
		//使用Map集合整合菜单之间的关系(将id和菜单绑定在一起)
		Map<Integer,Permission> permissionMap = new HashMap<Integer,Permission>();
		for (Permission permission : dbPermission) {
			//判断 当前遍历的permission(查到的所有的菜单中)的主键在已经分配的许可(菜单)中，就放到Map中
			if( permissionids.contains(permission.getId()) ) {
				//在的话，设置复选框的选中状态为true
				permission.setChecked(true);
			}
			permissionMap.put(permission.getId(), permission);
			
		}
		
		for (Permission permission : dbPermission) {
			//子菜单
			Permission children = permission;
			if(children.getPid() == 0) {
				//根菜单
				permissions.add(permission);
			}else {
				//父菜单
				Permission parent = permissionMap.get(children.getPid());
				//组合父子菜单之间的关系
				parent.getChildren().add(children);
			}
		}
		
		return permissions;
	}
	/**异步查询树的方法(过时,没有复选框的选中状态)*/
	@Deprecated
	@ResponseBody
	@RequestMapping("/loadAsyncData")
	public Object loadAsyncData() {
		List<Permission> permissions = new ArrayList<Permission>();
		//使用Map集合整合菜单之间的关系(将id和菜单绑定在一起)
		Map<Integer,Permission> permissionMap = new HashMap<Integer,Permission>();
		//从数据库中查出所有的菜单
		List<Permission> dbPermission = permissionService.queryAll();
		for (Permission permission : dbPermission) {
			permissionMap.put(permission.getId(), permission);
		}
		for (Permission permission : dbPermission) {
			//子菜单
			Permission children = permission;
			if(children.getPid() == 0) {
				//根菜单
				permissions.add(permission);
			}else {
				//父菜单
				Permission parent = permissionMap.get(children.getPid());
				//组合父子菜单之间的关系
				parent.getChildren().add(children);
			}
		}
	
		return permissions;
	}
	
	/**查询树的结构
	 * 给页面返回一个【】类型的数据
	 * 页面解析成树结构
	 */
	@Deprecated
	@ResponseBody
	@RequestMapping("/loadData")
	public Object loadData() {
		start();
		try {
/*	临时数据        *******
			Permission p1 = new Permission();
			p1.setName("1");
			Permission p2 = new Permission();
			p2.setName("1-1");
			Permission p3 = new Permission();
			p3.setName("1-2");
			// Permission类中的children是已经new出来的，可以直接赋值
			p1.getChildren().add(p2);
			p1.getChildren().add(p3);
			
			//后台页面需要的数据类型是【】
			// List ==> []		Map ==> {}		User ==> {}
			List<Permission> permissions = new ArrayList<Permission>();
			permissions.add(root);
************************************************************************/			
/*	普通数据库查询数据        *******
			//查询根菜单
			Permission root = permissionService.queryRootPermission();
			//查询子菜单
			List<Permission> childrenPermissions = permissionService.queryChildPermission(root.getId());
			//整合父子菜单的关系
			root.setChildren(childrenPermissions);
			
			for(Permission childrenPermission : childrenPermissions) {
				//查询子菜单的子菜单
				List<Permission> childrenchildrenPermissions = permissionService.queryChildPermission(childrenPermission.getId());
				//整合父子菜单的关系
				childrenPermission.setChildren(childrenchildrenPermissions);
			}
			//查子菜单的子菜单的子菜单.....	   childrenchildrenchildrenPermissions
			 
			//后台页面需要的数据类型是【】
			// List ==> []		Map ==> {}		User ==> {}
			List<Permission> permissions = new ArrayList<Permission>();
			permissions.add(root);
 ************************************************************************/			
/* 递归查询菜单数据*********
			//queryChildPermission方法需要传递一个对象参数，这时创建一个临时数据temp
			Permission temp = new Permission();
			temp.setId(0);
			queryChildPermission(temp);
			
			//传入页面的数据是temp.getChildren(),临时数据(id=0)查出来也放到了childrenPermissions
			putData(temp.getChildren());
* ***********************************************************************/
/*递归查询了很多次数据库，现在查询一次数据库，准备好树形结构的数据***************************
			List<Permission> permissions = new ArrayList<Permission>();
			//从数据库中查出所有的菜单
			List<Permission> dbPermission = permissionService.queryAll();
			// 站在子菜单的角度分配父子关系。将所有的节点都认为是子菜单，然后给他们分配父节点。有一个特殊，根节点
			for (Permission permission : dbPermission) {
				//子菜单
				Permission children = permission;
				if(children.getPid() == 0) {
					//根菜单
					permissions.add(permission);
				}else {
					for (Permission innerPermission : dbPermission) {
						if(innerPermission.getId() == children.getPid()) {
							//父菜单
							Permission parent = innerPermission;
							//组合父子关系
							parent.getChildren().add(children);
							//跳出最近的循环（跳出多重循环的话，需要添加标记）
							break;
						}
					}
				}
			}
			putData(permissions);
*********************************************************************************/			
//嵌套循环是平方的运行次数。ArrayList和LinkedList的区别？ArrayList查询快（在使用索引的情况下），嵌套循环每一个都循环，并没有使用索引。
			List<Permission> permissions = new ArrayList<Permission>();
			//使用Map集合整合菜单之间的关系(将id和菜单绑定在一起)
			Map<Integer,Permission> permissionMap = new HashMap<Integer,Permission>();
			//从数据库中查出所有的菜单
			List<Permission> dbPermission = permissionService.queryAll();
			for (Permission permission : dbPermission) {
				permissionMap.put(permission.getId(), permission);
			}
			for (Permission permission : dbPermission) {
				//子菜单
				Permission children = permission;
				if(children.getPid() == 0) {
					//根菜单
					permissions.add(permission);
				}else {
					//父菜单
					Permission parent = permissionMap.get(children.getPid());
					//组合父子菜单之间的关系
					parent.getChildren().add(children);
				}
			}
			
			putData(permissions);
			success();
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		}
		
		return end();
	}
	/**递归算法的要素：
	 * 	1）逻辑相同，方法自己调用自己
	 * 	2）方法调用时，数据之间应该有规律
	 * 	3）递归方法一定的有跳出的逻辑（此方法的出口是当查询不到子菜单时，逻辑结束）
	 * */
	@Deprecated
	private void queryChildPermission(Permission parent) {
		//查询子菜单
		List<Permission> childrenPermissions = permissionService.queryChildPermission(parent.getId());
		
		for(Permission childrenPermission:childrenPermissions) {
			queryChildPermission(childrenPermission);
		}
		//组合父子菜单的关系
		parent.setChildren(childrenPermissions);
	}
	
	
	/**去主页面*/
	@RequestMapping("/index")
	public String index() {
		return "permission/index";
	}
	
}
