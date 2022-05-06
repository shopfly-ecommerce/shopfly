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
package cloud.shopfly.b2c.core.promotion.seckill.model.dos;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 限时抢购入库实体
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-21 10:32:36
 */
@Table(name="es_seckill")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SeckillDO implements Serializable {

    private static final long serialVersionUID = 1621620877040645L;

    /**主键id*/
    @Id(name = "seckill_id")
    @ApiModelProperty(hidden=true)
    private Integer seckillId;

    /**活动名称*/
    @Column(name = "seckill_name")
    @NotEmpty(message = "请填写活动名称")
    @ApiModelProperty(value="活动名称",required=true)
    private String seckillName;

    /**活动日期*/
    @Column(name = "start_day")
    @NotNull(message = "请填写活动日期")
    @ApiModelProperty(value="活动日期",required=true)
    private Long startDay;

    /**报名截至时间*/
    @Column(name = "apply_end_time")
    @NotNull(message = "请填写报名截止时间")
    @ApiModelProperty(value="报名截至时间",required=true)
    private Long applyEndTime;

    /**申请规则*/
    @Column(name = "seckill_rule")
    @ApiModelProperty(value="申请规则",required=false)
    private String seckillRule;


    /**状态*/
    @Column(name = "seckill_status")
    @ApiModelProperty(value="状态,EDITING：编辑中,RELEASE:已发布,OVER:已结束")
    private String seckillStatus;

    @PrimaryKeyField
    public Integer getSeckillId() {
        return seckillId;
    }
    public void setSeckillId(Integer seckillId) {
        this.seckillId = seckillId;
    }

    public String getSeckillName() {
        return seckillName;
    }
    public void setSeckillName(String seckillName) {
        this.seckillName = seckillName;
    }

    public Long getStartDay() {
        return startDay;
    }

    public void setStartDay(Long startDay) {
        this.startDay = startDay;
    }

    public Long getApplyEndTime() {
        return applyEndTime;
    }

    public void setApplyEndTime(Long applyEndTime) {
        this.applyEndTime = applyEndTime;
    }

    public String getSeckillRule() {
        return seckillRule;
    }

    public void setSeckillRule(String seckillRule) {
        this.seckillRule = seckillRule;
    }

    public String getSeckillStatus() {
        return seckillStatus;
    }
    public void setSeckillStatus(String seckillStatus) {
        this.seckillStatus = seckillStatus;
    }

    @Override
    public String toString() {
        return "SeckillDO{" +
                "seckillId=" + seckillId +
                ", seckillName='" + seckillName + '\'' +
                ", startDay=" + startDay +
                ", applyEndTime=" + applyEndTime +
                ", seckillRule='" + seckillRule + '\'' +
                ", seckillStatus='" + seckillStatus + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (o == null || getClass() != o.getClass()){
            return false;
        }

        SeckillDO seckillDO = (SeckillDO) o;

        return new EqualsBuilder()
                .append(seckillId, seckillDO.seckillId)
                .append(seckillName, seckillDO.seckillName)
                .append(startDay, seckillDO.startDay)
                .append(applyEndTime, seckillDO.applyEndTime)
                .append(seckillRule, seckillDO.seckillRule)
                .append(seckillStatus, seckillDO.seckillStatus)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(seckillId)
                .append(seckillName)
                .append(startDay)
                .append(applyEndTime)
                .append(seckillRule)
                .append(seckillStatus)
                .toHashCode();
    }
}
