package cn.wzz.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.wzz.atcrowdfunding.bean.User;

/**Spring2.0是基于配置文件配置一个对象的
 * 		<bean id="user" class="xxx.User"></bean>
 * Spring3.0大量采用了注解的方式，直接在java类中配置一个对象
 * 		@Configuration用来声明这是一个配置对象的类
 */
@Configuration
public class BeanConfig {

	@Bean
	public User user() {
		return new User();
	}
}
