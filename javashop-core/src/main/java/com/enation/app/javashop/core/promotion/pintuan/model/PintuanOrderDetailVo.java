/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.promotion.pintuan.model;

import com.enation.app.javashop.framework.util.DateUtil;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Created by kingapex on 2019-02-12.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-02-12
 */
public class PintuanOrderDetailVo extends PintuanOrder {

    @ApiModelProperty(name = "origin_price", value = "原价")
    private Double originPrice;

    @ApiModelProperty(name = "sales_price", value = "拼团价")
    private Double salesPrice;

    @ApiModelProperty(name = "left_time", value = "拼团活动剩余秒数")
    private Long leftTime;


    @Override
    public String toString() {
        return "PintuanOrderDetailVo{" +
                "originPrice=" + originPrice +
                ", salesPrice=" + salesPrice +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PintuanOrderDetailVo that = (PintuanOrderDetailVo) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(getOriginPrice(), that.getOriginPrice())
                .append(getSalesPrice(), that.getSalesPrice())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(getOriginPrice())
                .append(getSalesPrice())
                .toHashCode();
    }

    public Double getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(Double originPrice) {
        this.originPrice = originPrice;
    }

    public Double getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(Double salesPrice) {
        this.salesPrice = salesPrice;
    }

    public Long getLeftTime() {
        Long now = DateUtil.getDateline();
        Long leftTime = getEndTime() - now;
        if (leftTime < 0) {
            leftTime = 0L;
        }
        return leftTime;
    }

    public void setLeftTime(Long leftTime) {
        this.leftTime = leftTime;
    }
}
