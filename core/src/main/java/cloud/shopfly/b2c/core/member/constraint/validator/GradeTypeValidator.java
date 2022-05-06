/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.member.constraint.validator;

import cloud.shopfly.b2c.core.member.constraint.annotation.GradeType;
import cloud.shopfly.b2c.core.member.model.enums.CommentGrade;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author fk
 * @version v2.0
 * @Description: GradeType 验证
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

