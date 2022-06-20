
package com.zhisida.board.core.validator.dateortime;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 校验日期或时间格式 yyyy-MM-dd 或 HH:mm:ss
 *
 * @author young-pastor
 */
@Documented
@Constraint(validatedBy = DateOrTimeValueValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateOrTimeValue {

    String message() default "日期或时间格式不正确，正确格式应为yyyy-MM-dd 或 HH:mm:ss";

    Class[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.FIELD, ElementType.PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        DateOrTimeValue[] value();
    }
}
