package com.wyy.jframework.generator;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author 张荣华
 * 转载请注明出处
 */
public class TemplateFactory {
	private static TemplateFactory instance;
	private Map objectMap;
	
	static{
		instance = new TemplateFactory();
	}
	
	public TemplateFactory() {
		super();
		this.objectMap = new HashMap();
		synchronized (this) {
			objectMap.put("freemarker", new FreemarkerTemplateEngine(){
				public String getTemplatePath() {
					return "template";
				}
			});
			
			objectMap.put("velocity", new VelocityTemplateEngine());
		}
	}

	public static TemplateFactory getInstance(){
		return instance;
	}
	
	/**
	 * 模仿spring的工厂
	 * @param beanName
	 * @return
	 */
	public Object getBean(String beanName){
		return objectMap.get(beanName);
	}

}