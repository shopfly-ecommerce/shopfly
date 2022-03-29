/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.goods.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 商品的积分兑换信息
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018年3月22日 上午11:43:40
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ExchangeVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4702156500500693965L;

    /**
     * 是否允许兑换
     */
    @ApiModelProperty(name = "enable_exchange", value = "是否允许积分兑换")
    private Integer enableExchange;

    /**
     * 兑换所需金额
     */
    @ApiModelProperty(name = "exchange_money", value = "兑换所需金额 ")
    private Double exchangeMoney;

    /**
     * 商品所属积分分类
     */
    @ApiModelProperty(name = "category_id", value = "积分兑换所属分类 ")
    private Integer categoryId;

    /**
     * 兑换所需积分
     */
    @ApiModelProperty(name = "exchange_point", value = "积分兑换使用的积分 ")
    private Integer exchangePoint;

    public Integer getEnableExchange() {
        return enableExchange;
    }

    public void setEnableExchange(Integer enableExchange) {
        this.enableExchange = enableExchange;
    }

    public double getExchangeMoney() {
        return exchangeMoney;
    }

    public void setExchangeMoney(double exchangeMoney) {
        this.exchangeMoney = exchangeMoney;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getExchangePoint() {
        return exchangePoint;
    }

    public void setExchangePoint(Integer exchangePoint) {
        this.exchangePoint = exchangePoint;
    }

    @Override
    public String toString() {
        return "ExchangeVO [enableExchange=" + enableExchange + ", exchangeMoney=" + exchangeMoney + ", categoryId="
                + categoryId + ", exchangePoint=" + exchangePoint + "]";
    }


}
