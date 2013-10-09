package com.wyy.jframework.generator;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author 张荣华
 * 转载请注明出处
 */
public class TemplateTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		/* 准备数据 */
        Map<String, String> latest = new HashMap<String, String>();
        latest.put("url", "products/greenmouse.html");
        latest.put("name", "green mouse");
        
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("user", "Big Joe");
        root.put("latestProduct", latest);
        root.put("number", new Long(2222));
        root.put("date",new Date());
        
        List<String> listTest = new ArrayList<String>();
        listTest.add("1");
        listTest.add("2");
        
        root.put("list",listTest);
        
        TemplateEngine freemarkerEngine = (TemplateEngine)TemplateFactory.getInstance().getBean("freemarker");
        freemarkerEngine.run(root);//使用freemarker模板技术
        
        TemplateEngine velocityEngine = (TemplateEngine)TemplateFactory.getInstance().getBean("velocity");
        velocityEngine.run(root);//使用velocity模板技术
	}

}