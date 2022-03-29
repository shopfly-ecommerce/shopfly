/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.member.model.dto;

import com.enation.app.javashop.core.base.context.Region;
import com.enation.app.javashop.core.base.context.RegionFormat;
import com.enation.app.javashop.framework.database.annotation.Column;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * 会员参数传递
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
     * 会员登陆用户名
     */
    @ApiModelProperty(name = "uname", value = "会员登陆用户名")
    private String uname;
    /**
     * 邮箱
     */
    @ApiModelProperty(name = "email", value = "邮箱")
    private String email;
    /**
     * 会员手机号码
     */
    @ApiModelProperty(name = "mobile", value = "会员手机号码")
    private String mobile;
    /**
     * 会员性别
     */
    @ApiModelProperty(name = "sex", value = "会员性别,1为男，0为女")
    private Integer sex;

    @RegionFormat
    @ApiModelProperty(name = "region", value = "地区")
    private Region region;
    /**
     * 开始时间
     */
    @ApiModelProperty(name = "start_time", value = "开始时间")
    private String startTime;
    /**
     * 结束时间
     */
    @ApiModelProperty(name = "end_time", value = "结束时间")
    private String endTime;
    /**
     * 关键字
     */
    @ApiModelProperty(name = "keyword", value = "关键字")
    private String keyword;
    /**
     * 页码
     */
    @ApiModelProperty(name = "page_no", value = "页码", required = true)
    private Integer pageNo;

    /**
     * 会员状态
     */
    @Column(name = "disabled")
    @ApiModelProperty(name = "disabled", value = "会员状态,0为正常.-1为删除，在会员回收站中", required = false)
    private Integer disabled;

    /**
     * 分页数
     */
    @ApiModelProperty(name = "page_size", value = "分页数", required = true)
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