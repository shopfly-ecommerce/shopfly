/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.member.model.enums;

/**
 * @author zjp
 * @version v7.0
 * @Description 微信信任登录参数项枚举类
 * @ClassName WechatConnectConfigItemEnm
 * @since v7.0 下午5:48 2018/6/28
 */
public enum WechatConnectConfigItemEnm {
    /**
     *  app_id
     */
    app_id("app_id"),
    /**
     *  app_key
     */
    app_key("app_key");


    private String text;

    WechatConnectConfigItemEnm(String text) {
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
