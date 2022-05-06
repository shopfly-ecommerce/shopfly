/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.pagedata.constraint.validator;

import cloud.shopfly.b2c.core.pagedata.constraint.annotation.ClientMobileType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

/**
 * @author fk
 * @version v2.0
 * @Description: ClientMobileType 验证
 * @date 2018/4/3 11:44
 * @since v7.0.0
 */
public class ClientMobileValidator implements ConstraintValidator<ClientMobileType, String> {

    private final String[] ALL_STATUS = {"PC", "MOBILE"};

    @Override
    public void initialize(ClientMobileType status) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Arrays.asList(ALL_STATUS).contains(value);
    }
}
