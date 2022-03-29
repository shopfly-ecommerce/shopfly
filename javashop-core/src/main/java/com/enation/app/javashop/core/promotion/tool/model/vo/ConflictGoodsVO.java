/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.promotion.tool.model.vo;

import com.enation.app.javashop.framework.database.annotation.Column;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * 冲突商品
 * @author Snow create in 2018/4/16
 * @version v2.0
 * @since v7.0.0
 */
public class ConflictGoodsVO implements Serializable {

    /** 商品名称 */
    @Column(name = "name")
    @ApiModelProperty(name="name",value="商品名称")
    private String name;

    /** 商品图片*/
    @Column(name = "thumbnail")
    @ApiModelProperty(name="thumbnail",value="商品图片")
    private String thumbnail;

    /**活动标题*/
    @Column(name = "title")
    @ApiModelProperty(name="title",value="活动标题",required=false)
    private String title;

    /**活动开始时间*/
    @Column(name = "start_time")
    @ApiModelProperty(name="start_time",value="活动开始时间",required=false)
    private Long startTime;

    /**活动结束时间*/
    @Column(name = "end_time")
    @ApiModelProperty(name="end_time",value="活动结束时间",required=false)
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
