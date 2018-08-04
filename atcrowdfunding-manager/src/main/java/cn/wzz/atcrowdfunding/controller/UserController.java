package cn.wzz.atcrowdfunding.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wzz.atcrowdfunding.BaseController;
import cn.wzz.atcrowdfunding.bean.AJAXResult;
import cn.wzz.atcrowdfunding.bean.Datas;
import cn.wzz.atcrowdfunding.bean.Page;
import cn.wzz.atcrowdfunding.bean.Role;
import cn.wzz.atcrowdfunding.bean.User;
import cn.wzz.atcrowdfunding.service.RoleService;
import cn.wzz.atcrowdfunding.service.UserService;
import cn.wzz.atcrowdfunding.util.ConfigUtil;
import cn.wzz.atcrowdfunding.util.MD5Util;
import cn.wzz.atcrowdfunding.util.StringUtil;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	/**去分配角色页面*/
	@RequestMapping("/assignRole")
	public String assignRolePage(Integer id,Model model) {
		//查询用户信息
		User user = userService.queryById(id);
		model.addAttribute("user", user);

		//查询出所有角色的信息，显示到页面
		List<Role> roles = roleService.queryAll();
		//查询出页面上已经分配的角色数据
		List<Integer> roleids = userService.queryRoleidsByUserid(id);
		//左边未分配的角色信息
		List<Role> leftRoles = new ArrayList<Role>();
		//右边已经分配的角色信息
		List<Role> rightRoles = new ArrayList<Role>();
		
		//根据userid判断所有的角色中已经分配了的角色
		for (Role role : roles) {
			if(roleids.contains(role.getId())) {
				rightRoles.add(role);
			}else {
				leftRoles.add(role);
			}
		}
		
		model.addAttribute("leftRoles",leftRoles);
		model.addAttribute("rightRoles",rightRoles);

		return "user/assignRole";
	}
	/**从左添加到右边，添加的功能*/
	@ResponseBody
	@RequestMapping("/assign")
	public Object assign(Integer userid,Datas datas) {
		start();
		try {
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("userId", userid);
			paramMap.put("roleIds", datas.getIds());
			userService.insertUserRoles(paramMap);
			success();
		} catch (Exception e) {
			fail();
		}
		return end();
	}
	/**从右移动到左，删除的功能*/
	@ResponseBody
	@RequestMapping("/unassign")
	public Object unassign(Integer userid,Datas datas) {
		start();
		try {
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("userId", userid);
			paramMap.put("roleIds", datas.getIds());
			int count = userService.deleteUserRoles(paramMap);
			success(count != -1);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		return end();
	}
	
	/**删除一个用户的功能*/
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(Integer id) {
		AJAXResult result = new AJAXResult();
		try {
			//删除用户
			int count = userService.deleteUserById(id);
			result.setSuccess(count != -1);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	/**删除多个用户的功能*/
	@ResponseBody
	@RequestMapping("/deletes")
	public Object deletes(Datas datas) {
		AJAXResult result = new AJAXResult();
		try {
			//删除用户
			int count = userService.deleteUsers(datas);
			result.setSuccess(count != -1);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	/**跳转去批量添加页面*/
	@RequestMapping("/addBatch")
	public String batchPage() {
		return "user/batch";
	}
	/**批量插入用户信息的功能(此处没有使用AJAX，使用的是普通的表单提交数据的方式)
	 *
	 * 1.传入参数 ： HttpServletRequest request
	 	//getParameterMap（）方法将所有的参数都封装成了一个集合。getParameterValues（）相当于根据键找值。
			Map<String,String[]> parameterMap = request.getParameterMap();
		//当页面传递了多个相同的name属性的值时，用getParameterValues（）方法接收时，收到的是一个字符串类型的数组
			request.getParameterValues("username");
		//getParameter（）方法相当于取得字符串数组中的第一个
			request.getParameter("username");
	 * 2.传入参数 ： String[] username
		在框架中可以直接传入一个字符串数组，参数名为name属性值。框架可以直接自动封装值。
	 * 3.传入参数  ： User[] user		XXX SpringMVC不支持引用数组类型数据入参接收数据	（505）
	  			List<User>		SpringMVC默认不支持List入参（只new除了一个对象，并没有封装数据null）
	  				- 对List<User>入参进行特殊操作：
	  					1）后台 增加封装集合对象的包装类，包装类中增加集合属性并提供set/get方法
	  					2）前台 修改表单数据提交的方式
	  						之前 id=1&id=2&name=zd&name=ls 并不能区分谁和谁是一对
	  						改为 users[0].id=1&users[1].id=2&uesrs[0].name=zs （users名是包装类的属性名）
	  					3）在方法中增加包装类参数即可
	  					4）封装数据时，可能会出现一些无用数据（删除一行表格，该索引位置传来数据全是null)，需要删除
	  					5）注意：包装类不能使用泛型的！！
	 */
	@RequestMapping("/batchInsert")
	public String batchInsert(Datas datas) {
//		for(User user : dates.getUsers()) {
//			if(user.getLoginacct() == null) {
//				userDates.getUsers().remove(user);
//			}
//		}
		//for循环删除数据不安全(数据会出现混乱)，应该使用迭代器
		Iterator<User> userIter = datas.getUsers().iterator();
		while(userIter.hasNext()) {
			User user = userIter.next();
			if(user.getLoginacct() == null) {
				userIter.remove();
			}
			System.out.println(user);
		}
		
		return "user/index";
	}
	
	
	/**跳转去修改页面*/
	@RequestMapping("/edit")
	public String editPage(Integer id , Model model) {
		User user = userService.queryById(id);
		model.addAttribute("user", user);
		return "user/edit";
	}
	/**更新用户信息的功能*/
	@ResponseBody
	@RequestMapping("/update")
	public Object update(User user) {
		AJAXResult result = new AJAXResult();
		try {
			//修改信息
			int count = userService.updateUser(user);
			//判断是否修改成功
			result.setSuccess(count != -1);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	
	/**跳转去添加页面*/
	@RequestMapping("/add")
	public String addPage() {
		return "user/add";
	}
	/**添加用户信息的功能*/
	@ResponseBody
	@RequestMapping("/insert")
	public Object insert(User user) {
		AJAXResult result = new AJAXResult();
		try {
			//保存用户信息(读取配置文件中的默认密码)
			user.setUserpswd(MD5Util.digest(ConfigUtil.getValueByKey("DEFAULT_PASSWORD")));
			//保存用户创建的时间(为了查询时，排序)
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = dateFormat.format(new Date());
			user.setCreatetime(time);
			
			int count = userService.insertUser(user);
			//判断是否插入成功（可以不用判断，因为只有插入不成功就会报错！）
			result.setSuccess(count != -1);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	/**异步查询***
	    1)点击菜单
		2)渲染页面，loaging
		3)serice调用DAO，查询数据(limit start,size)
		4)返回数据结果（JSON）
		5)局部刷新
	 */
	@RequestMapping("/index")
	public String index() {
		return "user/index";
	}
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
			
			List<User> users = userService.queryPageUsers(paramMap);
			
			//获取总共的记录数
			int totalsize = userService.queryTotalSize(paramMap);
			Page<User> userPage = new Page<User>();
			userPage.setPageno(pageno);
			userPage.setPagesize(pagesize);
			userPage.setDatas(users);
			userPage.setTotalsize(totalsize);
			
			result.setData(userPage);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
		}
		return result;
	}
	
	/**同步查询***
	    1)点击菜单，传递参数
		2)调用服务，查询数据
		3)serice调用DAO，查询数据(limit start,size)
		4)返回数据结果
		5)保存数据结果
		6)跳转页面（渲染页面）
	 */
	//@RequestMapping("/index")
	@Deprecated
	 public String index1(@RequestParam(value="pageno",required=false,defaultValue="1")Integer pageno,
			 			@RequestParam(value="pagesize",required=false,defaultValue="2")Integer pagesize,
			 			Model model) {
		//System.out.println(userService);
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("start", (pageno-1)*pagesize);
		paramMap.put("size", pagesize);
		
		List<User> users = userService.queryPageUsers(paramMap);
//		model.addAttribute("users", users);
		
		//获取总共的记录数
		int totalsize = userService.queryTotalSize(paramMap);
		//计算出总页数
//		int totalpage = 0;
//		if(totalsize % pagesize == 0) {
//			totalpage = totalsize/pagesize;
//		} else {
//			totalpage = totalsize/pagesize + 1;
//		}
		Page<User> userPage = new Page<User>();
		userPage.setPageno(pageno);
		userPage.setPagesize(pagesize);
		userPage.setDatas(users);
		userPage.setTotalsize(totalsize);
		
		model.addAttribute("userPage", userPage);
		
        return "user/index";	//prefix + vive Name + suffix
	 }
    
	/**测试方法*/
    @ResponseBody
    @RequestMapping("/json/{id}")
    public Object json(@PathVariable("id") Integer id) {
        //Map<String,Object> map = new HashMap<String,Object>();
        //map.put("username", "张三");
        //return map;
    	
    	//测试Spring中的AOP是否能够使用
    	User user = userService.queryUser(id);
        return user;
    }
    
}
