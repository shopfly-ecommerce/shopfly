/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.goods.constraint.annotation;

/**
 * @author fk
 * @version v2.0
 * @Description: 标签关键字验证
 * @date 2018/4/1110:27
 * @since v7.0.0
 */

import cloud.shopfly.b2c.core.goods.constraint.validator.MarkTypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = {MarkTypeValidator.class})
@Documented
@Target( {ElementType.PARAMETER,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MarkType {

    String message() default "标签关键字不正确";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
