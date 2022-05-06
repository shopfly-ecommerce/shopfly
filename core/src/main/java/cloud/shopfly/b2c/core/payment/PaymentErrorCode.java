/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.payment;

/**
 * 支付异常码
 * Created by kingapex on 2018/3/13.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/3/13
 */
public enum PaymentErrorCode {

    /**
     * 不存在的交易
     */
    E500("不存在的交易"),
    /**
     * 不存在的支付方式
     */
    E501("不存在的支付方式"),
    /**
     * 未开启的支付方式
     */
    E502("未开启的支付方式"),
    /**
     * 支付回调验证失败
     */
    E503("支付回调验证失败"),
    /**
     * 支付账单不存在
     */
    E504("支付账单不存在"),
    /**
     * 支付方式参数不正确
     */
    E505("支付方式参数不正确"),
    /**
     * 订单状态不正确，无法支付
     */
    E506("订单状态不正确无法支付"),

    /**
     * 没有找到适合的回调器
     */
    E507("没有找到适合的回调器"),

    E509("openid不能为空");

    private String describe;

    PaymentErrorCode(String des) {
        this.describe = des;
    }

    /**
     * 获取异常码
     *
     * @return
     */
    public String code() {
        return this.name().replaceAll("E", "");
    }

    public String getDescribe() {
        return this.describe;
    }
}
