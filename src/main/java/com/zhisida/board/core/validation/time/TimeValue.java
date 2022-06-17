
package com.zhisida.board.core.validation.time;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 校验日期格式 HH:mm:ss
 *
 * @author young-pastor
 */
@Documented
@Constraint(validatedBy = TimeValueValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface TimeValue {

    String message() default "日期格式不正确，正确格式应为HH:mm:ss";

    Class[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.FIELD, ElementType.PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        TimeValue[] value();
    }
}
