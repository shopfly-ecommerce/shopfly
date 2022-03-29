/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.promotion.seckill.model.dto;

import dev.shopflix.core.promotion.seckill.model.dos.SeckillDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author Snow
 * @version 1.0
 * @since 6.4.1
 * 2017年12月14日 16:58:55
 */
@ApiModel(description = "限时抢购活动vo")
public class SeckillDTO extends SeckillDO {

    @ApiModelProperty(name = "range_list", value = "活动时刻表")
    @Size(min = 1, max = 23)
    private List<Integer> rangeList;

    @ApiModelProperty(name = "0:未报名,1:已报名,2:已截止")
    private Integer isApply;

    @ApiModelProperty(name = "seckill_status_text", value = "状态值")
    private String seckillStatusText;


    public SeckillDTO() {

    }

    public List<Integer> getRangeList() {
        return rangeList;
    }

    public void setRangeList(List<Integer> rangeList) {
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

        SeckillDTO seckillVO = (SeckillDTO) o;

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
