package cn.wzz.atcrowdfunding.util;

import java.util.ResourceBundle;

/**
 * 获取配置文件中的信息
 * @author 王子政
 */
public class ConfigUtil {
	
	public static ResourceBundle rb = ResourceBundle.getBundle("config");
	
	public static String getValueByKey(String key) {
		return rb.getString(key);
	}
}
