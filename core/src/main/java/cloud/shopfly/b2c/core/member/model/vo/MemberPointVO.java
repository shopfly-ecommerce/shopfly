/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.member.model.vo;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;

/**
 *
 * 会员积分查询
 * @author zh
 * @version v70
 * @since v7.0
 * 2018-04-004 15:44:12
 */
public class MemberPointVO {

    /**
     * 等级积分
     */
    @Min(message="必须为数字", value = 0)
    @ApiModelProperty(name="grade_point",value="等级积分",required=false)
    private Integer gradePoint;

    /**消费积分*/
    @Min(message="必须为数字", value = 0)
    @ApiModelProperty(name="consum_point",value="消费积分",required=false)
    private Integer consumPoint;

    public Integer getGradePoint() {
        return gradePoint;
    }

    public void setGradePoint(Integer gradePoint) {
        this.gradePoint = gradePoint;
    }

    public Integer getConsumPoint() {
        return consumPoint;
    }

    public void setConsumPoint(Integer consumPoint) {
        this.consumPoint = consumPoint;
    }

    @Override
    public String toString() {
        return "MemberPointVO{" +
                "gradePoint=" + gradePoint +
                ", consumPoint=" + consumPoint +
                '}';
    }
}
