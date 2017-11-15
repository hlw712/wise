package com.wise.common.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 全局静态配置文件
 *
 * @author scalpel
 * @version 1.0
 * @date 2016年3月15日上午11:20:53
 */
public class Globals extends PropertyPlaceholderConfigurer{

	/**
	 * 静态文件加载的配置信息
	 */
	private static Map<String,String> propertiesMap;
	
	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		
		super.processProperties(beanFactoryToProcess, props);
		propertiesMap = new HashMap<String,String>();
	    for (Iterator iterator = props.keySet().iterator(); iterator.hasNext(); ) {
	    	Object key = iterator.next();
	    	String keyStr = key.toString();
	    	String value = props.getProperty(keyStr);
	    	propertiesMap.put(keyStr, value); 
	    }
	}
	
	
	public static String getProperties(String key)
	{
		return propertiesMap.get(key);
	}
	

	
}
