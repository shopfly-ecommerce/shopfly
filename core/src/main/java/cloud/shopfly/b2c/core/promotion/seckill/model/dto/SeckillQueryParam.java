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
package cloud.shopfly.b2c.core.promotion.seckill.model.dto;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Flash sale query parameters
 * @author Snow create in 2018/6/21
 * @version v2.0
 * @since v7.0.0
 */
public class SeckillQueryParam {

    @ApiModelProperty(value = "The page number")
    private Integer pageNo;

    @ApiModelProperty(value = "Number each page")
    private Integer pageSize;

    @ApiModelProperty(value = "Flash salesID")
    private Integer seckillId;

    @ApiModelProperty(value = "keyword")
    private String keywords;

    @ApiModelProperty(value = "Status")
    private String status;


    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(Integer seckillId) {
        this.seckillId = seckillId;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (o == null || getClass() != o.getClass()){
            return false;
        }

        SeckillQueryParam that = (SeckillQueryParam) o;

        return new EqualsBuilder()
                .append(pageNo, that.pageNo)
                .append(pageSize, that.pageSize)
                .append(seckillId, that.seckillId)
                .append(keywords, that.keywords)
                .append(status, that.status)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(pageNo)
                .append(pageSize)
                .append(seckillId)
                .append(keywords)
                .append(status)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "SeckillQueryParam{" +
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", seckillId=" + seckillId +
                ", keywords='" + keywords + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
