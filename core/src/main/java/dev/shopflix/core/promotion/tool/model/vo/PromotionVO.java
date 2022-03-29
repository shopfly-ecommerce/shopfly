/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.promotion.tool.model.vo;


import dev.shopflix.core.promotion.exchange.model.dos.ExchangeDO;
import dev.shopflix.core.promotion.fulldiscount.model.dos.FullDiscountGiftDO;
import dev.shopflix.core.promotion.fulldiscount.model.vo.FullDiscountVO;
import dev.shopflix.core.promotion.groupbuy.model.vo.GroupbuyGoodsVO;
import dev.shopflix.core.promotion.halfprice.model.vo.HalfPriceVO;
import dev.shopflix.core.promotion.minus.model.vo.MinusVO;
import dev.shopflix.core.promotion.seckill.model.vo.SeckillGoodsVO;
import dev.shopflix.core.promotion.tool.model.enums.PromotionTypeEnum;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * 活动商品对照表
 *
 * @author Snow
 * @version v1.0 2017年08月22日
 * @since v6.4
 */
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PromotionVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4796645552318671313L;

    @ApiModelProperty(value = "商品id")
    private Integer goodsId;

    @ApiModelProperty(value = "商品图片")
    private String thumbnail;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "货品id")
    private Integer skuId;

    @ApiModelProperty(value = "活动开始时间")
    private Long startTime;

    @ApiModelProperty(value = "活动结束时间")
    private Long endTime;

    @ApiModelProperty(value = "活动id")
    private Integer activityId;

    /**
     * 由此字段识别具体的活动类型
     * 此字段对应的是一个枚举值
     *
     * @see PromotionTypeEnum
     */
    @ApiModelProperty(value = "活动工具类型")
    private String promotionType;

    @ApiModelProperty(value = "活动名称")
    private String title;

    @ApiModelProperty(value = "积分兑换对象")
    private ExchangeDO exchange;

    @ApiModelProperty(value = "团购活动对象")
    private GroupbuyGoodsVO groupbuyGoodsVO;

    @ApiModelProperty(value = "满优惠活动")
    private FullDiscountVO fullDiscountVO;

    @ApiModelProperty(value = "满赠的赠品VO")
    private FullDiscountGiftDO fullDiscountGift;

    @ApiModelProperty(value = "单品立减活动")
    private MinusVO minusVO;

    @ApiModelProperty(value = "第二件半价活动")
    private HalfPriceVO halfPriceVO;

    @ApiModelProperty(value = "限时抢购活动")
    private SeckillGoodsVO seckillGoodsVO;


    @ApiModelProperty(value = "售空数量")
    private Integer num;

    @ApiModelProperty(value = "活动价格")
    private Double price;

    public PromotionVO() {
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ExchangeDO getExchange() {
        return exchange;
    }

    public void setExchange(ExchangeDO exchange) {
        this.exchange = exchange;
    }

    public FullDiscountVO getFullDiscountVO() {
        return fullDiscountVO;
    }

    public void setFullDiscountVO(FullDiscountVO fullDiscountVO) {
        this.fullDiscountVO = fullDiscountVO;
    }

    public FullDiscountGiftDO getFullDiscountGift() {
        return fullDiscountGift;
    }

    public void setFullDiscountGift(FullDiscountGiftDO fullDiscountGift) {
        this.fullDiscountGift = fullDiscountGift;
    }

    public MinusVO getMinusVO() {
        return minusVO;
    }

    public void setMinusVO(MinusVO minusVO) {
        this.minusVO = minusVO;
    }

    public HalfPriceVO getHalfPriceVO() {
        return halfPriceVO;
    }

    public void setHalfPriceVO(HalfPriceVO halfPriceVO) {
        this.halfPriceVO = halfPriceVO;
    }

    public SeckillGoodsVO getSeckillGoodsVO() {
        return seckillGoodsVO;
    }

    public void setSeckillGoodsVO(SeckillGoodsVO seckillGoodsVO) {

        this.seckillGoodsVO = seckillGoodsVO;
    }


    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public GroupbuyGoodsVO getGroupbuyGoodsVO() {
        return groupbuyGoodsVO;
    }

    public void setGroupbuyGoodsVO(GroupbuyGoodsVO groupbuyGoodsVO) {
        this.groupbuyGoodsVO = groupbuyGoodsVO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PromotionVO that = (PromotionVO) o;

        return new EqualsBuilder()
                .append(goodsId, that.goodsId)
                .append(thumbnail, that.thumbnail)
                .append(name, that.name)
                .append(skuId, that.skuId)
                .append(startTime, that.startTime)
                .append(endTime, that.endTime)
                .append(activityId, that.activityId)
                .append(promotionType, that.promotionType)
                .append(title, that.title)
                .append(exchange, that.exchange)
                .append(groupbuyGoodsVO, that.groupbuyGoodsVO)
                .append(fullDiscountVO, that.fullDiscountVO)
                .append(fullDiscountGift, that.fullDiscountGift)
                .append(minusVO, that.minusVO)
                .append(halfPriceVO, that.halfPriceVO)
                .append(seckillGoodsVO, that.seckillGoodsVO)
                .append(num, that.num)
                .append(price, that.price)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(goodsId)
                .append(thumbnail)
                .append(name)
                .append(skuId)
                .append(startTime)
                .append(endTime)
                .append(activityId)
                .append(promotionType)
                .append(title)
                .append(exchange)
                .append(groupbuyGoodsVO)
                .append(fullDiscountVO)
                .append(fullDiscountGift)
                .append(minusVO)
                .append(halfPriceVO)
                .append(seckillGoodsVO)
                .append(num)
                .append(price)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "PromotionVO{" +
                "goodsId=" + goodsId +
                ", thumbnail='" + thumbnail + '\'' +
                ", name='" + name + '\'' +
                ", skuId=" + skuId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", activityId=" + activityId +
                ", promotionType='" + promotionType + '\'' +
                ", title='" + title + '\'' +
                ", exchange=" + exchange +
                ", groupbuyGoodsVO=" + groupbuyGoodsVO +
                ", fullDiscountVO=" + fullDiscountVO +
                ", fullDiscountGift=" + fullDiscountGift +
                ", minusVO=" + minusVO +
                ", halfPriceVO=" + halfPriceVO +
                ", seckillGoodsVO=" + seckillGoodsVO +
                ", num=" + num +
                ", price=" + price +
                '}';
    }
}
