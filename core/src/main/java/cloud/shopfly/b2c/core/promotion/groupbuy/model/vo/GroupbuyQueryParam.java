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
package cloud.shopfly.b2c.core.promotion.groupbuy.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * Group purchase commodity query parameter object
 *
 * @author Snow create in 2018/5/28
 * @version v2.0
 * @since v7.0.0
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GroupbuyQueryParam implements Serializable {

    @ApiModelProperty(value = "Group-buying activitiesID")
    private Integer actId;

    @ApiModelProperty(value = "keyword")
    private String keywords;

    @ApiModelProperty(value = "Name")
    private String goodsName;

    @ApiModelProperty(value = "membersID")
    private Integer memberId;

    @ApiModelProperty(value = "The start time")
    private Long startTime;

    @ApiModelProperty(value = "The end of time")
    private Long endTime;

    @ApiModelProperty(value = "CategoriesID")
    private Integer catId;

    @ApiModelProperty(value = "What page")
    private Integer page;

    @ApiModelProperty(value = "Number each page")
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
