/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.promotion.groupbuy.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * 团购商品查询参数对象
 *
 * @author Snow create in 2018/5/28
 * @version v2.0
 * @since v7.0.0
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GroupbuyQueryParam implements Serializable {

    @ApiModelProperty(value = "团购活动ID")
    private Integer actId;

    @ApiModelProperty(value = "关键字")
    private String keywords;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "会员ID")
    private Integer memberId;

    @ApiModelProperty(value = "开始时间")
    private Long startTime;

    @ApiModelProperty(value = "结束时间")
    private Long endTime;

    @ApiModelProperty(value = "分类ID")
    private Integer catId;

    @ApiModelProperty(value = "第几页")
    private Integer page;

    @ApiModelProperty(value = "每页条数")
    private Integer pageSize;

    public Integer getActId() {
        return actId;
    }

    public void setActId(Integer actId) {
        this.actId = actId;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }


    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GroupbuyQueryParam param = (GroupbuyQueryParam) o;

        return new EqualsBuilder()
                .append(actId, param.actId)
                .append(keywords, param.keywords)
                .append(goodsName, param.goodsName)
                .append(memberId, param.memberId)
                .append(startTime, param.startTime)
                .append(endTime, param.endTime)
                .append(catId, param.catId)
                .append(page, param.page)
                .append(pageSize, param.pageSize)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(actId)
                .append(keywords)
                .append(goodsName)
                .append(memberId)
                .append(startTime)
                .append(endTime)
                .append(catId)
                .append(page)
                .append(pageSize)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "GroupbuyQueryParam{" +
                "actId=" + actId +
                ", keywords='" + keywords + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", memberId=" + memberId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", catId=" + catId +
                ", page=" + page +
                ", pageSize=" + pageSize +
                '}';
    }
}
