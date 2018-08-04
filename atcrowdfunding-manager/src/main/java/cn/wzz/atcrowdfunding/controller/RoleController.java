package cn.wzz.atcrowdfunding.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wzz.atcrowdfunding.BaseController;
import cn.wzz.atcrowdfunding.bean.AJAXResult;
import cn.wzz.atcrowdfunding.bean.Datas;
import cn.wzz.atcrowdfunding.bean.Page;
import cn.wzz.atcrowdfunding.bean.Role;
import cn.wzz.atcrowdfunding.service.RoleService;
import cn.wzz.atcrowdfunding.util.StringUtil;

@RequestMapping("/role")
@Controller
public class RoleController extends BaseController{
	
	@Autowired
	private RoleService roleService;

	
	/**跳转权限分配页面*/
	@RequestMapping("/assignPermission")
	public String assignPermissionPage(Integer id,Model model) {
		Role role = roleService.queryById(id);
		model.addAttribute("role", role);
		return "role/assignPermission";
	}
	/**分配权限的功能(删除之前的分配，采用当前传过来的数据当做数据库的最终数据)*/
	@ResponseBody
	@RequestMapping("/assign")
	public Object assign(Integer roleid,Datas datas) {
		start();
		try {
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("roleid", roleid);
			paramMap.put("permissionids",datas.getIds());
			roleService.insertRolePermissions(paramMap);
			success();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		return end();
	}
	
	/**删除一个角色的功能*/
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(Integer id) {
		start();
		try {
			int count = roleService.deleteRoleById(id);
			success(count != -1);
		} catch (Exception e) {
			fail();
		}
		return end();
	}
	/**删除多个角色的功能*/
	@ResponseBody
	@RequestMapping("/deletes")
	public Object deletes(Datas datas) {
		start();
		try {
			int count = roleService.deleteRoles(datas);
			success(count != -1);
		} catch (Exception e) {
			fail();
		}
		return end();
	}
	
	/**跳转批量插入角色页面*/
	@RequestMapping("/addBatch")
	public String addBatchPage() {
		return "role/batch";
	}

	/**跳转插入角色页面*/
	@RequestMapping("/add")
	public String addPage() {
		return "role/add";
	}
	@ResponseBody
	@RequestMapping("/insert")
	public Object insert(Role role) {
		AJAXResult result = new AJAXResult();
		try {
			roleService.insertRole(role);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	/**跳转修改页面*/
	@RequestMapping("/edit")
	public String editPage(Integer id,Model model) {
		//根据id查找角色
		Role role = roleService.queryById(id);
		model.addAttribute("role", role);
		return "role/edit";
	}
	/**修改功能*/
	@ResponseBody
	@RequestMapping("/update")
	public Object update(Role role) {
		AJAXResult result = new AJAXResult();
		
		try {
			//修改信息
			int count = roleService.updateRole(role);
			//判断信息是否修改成功
			result.setSuccess(count != -1);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	/**跳转角色页面*/
	@RequestMapping("/index")
	public String rolePage() {
		return "role/index";
	}
	/**分页异步查询*/
	@ResponseBody
	@RequestMapping("/pageQuery")
	public Object pageQuery(String queryContent,Integer pageno,Integer pagesize) {
		AJAXResult result = new AJAXResult();
		try {
			//分页查询数据
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("start", (pageno-1)*pagesize);
			paramMap.put("size", pagesize);
			//将特殊字符转义
			if( !StringUtil.isEmpty(queryContent)) {
				if( queryContent.indexOf("\\") != -1 ) {
					//java中的\需要转义为\\。replaceAll中的第一个参数是regex，\\也需要转义\\\\。sql语句中也需要转义则\\\\\\\\
					queryContent = queryContent.replaceAll("\\\\", "\\\\\\\\");
				}						
				if( queryContent.indexOf("%") != -1 ) {
					queryContent = queryContent.replaceAll("%", "\\\\%");
				}						
				if( queryContent.indexOf("_") != -1 ) {
					queryContent = queryContent.replaceAll("_", "\\\\_");
				}						
			}
			paramMap.put("queryContent", queryContent);
			
			List<Role> roles = roleService.queryPageRoles(paramMap);
			
			//获取总共的记录数
			int totalsize = roleService.queryTotalSize(paramMap);
			Page<Role> rolePage = new Page<Role>();
			rolePage.setPageno(pageno);
			rolePage.setPagesize(pagesize);
			rolePage.setTotalsize(totalsize);
			rolePage.setDatas(roles);
			
			result.setData(rolePage);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}

}
