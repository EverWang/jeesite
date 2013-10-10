/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package ${packageName}.${moduleName}.entity${subModuleName};
<#assign ClassName=StringUtils.firstToUpper(className)>
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * ${functionName}Entity
 * @author ${classAuthor}
 * @version ${classVersion}
 */
@Entity
@Table(name = "${table.name}")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ${ClassName} extends DataEntity {
	
	private static final long serialVersionUID = 1L;

	<#list fieldList as field>  
    private ${field.type} ${field.name}; // ${field.comment}
    </#list>   

    <#list fieldList as field>
    <#assign upperFieldName = StringUtils.firstToUpper(field.name)>
    public ${field.type} get${upperFieldName}() {
        return ${field.name};
    }
    
    public void set${upperFieldName}(${field.type} ${field.name}) {
        this.${field.name} = ${field.name};
    }
    
    </#list> 
}


