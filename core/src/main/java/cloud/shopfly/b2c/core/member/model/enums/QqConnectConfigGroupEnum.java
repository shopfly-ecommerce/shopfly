/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.member.model.enums;

/**
 * @author zjp
 * @version v7.0
 * @Description qq信任登录参数组枚举类
 * @ClassName QqConnectConfigGroupEnum
 * @since v7.0 下午8:05 2018/6/28
 */
public enum QqConnectConfigGroupEnum {
    /**
     * 网页端参数 （PC，WAP，微信网页端）
     */
    pc("网页端参数 （PC，WAP，微信网页端）"),
    /**
     * 原生-APP参数(安卓)
     */
    native_android("原生-APP参数(安卓)"),
    /**
     * 原生-APP参数(IOS)
     */
    native_ios("原生-APP参数(IOS)"),
    /**
     * RN-APP参数(安卓)
     */
    rn_android("RN-APP参数(安卓)"),
    /**
     * RN-APP参数(IOS)
     */
    rn_ios("RN-APP参数(IOS)");

    private String text;

    QqConnectConfigGroupEnum(String text) {
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
