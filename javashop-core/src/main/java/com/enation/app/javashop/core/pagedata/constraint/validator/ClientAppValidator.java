/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.pagedata.constraint.validator;

import com.enation.app.javashop.core.pagedata.constraint.annotation.ClientAppType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

/**
 * @author fk
 * @version v2.0
 * @Description: ClientAppType 验证
 * @date 2018/4/3 11:44
 * @since v7.0.0
 */
public class ClientAppValidator implements ConstraintValidator<ClientAppType, String> {

    private final String[] ALL_STATUS = {"PC", "WAP","APP"};

    @Override
    public void initialize(ClientAppType status) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Arrays.asList(ALL_STATUS).contains(value);
    }
}
