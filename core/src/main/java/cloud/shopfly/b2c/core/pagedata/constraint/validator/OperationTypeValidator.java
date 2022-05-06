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
package cloud.shopfly.b2c.core.pagedata.constraint.validator;

import cloud.shopfly.b2c.core.pagedata.constraint.annotation.OperationType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

/**
 * @author fk
 * @version v2.0
 * @Description: OperationType 验证
 * @date 2018/4/3 11:44
 * @since v7.0.0
 */
public class OperationTypeValidator implements ConstraintValidator<OperationType, String> {

    private final String[] ALL_STATUS = {"URL", "GOODS","KEYWORD","SHOP","CATEGORY","TOPIC","NONE"};

    @Override
    public void initialize(OperationType status) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Arrays.asList(ALL_STATUS).contains(value);
    }
}
