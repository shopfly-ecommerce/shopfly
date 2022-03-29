/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.member.constraint.annotation;

/**
 * @author fk
 * @version v1.0
 * @Description: 商品评分
 * @date 2018/4/11 10:27
 * @since v7.0.0
 */

import dev.shopflix.core.member.constraint.validator.GradeTypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = {GradeTypeValidator.class})
@Documented
@Target( {ElementType.PARAMETER,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GradeType {

    String message() default "商品评分不正确";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
