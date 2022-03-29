/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.promotion.tool.model.dto;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 活动DTO
 *
 * @author Snow create in 2018/7/9
 * @version v2.0
 * @since v7.0.0
 */
public class PromotionDTO {

    @ApiModelProperty(value="参与的活动ID")
    private Integer actId;

    @ApiModelProperty(value="商品商品")
    private Integer goodsId;

    @ApiModelProperty(value="购买的数量")
    private Integer num;

    public Integer getActId() {
        return actId;
    }

    public void setActId(Integer actId) {
        this.actId = actId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (o == null || getClass() != o.getClass()){
            return false;
        }

        PromotionDTO that = (PromotionDTO) o;

        return new EqualsBuilder()
                .append(actId, that.actId)
                .append(goodsId, that.goodsId)
                .append(num, that.num)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(actId)
                .append(goodsId)
                .append(num)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "PromotionDTO{" +
                "actId=" + actId +
                ", goodsId=" + goodsId +
                ", num=" + num +
                '}';
    }
}
