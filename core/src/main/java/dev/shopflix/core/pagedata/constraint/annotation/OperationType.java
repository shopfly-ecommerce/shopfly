/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.pagedata.constraint.annotation;

import dev.shopflix.core.pagedata.constraint.validator.OperationTypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author fk
 * @version v2.0
 * @Description: 操作类型验证
 * @date 2018/4/311:42
 * @since v7.0.0
 */
@Constraint(validatedBy = {OperationTypeValidator.class})
@Documented
@Target( {ElementType.PARAMETER,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationType {

    String message() default "不正确的操作类型";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
