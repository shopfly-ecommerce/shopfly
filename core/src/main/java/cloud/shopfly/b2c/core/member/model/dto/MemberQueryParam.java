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
package cloud.shopfly.b2c.core.member.model.dto;

import cloud.shopfly.b2c.core.base.context.Region;
import cloud.shopfly.b2c.core.base.context.RegionFormat;
import cloud.shopfly.b2c.framework.database.annotation.Column;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * Member parameter passing
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-30 14:27:48
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MemberQueryParam {
    /**
     * Member login user name
     */
    @ApiModelProperty(name = "uname", value = "Member login user name")
    private String uname;
    /**
     * email
     */
    @ApiModelProperty(name = "email", value = "email")
    private String email;
    /**
     * Member mobile Phone Number
     */
    @ApiModelProperty(name = "mobile", value = "Member mobile Phone Number")
    private String mobile;
    /**
     * Member of the gender
     */
    @ApiModelProperty(name = "sex", value = "Member of the gender,1For men,0For female")
    private Integer sex;

    @RegionFormat
    @ApiModelProperty(name = "region", value = "region")
    private Region region;
    /**
     * The start time
     */
    @ApiModelProperty(name = "start_time", value = "The start time")
    private String startTime;
    /**
     * The end of time
     */
    @ApiModelProperty(name = "end_time", value = "The end of time")
    private String endTime;
    /**
     * keyword
     */
    @ApiModelProperty(name = "keyword", value = "keyword")
    private String keyword;
    /**
     * The page number
     */
    @ApiModelProperty(name = "page_no", value = "The page number", required = true)
    private Integer pageNo;

    /**
     * The member state
     */
    @Column(name = "disabled")
    @ApiModelProperty(name = "disabled", value = "The member state,0For the normal.-1For deletion, in the member recycle bin", required = false)
    private Integer disabled;

    /**
     * Number of pages
     */
    @ApiModelProperty(name = "page_size", value = "Number of pages", required = true)
    private Integer pageSize;

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "MemberQueryParam{" +
                "uname='" + uname + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", sex=" + sex +
                ", region=" + region +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", keyword='" + keyword + '\'' +
                ", pageNo=" + pageNo +
                ", disabled=" + disabled +
                ", pageSize=" + pageSize +
                '}';
    }
}
