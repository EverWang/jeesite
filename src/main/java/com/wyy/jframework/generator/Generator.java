package com.wyy.jframework.generator;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.wyy.jframework.generator.entity.Column;
import com.wyy.jframework.generator.entity.Table;
import com.wyy.jframework.generator.impl.GeneratorCfg;

public interface Generator {
	GeneratorCfg generatorCfg = GeneratorCfg.instance() ;
	Map<Table, List<Column>> getTableMapFromExcel();

	void generateJava(Map<Table, List<Column>> tableMap) throws IOException;

	void generateSQL(Map<Table, List<Column>> tableMap) throws IOException;

}