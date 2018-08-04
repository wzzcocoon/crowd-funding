package cn.wzz.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import cn.wzz.atcrowdfunding.bean.User;

public class TestSpring3 {
	
	public static void main(String[] args) {
	
		ApplicationContext context =
				new AnnotationConfigApplicationContext("cn.wzz.test");
		User user = (User) context.getBean("user");
		System.out.println(user);
	}
	
}
