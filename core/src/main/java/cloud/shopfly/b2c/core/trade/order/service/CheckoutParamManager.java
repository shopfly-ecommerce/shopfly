/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.trade.order.service;

import cloud.shopfly.b2c.core.trade.order.model.enums.PaymentTypeEnum;
import cloud.shopfly.b2c.core.trade.order.model.vo.ReceiptVO;
import cloud.shopfly.b2c.core.trade.order.model.vo.CheckoutParamVO;

/**
 * 结算参数 业务层接口
 *
 * @author Snow create in 2018/4/8
 * @version v2.0
 * @since v7.0.0
 */
public interface CheckoutParamManager {

    /**
     * 获取订单的创建参数<br>
     * 如果没有设置过参数，则用默认
     *
     * @return 结算参数
     */
    CheckoutParamVO getParam();


    /**
     * 设置收货地址id
     *
     * @param addressId 收货地址id
     */
    void setAddressId(Integer addressId);


    /**
     * 设置支付式
     *
     * @param paymentTypeEnum 支付方式{@link PaymentTypeEnum}
     */
    void setPaymentType(PaymentTypeEnum paymentTypeEnum);


    /**
     * 设置发票
     *
     * @param receipt 发票vo {@link  ReceiptVO }
     */
    void setReceipt(ReceiptVO receipt);

    /**
     * 取消发票
     */
    void deleteReceipt();


    /**
     * 设置送货时间
     *
     * @param receiveTime 送货时间
     */
    void setReceiveTime(String receiveTime);


    /**
     * 设置订单备注
     *
     * @param remark 订单备注
     */
    void setRemark(String remark);

    /**
     * 设置订单来源
     *
     * @param clientType 客户端来源
     */
    void setClientType(String clientType);


    /**
     * 批量设置所有参数
     *
     * @param param 结算参数 {@link CheckoutParamVO}
     */
    void setAll(CheckoutParamVO param);

    /**
     * 检测是否支持货到付款
     * @param paymentTypeEnum
     */
    void checkCod(PaymentTypeEnum paymentTypeEnum);

}
