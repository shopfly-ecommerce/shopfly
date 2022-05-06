/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.promotion.groupbuy.model.vo;

import cloud.shopfly.b2c.core.promotion.groupbuy.model.dos.GroupbuyGoodsDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 团购商品VO
 *
 * @author Snow create in 2018/6/11
 * @version v2.0
 * @since v7.0.0
 */
@ApiModel
public class GroupbuyGoodsVO extends GroupbuyGoodsDO {


    @ApiModelProperty(name = "start_time", value = "活动开始时间")
    private Long startTime;

    @ApiModelProperty(name = "end_time", value = "活动截止时间")
    private Long endTime;

    @ApiModelProperty(name = "title", value = "活动标题")
    private String title;

    @ApiModelProperty(name = "enable_quantity", value = "可用库存")
    private Integer enableQuantity;

    @ApiModelProperty(name = "quantity", value = "库存")
    private Integer quantity;

    @ApiModelProperty(name = "isEnable", value = "活动是否在进行中")
    private Integer isEnable;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getEnableQuantity() {
        return enableQuantity;
    }

    public void setEnableQuantity(Integer enableQuantity) {
        this.enableQuantity = enableQuantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }


    @Override
    public String toString() {
        return "GroupbuyGoodsVO{" +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", title='" + title + '\'' +
                ", enableQuantity=" + enableQuantity +
                ", quantity=" + quantity +
                ", isEnable=" + isEnable +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GroupbuyGoodsVO that = (GroupbuyGoodsVO) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(startTime, that.startTime)
                .append(endTime, that.endTime)
                .append(title, that.title)
                .append(enableQuantity, that.enableQuantity)
                .append(quantity, that.quantity)
                .append(isEnable, that.isEnable)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(startTime)
                .append(endTime)
                .append(title)
                .append(enableQuantity)
                .append(quantity)
                .append(isEnable)
                .toHashCode();
    }


}
