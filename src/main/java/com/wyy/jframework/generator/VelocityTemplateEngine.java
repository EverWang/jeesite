package com.wyy.jframework.generator;

import java.util.Map;

/**
 * 
 * @author 张荣华
 * 转载请注明出处
 */
public class VelocityTemplateEngine extends AbstractTemplateEngine{

private static final String DEFAULT_TEMPLATE = "VelocityExample.vm";

	public String getTemplatePath() {
		return "/template/";
	}
	
	public void run(Map map) throws Exception{
		super.run(map);
	}

	public String getTemplate() {
		// TODO Auto-generated method stub
		return DEFAULT_TEMPLATE;
	}

	public String getEngineType() {
		// TODO Auto-generated method stub
		return TemplateConsts.ENGINE_TYPE_VELOCITY;
	}
}