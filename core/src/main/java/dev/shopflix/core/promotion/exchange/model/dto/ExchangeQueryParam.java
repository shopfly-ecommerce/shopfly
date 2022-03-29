/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.promotion.exchange.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 积分商品搜索参数
 * @author Snow create in 2018/5/29
 * @version v2.0
 * @since v7.0.0
 */
@ApiModel
public class ExchangeQueryParam {


    @ApiModelProperty(name="name",value="商品名称",required=false)
    private String name;

    @ApiModelProperty(name="cat_id",value="积分分类ID",required=false)
    private Integer catId;

    @ApiModelProperty(name="sn",value="商品编号",required=false)
    private String sn;


    @ApiModelProperty(name="page_no",value="第几页",required=false)
    private Integer pageNo;

    @ApiModelProperty(name="page_size",value="每页条数",required=false)
    private Integer pageSize;

    @ApiModelProperty(value = "开始时间")
    private Long startTime;

    @ApiModelProperty(value = "结束时间")
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
