package com.wyy.jframework.generator.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.wyy.jframework.generator.Generator;
import com.wyy.jframework.generator.entity.Column;
import com.wyy.jframework.generator.entity.Table;

public class GeneratorMain {
	private static Logger logger = LoggerFactory.getLogger(GeneratorMain.class);

	public static void main(String[] args) throws Exception {
		// 是否启用生成工具
		Boolean isEnable = true;

		// ========== ↑↑↑↑↑↑ 执行前请修改参数GeneratorCfg，谨慎执行。↑↑↑↑↑↑ ====================

		if (!isEnable) {
			logger.error("请启用代码生成工具，设置参数：isEnable = true");
			return;
		}

		if (StringUtils.isBlank(Generator.generatorCfg.moduleName)
				|| StringUtils.isBlank(Generator.generatorCfg.moduleName)
				|| StringUtils.isBlank(Generator.generatorCfg.functionName)) {
			logger.error("参数设置错误：包名、模块名、类名、功能名不能为空。");
			return;
		}
		
		
		Generator generator = GeneratorImpl.instance();
		Map<Table, List<Column>> tableMap = generator.getTableMapFromExcel();
		generator.generateSQL(tableMap);
		generator.generateJava(tableMap);

	}
}
