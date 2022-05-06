/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.pagedata.constraint.annotation;

import cloud.shopfly.b2c.core.pagedata.constraint.validator.PageTypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author fk
 * @version v2.0
 * @Description: 页面类型验证
 * @date 2018/4/3 11:42
 * @since v7.0.0
 */
@Constraint(validatedBy = {PageTypeValidator.class})
@Documented
@Target( {ElementType.PARAMETER,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PageType {

    String message() default "不正确的页面类型";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
