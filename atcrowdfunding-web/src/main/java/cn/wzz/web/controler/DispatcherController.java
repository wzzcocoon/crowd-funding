package cn.wzz.web.controler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wzz.atcrowdfunding.bean.AJAXResult;
import cn.wzz.atcrowdfunding.bean.Permission;
import cn.wzz.atcrowdfunding.bean.User;
import cn.wzz.atcrowdfunding.service.UserService;
import cn.wzz.atcrowdfunding.util.MD5Util;
import cn.wzz.atcrowdfunding.util.StringUtil;

@Controller
public class DispatcherController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value= {"","/","/index","/index/"}) 
	public String index() {
		return "index";
	}
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@Deprecated
	@RequestMapping("/dologin")
	public String dologin(User user,Model model) {
		//1.获取表单中传递的参数
		//2.调用服务接口，查询数据(将密码转换成MD5值)
		user.setUserpswd(MD5Util.digest(user.getUserpswd()));
		User dbUser = userService.queryUser4Login(user);
		//3.根据返回的值判断是否登录成功
		if( dbUser != null ) {
			//3.1如果登录成功，跳转到后台的主界面
			return "main";
		}else {
			//3.2如果登录失败，跳转到登录界面，并且提示错误信息
			String errorMsg = "登录账号或者密码不正确，请重新输入";
			//model中存放的数据默认存在request域中
			model.addAttribute("errorMsg", errorMsg);
			//采用这种方式进行重定向，框架会自动将请求中的参数保存到地址栏中传过去
			return "redirect:login";
		}
	}
	
	/**登录的功能*/
	@ResponseBody
	@RequestMapping("/checkLogin")
	public Object checkLogin(User user,HttpSession session) {
		AJAXResult result = new AJAXResult();
		
		user.setUserpswd(MD5Util.digest(user.getUserpswd()));
		User dbUser = userService.queryUser4Login(user);
		
		if(dbUser == null) {
			result.setSuccess(false);
		} else {
			result.setSuccess(true);
			
			//查询当前登录用户的权限菜单(许可)
			List<Permission> permissions = userService.queryPermissionsByUser(dbUser);
			//创建一个Set集合，用来存放该用户可以访问的权限
			Set<String> userAuthUrlSet = new HashSet<String>();
			//整合权限菜单
			Map<Integer,Permission> permissionMap = new HashMap<Integer,Permission>();
			for (Permission permission : permissions) {
				//如果许可表中的url不为空，放入Set中
				if(!StringUtil.isEmpty(permission.getUrl())) {
					userAuthUrlSet.add(permission.getUrl());
				}
				permissionMap.put(permission.getId(), permission);
			}
			Permission root = null;
			for (Permission permission : permissions) {
				Permission child = permission;
				if(child.getPid() == 0) {
					//根菜单
					root = permission;
				}else {
					Permission parent = permissionMap.get(child.getPid());
					parent.getChildren().add(child);
				}
			}
			
			session.setAttribute("rootPermission", root);
			session.setAttribute("userAuthUrlSet", userAuthUrlSet);
			session.setAttribute("loginUser", dbUser);
		}
		
		return result;
	}
	
	@RequestMapping("/main")
	public String mainPage() {
		return "main";
	}

	@RequestMapping("/error")
	public String erroePage() {
		return "error";
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		//让session域失效
		session.invalidate();
		return "index";
	}
}
