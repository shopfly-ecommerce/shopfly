/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.member.model.vo;

import dev.shopflix.core.trade.sdk.model.OrderSkuDTO;
import io.swagger.annotations.ApiModelProperty;

/**
 * 商品skuVo 新增每个sku优惠了多少钱
 *
 * @author zh
 * @version v7.0
 * @date 18/7/24 下午4:57
 * @since v7.0
 */
public class ReceiptGoodsSkuVO  extends OrderSkuDTO {
    /**
     * 每一个sku优惠金额
     */
    @ApiModelProperty(value = "优惠金额")
    private Double discount;


    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "ReceiptGoodsSkuVO{" +
                "discount=" + discount +
                '}';
    }
}
