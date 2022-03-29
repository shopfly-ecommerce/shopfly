/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.aftersale.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 申请退货商品DTO
 *
 * @author zjp
 * @version v7.0
 * @since v7.0 上午10:29 2018/5/2
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ApplyReturnGoodsDTO implements Serializable {

    private static final long serialVersionUID = -9006487940153781080L;
    @ApiModelProperty(value = "商品id", name = "good_id")
    private Integer goodId;

    @ApiModelProperty(value = "产品id", name = "sku_id")
    private Integer skuId;

    @ApiModelProperty(value = "退货数量", name = "return_num")
    private Integer returnNum;

    public Integer getGoodId() {
        return goodId;
    }

    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getReturnNum() {
        return returnNum;
    }

    public void setReturnNum(Integer returnNum) {
        this.returnNum = returnNum;
    }

    @Override
    public String toString() {
        return "ApplyReturnGoodsDTO{" +
                "goods_id=" + goodId +
                ", sku_id=" + skuId +
                ", return_num=" + returnNum +
                '}';
    }
}
