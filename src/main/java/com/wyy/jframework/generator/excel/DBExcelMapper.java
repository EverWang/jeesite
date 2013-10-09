/*****************************************************************
 *@ProjectName jeesite
 *@FileName DBExcelMapper.java
 *@Author WYY
 *@Date 2013-9-26
 *@Copyright 2012-2022  wyyft@163.com All rights reserved.
 *
 *****************************************************************/
package com.wyy.jframework.generator.excel;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.wyy.jframework.generator.entity.Column;
import com.wyy.jframework.generator.entity.Table;
import jxl.Sheet;
import jxl.Workbook;

/**
 * jxl实现的数据库Excel读取，转换成tableMap
 * @Author WYY
 * @Description 
 */
public class DBExcelMapper
{
    private static DBExcelMapper dbExcelMapper;
    private DBExcelMapper()
    {

    }

    public static DBExcelMapper instance()
    {
        if(null==dbExcelMapper)
        {
            dbExcelMapper = new DBExcelMapper();
        }
        return dbExcelMapper;

    }

    private static Logger logger = LoggerFactory.getLogger(DBExcelMapper.class);

    public Map<Table, List<Column>> createTableMap(String inputPath)
    {
        Map<Table, List<Column>> tableMap = new LinkedHashMap<Table, List<Column>>();
        try
        {
            File file = new File(inputPath);
            Workbook workbook = Workbook.getWorkbook(file);
            for (int i = 1; i < workbook.getNumberOfSheets(); i++)
            {
                Sheet sheet = workbook.getSheet(i);
                String tableName = sheet.getName().toLowerCase();
                String tablePK = "";
                List<Column> columnList = new ArrayList<Column>();
                for (int row = 1; row < sheet.getRows(); row++)
                {
                    String name = sheet.getCell(0, row).getContents().trim();
                    String type = sheet.getCell(1, row).getContents().trim();
                    String length = sheet.getCell(2, row).getContents().trim();
                    String precision = sheet.getCell(3, row).getContents().trim();
                    String notnull = sheet.getCell(4, row).getContents().trim();
                    String pk = sheet.getCell(5, row).getContents().trim();
                    String comment = sheet.getCell(6, row).getContents().trim();
                    columnList.add(new Column(name, type, length, precision, notnull, pk, comment));
                    if (StringUtils.isNotEmpty(pk))
                    {
                        tablePK = name;
                    }
                }
                tableMap.put(new Table(tableName, tablePK), columnList);
            }
            workbook.close();
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
        return tableMap;
    }
}
