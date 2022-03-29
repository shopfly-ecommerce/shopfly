/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.aftersale.model.vo;

import dev.shopflix.core.trade.order.model.vo.OrderDetailVO;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author zjp
 * @version v7.0
 * @Description 售后申请参数VO
 * @ClassName RefundApplyVO
 * @since v7.0 上午11:28 2018/7/4
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RefundApplyVO {

    @ApiModelProperty(name = "original_way", value = "是否支持原路退回")
    private String originalWay;

    @ApiModelProperty(name = "return_money", value = "退款金额")
    private Double returnMoney;

    @ApiModelProperty(name = "return_point", value = "退款积分")
    private Integer returnPoint;

    @ApiModelProperty(name = "order", value = "订单明细")
    private OrderDetailVO order;

    @ApiModelProperty(name = "sku_list", value = "货品列表")
    private List<RefundSkuVO> skuList;

    public String getOriginalWay() {
        return originalWay;
    }

    public void setOriginalWay(String originalWay) {
        this.originalWay = originalWay;
    }

    public Double getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(Double returnMoney) {
        this.returnMoney = returnMoney;
    }

    public Integer getReturnPoint() {
        return returnPoint;
    }

    public void setReturnPoint(Integer returnPoint) {
        this.returnPoint = returnPoint;
    }

    public OrderDetailVO getOrder() {
        return order;
    }

    public void setOrder(OrderDetailVO order) {
        this.order = order;
    }

    public List<RefundSkuVO> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<RefundSkuVO> skuList) {
        this.skuList = skuList;
    }

    @Override
    public String toString() {
        return "RefundApplyVO{" +
                "originalWay='" + originalWay + '\'' +
                ", returnMoney=" + returnMoney +
                ", returnPoint=" + returnPoint +
                ", order=" + order +
                ", skuList=" + skuList +
                '}';
    }
}
