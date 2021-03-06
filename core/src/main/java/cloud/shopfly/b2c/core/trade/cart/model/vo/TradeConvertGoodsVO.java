/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cloud.shopfly.b2c.core.trade.cart.model.vo;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * The shopping cart—productVO
 *
 * @author Snow
 * @version v2.0
 * @since v7.0.0
 * create in 2018/3/20
 */
@SuppressWarnings("AlibabaPojoMustOverrideToString")
public class TradeConvertGoodsVO implements Serializable {

    @ApiModelProperty(value = "The freight templateid")
    private Integer templateId;

    @ApiModelProperty(name = "last_modify", value = "The last modification time of the product")
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
