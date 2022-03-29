/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.promotion.seckill.model.dto;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 限时抢购查询参数
 * @author Snow create in 2018/6/21
 * @version v2.0
 * @since v7.0.0
 */
public class SeckillQueryParam {

    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @ApiModelProperty(value = "每页条数")
    private Integer pageSize;

    @ApiModelProperty(value = "限时抢购活动ID")
    private Integer seckillId;

    @ApiModelProperty(value = "关键字")
    private String keywords;

    @ApiModelProperty(value = "状态")
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
