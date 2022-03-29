/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.member.model.enums;

/**
 * @author zjp
 * @version v7.0
 * @Description 微博信任登录参数项枚举类
 * @ClassName WeiboConnectConfigItemEnum
 * @since v7.0 下午6:12 2018/6/28
 */
public enum WeiboConnectConfigItemEnum {
    /**
     *  app_key
     */
    app_key("app_key"),
    /**
     * app_secret
     */
    app_secret("app_secret");

    private String text;

    WeiboConnectConfigItemEnum(String text) {
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
