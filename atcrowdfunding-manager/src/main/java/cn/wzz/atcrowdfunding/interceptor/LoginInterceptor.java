package cn.wzz.atcrowdfunding.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.wzz.atcrowdfunding.bean.User;

/**
 *	登录拦截器 
 *		--没有登录、没有权限的请求全部拦截下来
 */
public class LoginInterceptor extends LoginInterceptorAdapter {
	
	/**
	 * 1)获取用户的登录状态
	 * 2)如果用户已经登录，那么继续执行
	 * 3)如果用户没有登录，那么不能继续执行，对请求进行拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//URI :	/xx/xxx
		//URL :	http://127.0.0.1:8080/xx/xx
		//request.getRequestURL();
		//String uri = request.getRequestURI();
		//System.out.println("uri : " + uri);
		
		//获取会话范围中保存的用户信息
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");
		
		if(user == null) {
			//如果没有登录，重定向到登录页面
			response.sendRedirect("/login");
			return false;
		}else {
			return true;
		}
		
	}
}
