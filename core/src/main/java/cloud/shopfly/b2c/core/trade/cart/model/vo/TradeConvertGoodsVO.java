/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.trade.cart.model.vo;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * 购物车—商品VO
 *
 * @author Snow
 * @version v2.0
 * @since v7.0.0
 * create in 2018/3/20
 */
@SuppressWarnings("AlibabaPojoMustOverrideToString")
public class TradeConvertGoodsVO implements Serializable {

    @ApiModelProperty(value = "运费模板id")
    private Integer templateId;

    @ApiModelProperty(name = "last_modify", value = "商品最后修改时间")
    private Long lastModify;


    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Long getLastModify() {
        return lastModify;
    }

    public void setLastModify(Long lastModify) {
        this.lastModify = lastModify;
    }

    @Override
    public String toString() {
        return "TradeConvertGoodsVO{" +
                "templateId=" + templateId +
                ", lastModify=" + lastModify +
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

        TradeConvertGoodsVO that = (TradeConvertGoodsVO) o;

        return new EqualsBuilder()
                .append(templateId, that.templateId)
                .append(lastModify, that.lastModify)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(templateId)
                .append(lastModify)
                .toHashCode();
    }
}
