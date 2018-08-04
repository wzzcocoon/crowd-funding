package cn.wzz.atcrowdfunding.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 登录适配器	
 */
public class LoginInterceptorAdapter implements HandlerInterceptor{

	/**
	 * 在执行处理器逻辑之前进行操作
	 * 方法的返回结果决定业务逻辑是否继续执行
	 * true继续执行、false不继续执行 
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	/**在处理逻辑完(controller结束)后执行*/
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/**在渲染视图后(整个请求处理完成)执行*/
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
