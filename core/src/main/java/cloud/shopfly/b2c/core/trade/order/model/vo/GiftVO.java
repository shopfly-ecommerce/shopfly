/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.trade.order.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * 赠品VO
 *
 * @author Snow create in 2018/4/9
 * @version v2.0
 * @since v7.0.0
 */
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GiftVO implements Serializable {

    @ApiModelProperty(value = "赠品ID")
    private Integer giftId;

    @ApiModelProperty(value = "商家ID")
    private Integer sellerId;

    @ApiModelProperty(value = "赠品名称")
    private String giftName;

    @ApiModelProperty(value = "数量")
    private Integer giftNum;


    public Integer getGiftId() {
        return giftId;
    }

    public void setGiftId(Integer giftId) {
        this.giftId = giftId;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public Integer getGiftNum() {
        return giftNum;
    }

    public void setGiftNum(Integer giftNum) {
        this.giftNum = giftNum;
    }

    @Override
    public String toString() {
        return "GiftVO{" +
                "giftId=" + giftId +
                ", sellerId=" + sellerId +
                ", giftName='" + giftName + '\'' +
                ", giftNum=" + giftNum +
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

        GiftVO giftVO = (GiftVO) o;

        return new EqualsBuilder()
                .append(giftId, giftVO.giftId)
                .append(sellerId, giftVO.sellerId)
                .append(giftName, giftVO.giftName)
                .append(giftNum, giftVO.giftNum)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(giftId)
                .append(sellerId)
                .append(giftName)
                .append(giftNum)
                .toHashCode();
    }
}
