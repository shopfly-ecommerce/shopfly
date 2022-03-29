/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.member.model.dos;

import dev.shopflix.framework.database.annotation.Column;
import dev.shopflix.framework.database.annotation.Id;
import dev.shopflix.framework.database.annotation.PrimaryKeyField;
import dev.shopflix.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * 会员积分表实体
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
     * 主键ID
     */
    @Id(name = "id")
    @ApiModelProperty(hidden = true)
    private Integer id;
    /**
     * 会员ID
     */
    @Column(name = "member_id")
    @ApiModelProperty(name = "member_id", value = "会员ID", required = false)
    private Integer memberId;
    /**
     * 等级积分
     */
    @Column(name = "grade_point")
    @Min(message = "必须为数字", value = 0)
    @ApiModelProperty(name = "gade_point", value = "等级积分", required = false)
    private Integer gradePoint;
    /**
     * 操作时间
     */
    @Column(name = "time")
    @ApiModelProperty(name = "time", value = "操作时间", required = false)
    private Long time;
    /**
     * 操作理由
     */
    @Column(name = "reason")
    @ApiModelProperty(name = "reason", value = "操作理由", required = false)
    private String reason;
    /**
     * 等级积分类型
     */
    @Column(name = "grade_point_type")
    @Min(message = "最小值为0", value = 0)
    @Max(message = "最大值为1", value = 1)
    @ApiModelProperty(name = "grade_point_type", value = "等级积分类型 1为增加，0为消费", required = false)
    private Integer gradePointType;
    /**
     * 操作者
     */
    @Column(name = "operator")
    @ApiModelProperty(name = "operator", value = "操作者", required = false)
    private String operator;
    /**
     * 消费积分
     */
    @Column(name = "consum_point")
    @Min(message = "必须为数字", value = 0)
    @ApiModelProperty(name = "consum_point", value = "消费积分", required = false)
    private Integer consumPoint;
    /**
     * 消费积分类型
     */
    @Column(name = "consum_point_type")
    @Min(message = "最小值为0", value = 0)
    @Max(message = "最大值为1", value = 1)
    @ApiModelProperty(name = "consum_point_type", value = "消费积分类型，1为增加，0为消费", required = false)
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