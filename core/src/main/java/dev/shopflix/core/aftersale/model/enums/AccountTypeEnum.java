/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.aftersale.model.enums;

/**
 * @author zjp
 * @version v7.0
 * @Description 退款账户类型枚举类
 * @ClassName AccountTypeEnum
 * @since v7.0 下午3:11 2018/6/7
 */
public enum AccountTypeEnum {

    //支付宝
    ALIPAY("支付宝"),
    //微信
    WEIXINPAY("微信"),
    //银行转账
    BANKTRANSFER("银行转账");

    private String description;

    AccountTypeEnum(String des) {
        this.description = des;
    }

    public String description() {
        return this.description;
    }

    public String value() {
        return this.name();
    }
}
