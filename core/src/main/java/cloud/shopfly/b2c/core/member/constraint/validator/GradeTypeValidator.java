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
package cloud.shopfly.b2c.core.member.constraint.validator;

import cloud.shopfly.b2c.core.member.constraint.annotation.GradeType;
import cloud.shopfly.b2c.core.member.model.enums.CommentGrade;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author fk
 * @version v2.0
 * @Description: GradeType validation
 * @date 2018/4/3 11:44
 * @since v7.0.0
 */
public class GradeTypeValidator implements ConstraintValidator<GradeType, String> {

    @Override
    public void initialize(GradeType status) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        try {
            CommentGrade.valueOf(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }

    }

}

