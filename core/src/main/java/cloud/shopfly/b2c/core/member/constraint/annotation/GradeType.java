/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cloud.shopfly.b2c.core.member.constraint.annotation;

/**
 * @author fk
 * @version v1.0
 * @Description: 商品评分
 * @date 2018/4/11 10:27
 * @since v7.0.0
 */

import cloud.shopfly.b2c.core.member.constraint.validator.GradeTypeValidator;

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
