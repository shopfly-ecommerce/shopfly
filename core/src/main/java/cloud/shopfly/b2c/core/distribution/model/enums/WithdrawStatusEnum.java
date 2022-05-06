/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.distribution.model.enums;

import cloud.shopfly.b2c.framework.util.StringUtil;

/**
 * 提现审核状态枚举
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/25 上午11:37
 */

public enum WithdrawStatusEnum {
    //提现状态
    APPLY("申请中"), VIA_AUDITING("审核成功"), FAIL_AUDITING("审核失败"),
    TRANSFER_ACCOUNTS("已转账");

    private String name;

    WithdrawStatusEnum(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public static String codeToName(String code) {
        if (StringUtil.isEmpty(code)) {
            return WithdrawStatusEnum.APPLY.getName();
        }
        if (code.equals(WithdrawStatusEnum.APPLY.name())) {
            return WithdrawStatusEnum.APPLY.getName();
        } else if (code.equals(WithdrawStatusEnum.VIA_AUDITING.name())) {
            return WithdrawStatusEnum.VIA_AUDITING.getName();
        } else if (code.equals(WithdrawStatusEnum.FAIL_AUDITING.name())) {
            return WithdrawStatusEnum.FAIL_AUDITING.getName();
        } else {
            return WithdrawStatusEnum.TRANSFER_ACCOUNTS.getName();
        }
    }

}
