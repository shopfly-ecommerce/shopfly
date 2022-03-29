/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.member.model.enums;

/**
 * @author zjp
 * @version v7.0
 * @Description 信任登录类型枚举类
 * @ClassName ConnectUserGenderEnum
 * @since v7.0 上午10:35 2018/6/6
 */
public enum ConnectTypeEnum {
    //QQ联合登录
    QQ("QQ"),
    //QQ H5 登陆openid
    QQ_OPENID("QQ H5 登陆openid"),
    //QQ APP 登陆openid
    QQ_APP("QQ APP 登陆openid"),
    //微博联合登录
    WEIBO("微博联合登录"),
    //微信联合登录
    WECHAT("微信联合登录"),
    // 微信小程序联合登录
    WECHAT_MINI("微信小程序联合登录"),
    //微信H5登录openid
    WECHAT_OPENID("微信H5登录 openid"),
    //微信APP登录openid
    WECHAT_APP("微信APP登录 openid"),
    //支付宝登录
    ALIPAY("支付宝登录"),
    //支付宝登录
    APPLEID("IOS苹果登录登录");

    private String description;

    ConnectTypeEnum(String des) {
        this.description = des;
    }

    public String description() {
        return this.description;
    }

    public String value() {
        return this.name();
    }

}
