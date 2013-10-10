package com.wyy.jframework.generator.impl;

import java.util.Arrays;

public class MysqlKeyInfo extends DBKeyInfo {

	private static MysqlKeyInfo instance;

	private MysqlKeyInfo() {
		initKeyWords();
	}

	public static MysqlKeyInfo instance() {
		if (null == instance) {
			instance = new MysqlKeyInfo();
		}
		return instance;
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
}
