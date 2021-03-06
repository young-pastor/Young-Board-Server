
package com.zhisida.core.validator.unique;

import cn.hutool.core.util.ObjectUtil;
import com.zhisida.core.pojo.base.param.BaseParam;
import com.zhisida.core.pojo.base.validate.UniqueValidateParam;
import com.zhisida.core.context.group.RequestGroupContext;
import com.zhisida.core.context.group.RequestParamIdContext;
import com.zhisida.core.context.system.SystemContextHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 验证表的的某个字段值是否在是唯一值
 *
 * @author Young-Pastor
 */
public class TableUniqueValueValidator implements ConstraintValidator<TableUniqueValue, String> {

    private String tableName;

    private String columnName;

    private boolean excludeLogicDeleteItems;

    private String logicDeleteFieldName;

    private String logicDeleteValue;

    @Override
    public void initialize(TableUniqueValue constraintAnnotation) {
        this.tableName = constraintAnnotation.tableName();
        this.columnName = constraintAnnotation.columnName();
        this.excludeLogicDeleteItems = constraintAnnotation.excludeLogicDeleteItems();
        this.logicDeleteFieldName = constraintAnnotation.logicDeleteFieldName();
        this.logicDeleteValue = constraintAnnotation.logicDeleteValue();
    }

    @Override
    public boolean isValid(String fieldValue, ConstraintValidatorContext context) {

        if (ObjectUtil.isNull(fieldValue)) {
            return true;
        }

        Class<?> validateGroupClass = RequestGroupContext.get();

        // 如果属于add group，则校验库中所有行
        if (BaseParam.add.class.equals(validateGroupClass)) {
            return SystemContextHolder.me().tableUniValueFlag(createAddParam(fieldValue));
        }

        // 如果属于edit group，校验时需要排除当前修改的这条记录
        if (BaseParam.edit.class.equals(validateGroupClass)) {
            return SystemContextHolder.me().tableUniValueFlag(createEditParam(fieldValue));
        }

        // 默认校验所有的行
        return SystemContextHolder.me().tableUniValueFlag(createAddParam(fieldValue));
    }

    /**
     * 创建校验新增的参数
     *
     * @author Young-Pastor
     */
    private UniqueValidateParam createAddParam(String fieldValue) {
        return UniqueValidateParam.builder()
                .tableName(tableName)
                .columnName(columnName)
                .value(fieldValue)
                .excludeCurrentRecord(Boolean.FALSE)
                .excludeLogicDeleteItems(excludeLogicDeleteItems)
                .logicDeleteFieldName(logicDeleteFieldName)
                .logicDeleteValue(logicDeleteValue).build();
    }

    /**
     * 创建修改的参数校验
     *
     * @author Young-Pastor
     */
    private UniqueValidateParam createEditParam(String fieldValue) {
        return UniqueValidateParam.builder()
                .tableName(tableName)
                .columnName(columnName)
                .value(fieldValue)
                .excludeCurrentRecord(Boolean.TRUE)
                .id(RequestParamIdContext.get())
                .excludeLogicDeleteItems(excludeLogicDeleteItems)
                .logicDeleteFieldName(logicDeleteFieldName)
                .logicDeleteValue(logicDeleteValue).build();
    }

}
