package com.wyy.jframework.generator;

import java.util.Map;

/**
 * 
 * @author 张荣华
 * 转载请注明出处
 */
public class FreemarkerTemplateEngine extends AbstractTemplateEngine{
	private static final String DEFAULT_TEMPLATE = "FreemarkerExample.ftl";
	
	/**
	 * 这个方法应该实现的是读取配置文件
	 */
	public String getTemplatePath() {
		return null;
	}
	
	public void run(Map root) throws Exception{
		super.run(root);
	}

	public String getTemplate() {
		// TODO Auto-generated method stub
		return DEFAULT_TEMPLATE;
	}

	public String getEngineType() {
		return GeneratorConsts.ENGINE_TYPE_FREEMARKER;
	}
}