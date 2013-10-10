package com.wyy.jframework.generator.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.utils.StringUtils;

public abstract class DBKeyInfo {
	List<String> keywordList = new ArrayList<String>();
	Map<String, String> typeMap = new HashMap<String, String>();

	public String transformFieldName(String columnName) {
		String fieldName;
		if (keywordList.contains(columnName)) {
			fieldName = columnName + "_";
		} else {
			fieldName = columnName;
		}
		return StringUtils.toCamelhump(fieldName);
	}

	public String transformFieldType(String columnType) {
		String fieldType;
		if (typeMap.containsKey(columnType)) {
			fieldType = typeMap.get(columnType);
		} else {
			fieldType = "String";
		}
		return fieldType;
	}

	abstract void initKeyWords();
}
