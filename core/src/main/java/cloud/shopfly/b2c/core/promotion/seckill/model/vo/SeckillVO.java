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
package cloud.shopfly.b2c.core.promotion.seckill.model.vo;

import cloud.shopfly.b2c.core.promotion.seckill.model.dos.SeckillApplyDO;
import cloud.shopfly.b2c.core.promotion.seckill.model.dos.SeckillDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

/**
 * @author Snow
 * @version 1.0
 * @since 6.4.1
 * 2017years12month14day16:58:55
 */
@ApiModel(description = "Flash salesvo")
public class SeckillVO extends SeckillDO {

    @ApiModelProperty(name = "range_list", value = "Active schedule")
    @Size(min = 1, max = 23)
    private Map<Integer, List<SeckillApplyDO>> rangeList;

    @ApiModelProperty(name = "0:Did not sign up,1:Have to sign up,2:Has as")
    private Integer isApply;

    @ApiModelProperty(name = "seckill_status_text", value = "The status value")
    private String seckillStatusText;


    public SeckillVO() {

    }

    public Map<Integer, List<SeckillApplyDO>> getRangeList() {
        return rangeList;
    }

    public void setRangeList(Map<Integer, List<SeckillApplyDO>> rangeList) {
        this.rangeList = rangeList;
    }

    public Integer getIsApply() {
        return isApply;
    }

    public void setIsApply(Integer isApply) {
        this.isApply = isApply;
    }

    public String getSeckillStatusText() {
        return seckillStatusText;
    }

    public void setSeckillStatusText(String seckillStatusText) {
        this.seckillStatusText = seckillStatusText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SeckillVO seckillVO = (SeckillVO) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(rangeList, seckillVO.rangeList)
                .append(isApply, seckillVO.isApply)
                .append(seckillStatusText, seckillVO.seckillStatusText)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(rangeList)
                .append(isApply)
                .append(seckillStatusText)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "SeckillVO{" +
                "rangeList=" + rangeList +
                ", isApply=" + isApply +
                ", seckillStatusText='" + seckillStatusText + '\'' +
                '}';
    }
}
