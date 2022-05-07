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
package cloud.shopfly.b2c.core.member.model.dos;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * Member points form entity
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-04-03 15:44:12
 */
@Table(name = "es_member_point_history")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MemberPointHistory implements Serializable {

    private static final long serialVersionUID = 5222393178191730L;

    /**
     * A primary keyID
     */
    @Id(name = "id")
    @ApiModelProperty(hidden = true)
    private Integer id;
    /**
     * membersID
     */
    @Column(name = "member_id")
    @ApiModelProperty(name = "member_id", value = "membersID", required = false)
    private Integer memberId;
    /**
     * Level score
     */
    @Column(name = "grade_point")
    @Min(message = "Must be a number", value = 0)
    @ApiModelProperty(name = "gade_point", value = "Level score", required = false)
    private Integer gradePoint;
    /**
     * Operating time
     */
    @Column(name = "time")
    @ApiModelProperty(name = "time", value = "Operating time", required = false)
    private Long time;
    /**
     * Operation reason
     */
    @Column(name = "reason")
    @ApiModelProperty(name = "reason", value = "Operation reason", required = false)
    private String reason;
    /**
     * Grade integral type
     */
    @Column(name = "grade_point_type")
    @Min(message = "The minimum value for0", value = 0)
    @Max(message = "The maximum value for1", value = 1)
    @ApiModelProperty(name = "grade_point_type", value = "Grade integral type1In order to increase,0For the consumer", required = false)
    private Integer gradePointType;
    /**
     * The operator
     */
    @Column(name = "operator")
    @ApiModelProperty(name = "operator", value = "The operator", required = false)
    private String operator;
    /**
     * consumption score
     */
    @Column(name = "consum_point")
    @Min(message = "Must be a number", value = 0)
    @ApiModelProperty(name = "consum_point", value = "consumption score", required = false)
    private Integer consumPoint;
    /**
     * Consumption Credit type
     */
    @Column(name = "consum_point_type")
    @Min(message = "The minimum value for0", value = 0)
    @Max(message = "The maximum value for1", value = 1)
    @ApiModelProperty(name = "consum_point_type", value = "Consumption point type,1In order to increase,0For the consumer", required = false)
    private Integer consumPointType;

    @PrimaryKeyField
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Integer getConsumPoint() {
        return consumPoint;
    }

    public void setConsumPoint(Integer consumPoint) {
        this.consumPoint = consumPoint;
    }

    public Integer getConsumPointType() {
        return consumPointType;
    }

    public void setConsumPointType(Integer consumPointType) {
        this.consumPointType = consumPointType;
    }

    public Integer getGradePoint() {
        return gradePoint;
    }

    public void setGradePoint(Integer gradePoint) {
        this.gradePoint = gradePoint;
    }

    public Integer getGradePointType() {
        return gradePointType;
    }

    public void setGradePointType(Integer gradePointType) {
        this.gradePointType = gradePointType;
    }

    @Override
    public String toString() {
        return "MemberPointHistory{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", gradePoint=" + gradePoint +
                ", time=" + time +
                ", reason='" + reason + '\'' +
                ", gradePointType=" + gradePointType +
                ", operator='" + operator + '\'' +
                ", consumPoint=" + consumPoint +
                ", consumPointType=" + consumPointType +
                '}';
    }
}
