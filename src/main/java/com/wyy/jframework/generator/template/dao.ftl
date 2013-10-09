/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package ${model.packageName}.${model.moduleName}.dao${model.subModuleName};
<#assign ClassName=StringUtils.firstToUpper(className)>
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.common.persistence.BaseDao;
import com.thinkgem.jeesite.common.persistence.BaseDaoImpl;
import ${model.packageName}.${model.moduleName}.entity${model.subModuleName}.${ClassName};

/**
 * ${model.functionName}DAO接口
 * @author ${model.classAuthor}
 * @version ${model.classVersion}
 */
public interface ${ClassName}Dao extends ${ClassName}DaoCustom, CrudRepository<${ClassName}, Long> {

	@Modifying
	@Query("update ${ClassName} set delFlag='" + ${ClassName}.DEL_FLAG_DELETE + "' where id = ?1")
	public int deleteById(Long id);
	
}

/**
 * DAO自定义接口
 * @author ${model.classAuthor}
 */
interface ${ClassName}DaoCustom extends BaseDao<${ClassName}> {

}

/**
 * DAO自定义接口实现
 * @author ${model.classAuthor}
 */
@Component
class ${ClassName}DaoImpl extends BaseDaoImpl<${ClassName}> implements ${ClassName}DaoCustom {

}
