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
package cloud.shopfly.b2c.core.pagedata.constraint.annotation;

import cloud.shopfly.b2c.core.pagedata.constraint.validator.ClientMobileValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author fk
 * @version v2.0
 * @Description: Type verification on both ends of the client
 * @date 2018/4/311:42
 * @since v7.0.0
 */
@Constraint(validatedBy = {ClientMobileValidator.class})
@Documented
@Target( {ElementType.PARAMETER,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ClientMobileType {

    String message() default "Incorrect client type";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
