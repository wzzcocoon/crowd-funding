package cn.wzz.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *	服务器监听器
 */
public class ServerStartupListener implements ServletContextListener{

	public void contextInitialized(ServletContextEvent sce) {
		
		//获取web应用对象
		ServletContext application = sce.getServletContext();
		
		//获取 web应用路径
		String path = application.getContextPath();
		
		//将路径放到application域范围中
		application.setAttribute("APP_PATH", path);
	}

	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
