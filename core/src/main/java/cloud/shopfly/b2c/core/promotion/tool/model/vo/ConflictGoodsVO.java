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
package cloud.shopfly.b2c.core.promotion.tool.model.vo;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * Conflict goods
 * @author Snow create in 2018/4/16
 * @version v2.0
 * @since v7.0.0
 */
public class ConflictGoodsVO implements Serializable {

    /** Name*/
    @Column(name = "name")
    @ApiModelProperty(name="name",value="Name")
    private String name;

    /** Commodity images*/
    @Column(name = "thumbnail")
    @ApiModelProperty(name="thumbnail",value="Commodity images")
    private String thumbnail;

    /**Activity title*/
    @Column(name = "title")
    @ApiModelProperty(name="title",value="Activity title",required=false)
    private String title;

    /**Activity start time*/
    @Column(name = "start_time")
    @ApiModelProperty(name="start_time",value="Activity start time",required=false)
    private Long startTime;

    /**End time*/
    @Column(name = "end_time")
    @ApiModelProperty(name="end_time",value="End time",required=false)
    private Long endTime;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
    public String toString() {
        return "ConflictGoodsVO{" +
                "name='" + name + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", title='" + title + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    public ConflictGoodsVO() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (o == null || getClass() != o.getClass()){
            return false;
        }

        ConflictGoodsVO goodsVO = (ConflictGoodsVO) o;

        return new EqualsBuilder()
                .append(name, goodsVO.name)
                .append(thumbnail, goodsVO.thumbnail)
                .append(title, goodsVO.title)
                .append(startTime, goodsVO.startTime)
                .append(endTime, goodsVO.endTime)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .append(thumbnail)
                .append(title)
                .append(startTime)
                .append(endTime)
                .toHashCode();
    }
}
