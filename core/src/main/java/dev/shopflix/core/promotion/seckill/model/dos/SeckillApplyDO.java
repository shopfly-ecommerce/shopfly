/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.promotion.seckill.model.dos;

import dev.shopflix.framework.database.annotation.Column;
import dev.shopflix.framework.database.annotation.Id;
import dev.shopflix.framework.database.annotation.PrimaryKeyField;
import dev.shopflix.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;


/**
 * 限时抢购申请实体
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-04-02 17:30:09
 */
@Table(name = "es_seckill_apply")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SeckillApplyDO implements Serializable {

    private static final long serialVersionUID = 2980175459354215L;

    /**
     * 主键ID
     */
    @Id(name = "apply_id")
    @ApiModelProperty(hidden = true)
    private Integer applyId;

    /**
     * 活动id
     */
    @Column(name = "seckill_id")
    @ApiModelProperty(name = "seckill_id", value = "活动id", required = true)
    private Integer seckillId;

    /**
     * 时刻
     */
    @Column(name = "time_line")
    @ApiModelProperty(name = "time_line", value = "时刻")
    private Integer timeLine;

    /**
     * 活动开始日期
     */
    @Column(name = "start_day")
    @ApiModelProperty(name = "start_day", value = "活动开始日期")
    private Long startDay;

    /**
     * 商品ID
     */
    @Column(name = "goods_id")
    @ApiModelProperty(name = "goods_id", value = "商品ID")
    private Integer goodsId;

    /**
     * 商品名称
     */
    @Column(name = "goods_name")
    @ApiModelProperty(name = "goods_name", value = "商品名称")
    private String goodsName;

    /**
     * 价格
     */
    @Column(name = "price")
    @ApiModelProperty(name = "price", value = "价格")
    private Double price;

    /**
     * 售空数量
     */
    @Column(name = "sold_quantity")
    @ApiModelProperty(name = "sold_quantity", value = "售空数量")
    private Integer soldQuantity;

    @Column(name = "sales_num")
    @ApiModelProperty(name = "sales_num", value = "已售数量")
    private Integer salesNum;

    @Column(name = "original_price")
    @ApiModelProperty(name = "original_price", value = "商品原始价格")
    private Double originalPrice;


    @PrimaryKeyField
    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public Integer getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(Integer seckillId) {
        this.seckillId = seckillId;
    }

    public Integer getTimeLine() {
        return timeLine;
    }

    public void setTimeLine(Integer timeLine) {
        this.timeLine = timeLine;
    }

    public Long getStartDay() {
        return startDay;
    }

    public void setStartDay(Long startDay) {
        this.startDay = startDay;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(Integer soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Integer getSalesNum() {
        if (salesNum == null) {
            salesNum = 0;
        }
        return salesNum;
    }

    public void setSalesNum(Integer salesNum) {
        this.salesNum = salesNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SeckillApplyDO applyDO = (SeckillApplyDO) o;

        return new EqualsBuilder()
                .append(applyId, applyDO.applyId)
                .append(seckillId, applyDO.seckillId)
                .append(timeLine, applyDO.timeLine)
                .append(startDay, applyDO.startDay)
                .append(goodsId, applyDO.goodsId)
                .append(goodsName, applyDO.goodsName)
                .append(price, applyDO.price)
                .append(soldQuantity, applyDO.soldQuantity)
                .append(salesNum, applyDO.salesNum)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(applyId)
                .append(seckillId)
                .append(timeLine)
                .append(startDay)
                .append(goodsId)
                .append(goodsName)
                .append(price)
                .append(soldQuantity)
                .append(salesNum)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "SeckillApplyDO{" +
                "applyId=" + applyId +
                ", seckillId=" + seckillId +
                ", timeLine=" + timeLine +
                ", startDay=" + startDay +
                ", goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", price=" + price +
                ", soldQuantity=" + soldQuantity +
                ", salesNum=" + salesNum +
                '}';
    }
}
