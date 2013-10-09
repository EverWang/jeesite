/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.wyy.jframework.generator.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.common.utils.FreeMarkers;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.wyy.jframework.generator.Generator;
import com.wyy.jframework.generator.entity.Column;
import com.wyy.jframework.generator.entity.Field;
import com.wyy.jframework.generator.entity.Table;
import com.wyy.jframework.generator.excel.DBExcelMapperPOI;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 代码生成器
 * 
 * @author ThinkGem
 * @version 2013-06-21
 */
public class GeneratorImpl implements Generator {
	private Configuration cfg;

	private GeneratorImpl() {
		cfg = new Configuration();
		// 代码模板配置
		try {
			cfg.setDirectoryForTemplateLoading(new File(
					Generator.generatorCfg.tplPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static Generator instance;

	public static Generator instance() {
		if (null == instance) {
			instance = new GeneratorImpl();
		}
		return instance;
	}

	private static Logger logger = LoggerFactory.getLogger(GeneratorImpl.class);

	/**
	 * 将内容写入文件
	 * 
	 * @param content
	 * @param filePath
	 */
	public static void writeFile(String content, String filePath) {
		try {
			if (FileUtils.createFile(filePath)) {
				FileWriter fileWriter = new FileWriter(filePath, true);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				bufferedWriter.write(content);
				bufferedWriter.close();
				fileWriter.close();
			} else {
				logger.info("生成失败，文件已存在！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void generateSQL(Map<Table, List<Column>> tableMap)
			throws IOException {

		for (Map.Entry<Table, List<Column>> entry : tableMap.entrySet()) {
			Table table = entry.getKey();
			String tableName = table.getName();
			List<Column> columnList = entry.getValue();

			Map<String, Object> dataMap = new HashMap<String, Object>();

			dataMap.put("table", table);
			dataMap.put("columnList", columnList);
			Template template = cfg.getTemplate("table.ftl");
			String content = FreeMarkers.renderTemplate(template, dataMap);

			String filePath = generatorCfg.getSqlPath(tableName);
			writeFile(content, filePath);
			logger.info("Table: {}", filePath);
		}
	}

	@Override
	public void generateJava(Map<Table, List<Column>> tableMap)
			throws IOException {

		for (Map.Entry<Table, List<Column>> entry : tableMap.entrySet()) {
			Table table = entry.getKey();
			// 表名
			String tableName = table.getName();
			// 类名-首字母为小写
			String className = StringUtils.toCamelhump(tableName);

			List<Column> columnList = entry.getValue();
			List<Field> fieldList = transformFieldList(columnList);

			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("table", table);
			dataMap.put("className", className);
			dataMap.put("fieldList", fieldList);
			dataMap.put("StringUtils", new StringUtils());
			dataMap.put("model", generatorCfg.getModel(className));

			generateEntity(className, dataMap);

			generateDao(className, dataMap);

			generateService(className, dataMap);

			generateController(className, dataMap);

			generateViewForm(className, dataMap);

			generateViewList(className, dataMap);

			logger.info("Generate Success.");
		}
	}

	private void generateEntity(String className, Map<String, Object> dataMap)
			throws IOException {
		// 生成Entity
		Template template = cfg.getTemplate("entity.ftl");
		String content = FreeMarkers.renderTemplate(template, dataMap);

		String filePath = generatorCfg.getEntityPath(className);
		writeFile(content, filePath);
		logger.info("Entity: {}", filePath);
	}

	private void generateDao(String className, Map<String, Object> dataMap)
			throws IOException {
		Template template;
		String content;
		String filePath;
		// 生成 Dao
		template = cfg.getTemplate("dao.ftl");
		content = FreeMarkers.renderTemplate(template, dataMap);
		filePath = generatorCfg.getDaoPath(className);
		writeFile(content, filePath);
		logger.info("Dao: {}", filePath);
	}

	private void generateService(String className, Map<String, Object> dataMap)
			throws IOException {
		Template template;
		String content;
		String filePath;
		// 生成 Service
		template = cfg.getTemplate("service.ftl");
		content = FreeMarkers.renderTemplate(template, dataMap);
		filePath = generatorCfg.getServicePath(className);
		writeFile(content, filePath);
		logger.info("Service: {}", filePath);
	}

	private void generateController(String className,
			Map<String, Object> dataMap) throws IOException {
		Template template;
		String content;
		String filePath;
		// 生成 Controller
		template = cfg.getTemplate("controller.ftl");
		content = FreeMarkers.renderTemplate(template, dataMap);
		filePath = generatorCfg.getControllerPath(className);
		writeFile(content, filePath);
		logger.info("Controller: {}", filePath);
	}

	private void generateViewForm(String className, Map<String, Object> dataMap)
			throws IOException {
		Template template;
		String content;
		String filePath;
		// 生成 ViewForm
		template = cfg.getTemplate("viewForm.ftl");
		content = FreeMarkers.renderTemplate(template, dataMap);
		filePath = generatorCfg.getViewFormPath(className);
		writeFile(content, filePath);
		logger.info("ViewForm: {}", filePath);
	}

	private void generateViewList(String className, Map<String, Object> dataMap)
			throws IOException {
		Template template;
		String content;
		String filePath;
		// 生成 ViewList
		template = cfg.getTemplate("viewList.ftl");
		content = FreeMarkers.renderTemplate(template, dataMap);
		filePath = generatorCfg.getViewListPath(className);
		writeFile(content, filePath);
		logger.info("ViewList: {}", filePath);
	}

	private List<Field> transformFieldList(List<Column> columnList) {
		List<Field> fieldList = new ArrayList<Field>(columnList.size());
		for (Column column : columnList) {
			String fieldName = this.transformFieldName(column.getName());
			String fieldType = this.transformFieldType(column.getType());
			String fieldComment = column.getComment();
			fieldList.add(new Field(fieldName, fieldType, fieldComment));
		}
		return fieldList;
	}

	private String transformFieldName(String columnName) {
		String fieldName;
		if (generatorCfg.keywordList.contains(columnName)) {
			fieldName = columnName + "_";
		} else {
			fieldName = columnName;
		}
		return StringUtils.toCamelhump(fieldName);
	}

	private String transformFieldType(String columnType) {
		String fieldType;
		if (generatorCfg.typeMap.containsKey(columnType)) {
			fieldType = generatorCfg.typeMap.get(columnType);
		} else {
			fieldType = "String";
		}
		return fieldType;
	}

	public Map<Table, List<Column>> getTableMapFromExcel() {
		String inputPath = StringUtils.replace(generatorCfg.projectPath
				+ "/src/main/java/com/wyy/jframework/generator/excel", "/",
				generatorCfg.FILE_SEPERATOR)
				+ generatorCfg.FILE_SEPERATOR + "db.xls";
		return DBExcelMapperPOI.instance().createTableMap(inputPath);
	}
}
