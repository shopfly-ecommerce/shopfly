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
package cloud.shopfly.b2c.core.promotion.exchange.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Integral product search parameters
 * @author Snow create in 2018/5/29
 * @version v2.0
 * @since v7.0.0
 */
@ApiModel
public class ExchangeQueryParam {


    @ApiModelProperty(name="name",value="Name",required=false)
    private String name;

    @ApiModelProperty(name="cat_id",value="Integral classificationID",required=false)
    private Integer catId;

    @ApiModelProperty(name="sn",value="SN",required=false)
    private String sn;


    @ApiModelProperty(name="page_no",value="What page",required=false)
    private Integer pageNo;

    @ApiModelProperty(name="page_size",value="Number each page",required=false)
    private Integer pageSize;

    @ApiModelProperty(value = "The start time")
    private Long startTime;

    @ApiModelProperty(value = "The end of time")
    private Long endTime;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
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

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (o == null || getClass() != o.getClass()){
            return false;
        }

        ExchangeQueryParam that = (ExchangeQueryParam) o;

        return new EqualsBuilder()
                .append(name, that.name)
                .append(catId, that.catId)
                .append(sn, that.sn)
                .append(pageNo, that.pageNo)
                .append(pageSize, that.pageSize)
                .append(startTime, that.startTime)
                .append(endTime, that.endTime)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .append(catId)
                .append(sn)
                .append(pageNo)
                .append(pageSize)
                .append(startTime)
                .append(endTime)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "ExchangeQueryParam{" +
                "name='" + name + '\'' +
                ", catId=" + catId +
                ", sn='" + sn + '\'' +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
