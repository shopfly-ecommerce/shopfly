/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.goods.constraint.annotation;

import com.enation.app.javashop.core.goods.constraint.validator.SortTypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author fk
 * @version v2.0
 * @Description: 排序关键字验证
 * @date 2018/4/311:42
 * @since v7.0.0
 */
@Constraint(validatedBy = {SortTypeValidator.class})
@Documented
@Target( {ElementType.PARAMETER,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SortType {

    String message() default "不正确的状态 , 应该是 'up', 'down'其中之一";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
