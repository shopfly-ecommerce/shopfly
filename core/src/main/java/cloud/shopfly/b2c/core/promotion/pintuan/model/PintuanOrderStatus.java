/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.promotion.pintuan.model;

/**
 * Created by kingapex on 2019-01-24.
 * 拼团订单状态
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-01-24
 */
public enum PintuanOrderStatus {

    /**
     * 新订单
     */
    new_order,

    /**
     * 待成团
     */
    wait,

    /**
     * 已经支付
     */
    pay_off,

    /**
     * 已成团
     */
    formed,

    /**
     * 已取消
     */
    cancel
}
