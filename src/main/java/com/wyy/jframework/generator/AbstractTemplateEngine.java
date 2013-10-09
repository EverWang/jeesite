package com.wyy.jframework.generator;

import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * 
 * @author 张荣华
 * 转载请注明出处
 */
public abstract class AbstractTemplateEngine implements TemplateEngine{

	public abstract String getTemplatePath();
	
	public abstract String getTemplate();
	
	public abstract String getEngineType();
	
	public void run(Map context)throws Exception{
		if(GeneratorConsts.ENGINE_TYPE_FREEMARKER.equals(getEngineType()))
			executeFreemarker(context);
		else
			executeVelocity(context);
	}
	
	private void executeFreemarker(Map context)throws Exception{
		Configuration cfg = new Configuration();
	    cfg.setDirectoryForTemplateLoading(
	            new File(getTemplatePath()));
	    cfg.setObjectWrapper(new DefaultObjectWrapper());
	    
	    cfg.setCacheStorage(new freemarker.cache.MruCacheStorage(20, 250));
	            
	    Template temp = cfg.getTemplate(getTemplate());

	    Writer out = new OutputStreamWriter(System.out);
	    temp.process(context, out);
	    out.flush();
	}
	
	private void executeVelocity(Map root)throws Exception{
		
//		Velocity.init();
//		VelocityContext context = new VelocityContext(root);
//		org.apache.velocity.Template template = null;
//		
//	    template = Velocity.getTemplate(getTemplatePath()+getTemplate());
//	    
//		StringWriter sw = new StringWriter();
//		template.merge( context, sw );
//		System.out.print(sw.toString());

	}

}