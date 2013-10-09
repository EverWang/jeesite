DROP TABLE IF EXISTS `${table.name}`;
CREATE TABLE `${table.name}` (
<#list columnList as column>
    `${column.name}` ${column.type}<#if (column.length != "0") && (column.precision != "0")>(${column.length},${column.precision})<#elseif column.length != "0">(${column.length})</#if> <#if column.notnull == "1">NOT NULL</#if> <#if column.pk == "1">AUTO_INCREMENT</#if> <#if column.comment != "">COMMENT '${column.comment}',</#if>
</#list>
    PRIMARY KEY (`${table.pk}`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

