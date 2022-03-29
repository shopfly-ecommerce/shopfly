/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.member.model.enums;

/**
 * @author zjp
 * @version v7.0
 * @Description 支付宝信任登录参数组枚举类
 * @ClassName AlipayConnectConfigGroupEnum
 * @since v7.0 下午8:05 2018/6/28
 */
public enum AlipayConnectConfigGroupEnum {
    /**
     * 网页端参数 （PC，WAP）参数
     */
    pc("网页端参数 （PC，WAP）参数"),
    /**
     * app端参数
     */
    app("app端参数");

    private String text;

    AlipayConnectConfigGroupEnum(String text) {
        this.text = text;

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String value() {
        return this.name();
    }
}
