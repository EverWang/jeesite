package com.wyy.jframework.generator.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;

public class GeneratorCfg {
	// 文件分隔符
	public String FILE_SEPERATOR = File.separator;
	private static GeneratorCfg instance;

	private GeneratorCfg() {
		init();
	}

	public static GeneratorCfg instance() {
		if (null == instance) {
			instance = new GeneratorCfg();
		}
		return instance;
	}

	private static Logger logger = LoggerFactory.getLogger(GeneratorCfg.class);

	// 主要提供基本功能模块代码生成。
	// 目录生成结构：{packageName}/{moduleName}/{dao,entity,service,web}/{subModuleName}/{className}

	// packageName
	// 包名，这里如果更改包名，请在applicationContext.xml和srping-mvc.xml中配置base-package、packagesToScan属性，来指定多个（共4处需要修改）。
	String packageName = "com.thinkgem.jeesite.modules";
	String moduleName = "rest"; // 模块名，例：sys
	String subModuleName = ""; // 子模块名（可选）
	String classAuthor = "Ever.Wang"; // 类作者，例：ThinkGem
	String functionName = "菜品"; // 功能名，例：用户

	File projectPath;
	String tplPath;
	// Java文件路径
	String javaPath;
	String viewPath;
	// 定义模板变量
	List<String> keywordList = new ArrayList<String>();
	Map<String, String> typeMap = new HashMap<String, String>();

	public void init() {
		try {
			initPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initKeyWords();
	}

	private void initPath() throws IOException {
		// 获取工程路径
		projectPath = new DefaultResourceLoader().getResource("").getFile();
		while (!new File(projectPath.getPath() + FILE_SEPERATOR + "src"
				+ FILE_SEPERATOR + "main").exists()) {
			projectPath = projectPath.getParentFile();
		}
		logger.info("Project Path: {}", projectPath);
		// 模板文件路径
		tplPath = StringUtils.replace(projectPath
				+ "/src/main/java/com/wyy/jframework/generator/template", "/",
				FILE_SEPERATOR);
		logger.info("Template Path: {}", tplPath);
		javaPath = StringUtils.replaceEach(projectPath + "/src/main/java/"
				+ StringUtils.lowerCase(packageName),
				new String[] { "/", "." }, new String[] { FILE_SEPERATOR,
						FILE_SEPERATOR });

		// 视图文件路径
		viewPath = StringUtils.replace(projectPath
				+ "/src/main/webapp/WEB-INF/views", "/", FILE_SEPERATOR);
		logger.info("View Path: {}", viewPath);

	}

	public String getEntityPath(String className) {
		return javaPath + FILE_SEPERATOR + moduleName + FILE_SEPERATOR
				+ "entity" + FILE_SEPERATOR
				+ StringUtils.lowerCase(subModuleName) + FILE_SEPERATOR
				+ StringUtils.firstToUpper(className) + ".java";
	}

	public String getDaoPath(String className) {
		return javaPath + FILE_SEPERATOR + moduleName + FILE_SEPERATOR + "dao"
				+ FILE_SEPERATOR + StringUtils.lowerCase(subModuleName)
				+ FILE_SEPERATOR + StringUtils.firstToUpper(className)
				+ "Dao.java";
	}

	public String getServicePath(String className) {
		return javaPath + FILE_SEPERATOR + moduleName + FILE_SEPERATOR
				+ "service" + FILE_SEPERATOR
				+ StringUtils.lowerCase(subModuleName) + FILE_SEPERATOR
				+ StringUtils.firstToUpper(className) + "Service.java";
	}

	public String getControllerPath(String className) {
		return javaPath + FILE_SEPERATOR + moduleName + FILE_SEPERATOR + "web"
				+ FILE_SEPERATOR + StringUtils.lowerCase(subModuleName)
				+ FILE_SEPERATOR + StringUtils.firstToUpper(className)
				+ "Controller.java";
	}

	public String getViewFormPath(String className) {
		return viewPath + FILE_SEPERATOR
				+ StringUtils.substringAfterLast(packageName, ".")
				+ FILE_SEPERATOR + moduleName + FILE_SEPERATOR
				+ StringUtils.lowerCase(subModuleName) + FILE_SEPERATOR
				+ className + "Form.jsp";
	}

	public String getViewListPath(String className) {
		return viewPath + FILE_SEPERATOR
				+ StringUtils.substringAfterLast(packageName, ".")
				+ FILE_SEPERATOR + moduleName + FILE_SEPERATOR
				+ StringUtils.lowerCase(subModuleName) + FILE_SEPERATOR
				+ className + "List.jsp";
	}

	public String getSqlPath(String tableName) {
		return javaPath + FILE_SEPERATOR + moduleName + FILE_SEPERATOR
				+ "table" + FILE_SEPERATOR
				+ StringUtils.lowerCase(subModuleName) + FILE_SEPERATOR
				+ tableName + ".sql";
	}

	public void initKeyWords() {
		keywordList = Arrays.asList("abstract", "assert", "boolean", "break",
				"byte", "case", "catch", "char", "class", "continue",
				"default", "do", "double", "else", "enum", "extends", "final",
				"finally", "float", "for", "if", "implements", "import",
				"instanceof", "int", "interface", "long", "native", "new",
				"package", "private", "protected", "public", "return",
				"strictfp", "short", "static", "super", "switch",
				"synchronized", "this", "throw", "throws", "transient", "try",
				"void", "volatile", "while");

		typeMap.put("bigint", "long");
		typeMap.put("varchar", "String");
		typeMap.put("char", "String");
		typeMap.put("int", "int");
		typeMap.put("text", "String");
	}

	public Map<String, String> getModel(String className) {
		Map<String, String> model = Maps.newHashMap();
		model.put("packageName", StringUtils.lowerCase(packageName));
		model.put("moduleName", StringUtils.lowerCase(moduleName));
		model.put("subModuleName", StringUtils.isNotBlank(subModuleName) ? "."
				+ StringUtils.lowerCase(subModuleName) : "");
		model.put("classAuthor",
				StringUtils.isNotBlank(classAuthor) ? classAuthor
						: "Auto Generated");
		model.put("classVersion", DateUtils.getDate());
		model.put("functionName", functionName);
		model.put(
				"urlPrefix",
				model.get("moduleName")
						+ (StringUtils.isNotBlank(subModuleName) ? "/"
								+ StringUtils.lowerCase(subModuleName) : "")
						+ "/" + className);
		model.put("viewPrefix",
				StringUtils.substringAfterLast(model.get("packageName"), ".")
						+ "/" + model.get("urlPrefix"));
		model.put(
				"permissionPrefix",
				model.get("moduleName")
						+ (StringUtils.isNotBlank(subModuleName) ? ":"
								+ StringUtils.lowerCase(subModuleName) : "")
						+ ":" + className);
		return model;
	}
}
