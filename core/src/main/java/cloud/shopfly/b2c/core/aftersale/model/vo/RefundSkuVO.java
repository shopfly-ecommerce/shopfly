/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.aftersale.model.vo;

import cloud.shopfly.b2c.core.trade.order.model.vo.OrderSkuVO;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * RefundSkuVO
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-12-18 上午9:04
 */
public class RefundSkuVO extends OrderSkuVO implements Serializable {

    @ApiModelProperty("单件可退款金额")
    private double refundPrice;

    @ApiModelProperty("最后一件商品可退款金额（针对购买多件相同商品）")
    private double lastRefundPrice;

    public double getRefundPrice() {
        return refundPrice;
    }

    public void setRefundPrice(double refundPrice) {
        this.refundPrice = refundPrice;
    }

    public double getLastRefundPrice() {
        return lastRefundPrice;
    }

    public void setLastRefundPrice(double lastRefundPrice) {
        this.lastRefundPrice = lastRefundPrice;
    }

    public RefundSkuVO() {

    }

    public RefundSkuVO(OrderSkuVO skuVO) {
        BeanUtils.copyProperties(skuVO, this);
    }

    @Override
    public String toString() {
        return "RefundSkuVO{" +
                "refundPrice=" + refundPrice +
                ", lastRefundPrice=" + lastRefundPrice +
                '}';
    }
}
