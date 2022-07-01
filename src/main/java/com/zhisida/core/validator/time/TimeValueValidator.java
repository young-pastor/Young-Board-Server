
package com.zhisida.core.validator.time;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 校验日期格式 HH:mm:ss
 *
 * @author Young-Pastor
 */
public class TimeValueValidator implements ConstraintValidator<TimeValue, String> {

    @Override
    public boolean isValid(String dateValue, ConstraintValidatorContext context) {
        //为空则放过，因为在此校验之前会加入@NotNull或@NotBlank校验
        if (ObjectUtil.isEmpty(dateValue)) {
            return true;
        }
        //长度不对直接返回
        if (dateValue.length() != DatePattern.NORM_TIME_PATTERN.length()) {
            return false;
        }
        try {
            DateUtil.parseTime(dateValue);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
