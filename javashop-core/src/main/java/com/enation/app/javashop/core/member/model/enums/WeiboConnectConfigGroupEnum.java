/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.member.model.enums;

/**
 * @author zjp
 * @version v7.0
 * @Description 微博信任登录参数组枚举类
 * @ClassName WeiboConnectConfigGroupEnum
 * @since v7.0 下午8:05 2018/6/28
 */
public enum WeiboConnectConfigGroupEnum {
    /**
     * 网页端参数 （PC，WAP，微信网页端）
     */
    pc("网页端参数 （PC，WAP，微信网页端）"),
    /**
     * 原生-APP参数
     */
    app("原生-APP参数"),
    /**
     * RN-APP参数
     */
    rn("RN-APP参数");


    private String text;

    WeiboConnectConfigGroupEnum(String text) {
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
