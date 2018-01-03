/**
 *
 *  Create On 2015年12月17日 下午11:06:32
 *  auther: huoshan
 *  
 */
package com.taiji.common.util.listener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author huoshan
 *
 */
public class ConfigLog4jListener implements ServletContextListener {  
    public static final String log4jdirkey = "log4jdir";  
    public void contextDestroyed(ServletContextEvent servletcontextevent) {  
        System.getProperties().remove(log4jdirkey);  
    }  
    public void contextInitialized(ServletContextEvent servletcontextevent) {
	    String log4jProperties = servletcontextevent.getServletContext().getInitParameter("log4jProperties");
	    String log4jPropertyName = log4jProperties.split(":")[1];
	    
	    String classpath = this.getClass().getClassLoader().getResource(log4jPropertyName).getPath();
	    
	    Properties p = new Properties();
	    try {
			p.load(new FileInputStream(new File(classpath)));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    Iterator itr = p.entrySet().iterator();
	    while (itr.hasNext()){
	        Entry e = (Entry)itr.next();
	        System.setProperty(e.getKey()+"", e.getValue()+"");  
	    }
    
    }  
}
