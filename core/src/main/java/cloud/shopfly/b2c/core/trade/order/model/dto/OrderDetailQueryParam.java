/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.trade.order.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 订单详细查询参数
 *
 * @author Snow create in 2018/7/18
 * @version v2.0
 * @since v7.0.0
 */

@ApiIgnore
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderDetailQueryParam {

    @ApiModelProperty(value = "会员ID")
    private Integer buyerId;

    @ApiModelProperty(value = "商家ID")
    private Integer sellerId;


    public Integer getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderDetailQueryParam that = (OrderDetailQueryParam) o;

        return new EqualsBuilder()
                .append(buyerId, that.buyerId)
                .append(sellerId, that.sellerId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(buyerId)
                .append(sellerId)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "OrderDetailQueryParam{" +
                ", buyerId=" + buyerId +
                ", sellerId=" + sellerId +
                '}';
    }
}
