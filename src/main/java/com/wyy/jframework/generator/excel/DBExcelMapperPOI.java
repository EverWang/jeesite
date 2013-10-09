/*****************************************************************
 *@ProjectName jeesite
 *@FileName DBExcelMapperPOI.java
 *@Author WYY
 *@Date 2013-9-26
 *@Copyright 2012-2022  wyyft@163.com All rights reserved.
 *
 *****************************************************************/
package com.wyy.jframework.generator.excel;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.wyy.jframework.generator.entity.Column;
import com.wyy.jframework.generator.entity.Table;

/**
 * 使用POI读取数据库的Excel转换成tableMap，替换原来的jxl，实现统一 TODO
 * 
 * @Author WYY
 * @Description
 */
public class DBExcelMapperPOI {
	private static DBExcelMapperPOI dbExcelMapperPOI;

	private DBExcelMapperPOI() {

	}

	public static DBExcelMapperPOI instance() {
		if (null == dbExcelMapperPOI) {
			dbExcelMapperPOI = new DBExcelMapperPOI();
		}
		return dbExcelMapperPOI;

	}

	private static Logger logger = LoggerFactory
			.getLogger(DBExcelMapperPOI.class);

	public Map<Table, List<Column>> createTableMap(String inputPath) {
		Map<Table, List<Column>> tableMap = new LinkedHashMap<Table, List<Column>>();
		try {
			FileInputStream fis = new FileInputStream(inputPath);
			HSSFWorkbook workbook = new HSSFWorkbook(fis);
			for (int i = 1; i < workbook.getNumberOfSheets(); i++) {
				HSSFSheet sheet = workbook.getSheetAt(i);
				String tableName = sheet.getSheetName().toLowerCase();
				String tablePK = "";
				List<Column> columnList = new ArrayList<Column>();
				for (int row = 1; row < sheet.getLastRowNum(); row++) {
					/**
					 * String name = sheet.getCell(0, row).getContents().trim();
					 * String type = sheet.getCell(1, row).getContents().trim();
					 * String length = sheet.getCell(2,
					 * row).getContents().trim(); String precision =
					 * sheet.getCell(3, row).getContents().trim(); String
					 * notnull = sheet.getCell(4, row).getContents().trim();
					 * String pk = sheet.getCell(5, row).getContents().trim();
					 * String comment = sheet.getCell(6,
					 * row).getContents().trim();
					 */
					HSSFRow curRow = sheet.getRow(row);
					String name = curRow.getCell(0).getStringCellValue().trim();
					String type = curRow.getCell(1).getStringCellValue().trim();
					String length = Integer.toString(
							(int) (curRow.getCell(2).getNumericCellValue()))
							.trim();
					String precision = Integer.toString(
							(int) (curRow.getCell(3).getNumericCellValue()))
							.trim();
					String notnull = Integer.toString(
							(int) (curRow.getCell(4).getNumericCellValue()))
							.trim();
					String pk = Integer.toString(
							(int) (curRow.getCell(5).getNumericCellValue()))
							.trim();
					String comment = curRow.getCell(6).getStringCellValue()
							.trim();
					columnList.add(new Column(name, type, length, precision,
							notnull, pk, comment));
					if (StringUtils.isNotEmpty(pk)&&("1".equals(pk))) {
						tablePK = name;
					}
				}
				tableMap.put(new Table(tableName, tablePK), columnList);
			}
			fis.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
		return tableMap;
	}
}
