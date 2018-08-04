package cn.wzz.atcrowdfunding.interceptor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.wzz.atcrowdfunding.bean.Permission;
import cn.wzz.atcrowdfunding.service.PermissionService;
import cn.wzz.atcrowdfunding.util.StringUtil;

/**
 * 授权拦截器
 * 		--登录以后，没有授权的请求也进行拦截
 */
public class AuthAdapter extends HandlerInterceptorAdapter {

	@Autowired
	private PermissionService permissionService;
	
	/**
	 * 1)获取用户发送的请求路径
	 * 2)判断当前的路径是否需要进行授权验证(验证路径是否存在许可表中)
	 * 3)如果不需要验证，请求继续执行
	 * 4)如果需要验证，判断当前用户是否有授权访问	
	 * 		4-1)如果有授权，请求继续执行
	 * 		4-2)如果没有授权，跳转到错误页面，展示错误信息
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//获取请求路径
		String uri = request.getRequestURI();
		//查询所有的许可信息
		List<Permission> permissions = permissionService.queryAll();
		//创建一个Set存储许可表中的url （set的特性：无序、不重复）
		Set<String> uriSet = new HashSet<String>();

		for (Permission permission : permissions) {
			//如果许可表中的url不为空，放入Set中
			if(!StringUtil.isEmpty(permission.getUrl())) {
				uriSet.add(permission.getUrl());
			}
		}
		
		//判断请求路径是否在Set中		uriSet所有的许可路径		userAuthUrlSet用户被授权的许可路径
		if(uriSet.contains(uri)) {
			//需要验证		获取session域中保存的许可路径
			HttpSession session = request.getSession();
			Set<String> userAuthUrlSet = (Set<String>) session.getAttribute("userAuthUrlSet");
			if(userAuthUrlSet.contains(uri)) {
				//已经授权
				return true;
			}else {
				response.sendRedirect("/error");
				return false;
			}
		} else {
			//不需要验证
			return true;
		}
		
	}
}
