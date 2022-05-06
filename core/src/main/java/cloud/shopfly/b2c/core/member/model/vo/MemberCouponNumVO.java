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
package cloud.shopfly.b2c.core.member.model.vo;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * 会员优惠券—各个状态数量
 * @author Snow create in 2018/8/20
 * @version v2.0
 * @since v7.0.0
 */
public class MemberCouponNumVO implements Serializable {

    @ApiModelProperty(value = "未使用优惠券数量")
    private Integer unUseNum;

    @ApiModelProperty(value = "已使用优惠券数量")
    private Integer useNum;

    @ApiModelProperty(value = "已过期优惠券数量")
    private Integer expiredNum;

    public Integer getUnUseNum() {
        return unUseNum;
    }

    public void setUnUseNum(Integer unUseNum) {
        this.unUseNum = unUseNum;
    }

    public Integer getUseNum() {
        return useNum;
    }

    public void setUseNum(Integer useNum) {
        this.useNum = useNum;
    }

    public Integer getExpiredNum() {
        return expiredNum;
    }

    public void setExpiredNum(Integer expiredNum) {
        this.expiredNum = expiredNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (o == null || getClass() != o.getClass()){
            return false;
        }

        MemberCouponNumVO that = (MemberCouponNumVO) o;

        return new EqualsBuilder()
                .append(unUseNum, that.unUseNum)
                .append(useNum, that.useNum)
                .append(expiredNum, that.expiredNum)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(unUseNum)
                .append(useNum)
                .append(expiredNum)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "MemberCouponNumVO{" +
                "unUseNum=" + unUseNum +
                ", useNum=" + useNum +
                ", expiredNum=" + expiredNum +
                '}';
    }
}
