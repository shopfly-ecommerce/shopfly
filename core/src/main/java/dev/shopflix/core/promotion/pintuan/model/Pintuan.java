/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.promotion.pintuan.model;

import dev.shopflix.framework.database.annotation.Column;
import dev.shopflix.framework.database.annotation.Id;
import dev.shopflix.framework.database.annotation.PrimaryKeyField;
import dev.shopflix.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;


/**
 * 拼团实体
 *
 * @author admin
 * @version vv1.0.0
 * @since vv7.1.0
 * 2019-01-21 15:17:57
 */
@Table(name = "es_pintuan")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Pintuan {


    /**
     * id
     */
    @Id(name = "promotion_id")
    @ApiModelProperty(hidden = true)
    private Integer promotionId;
    /**
     * 活动名称
     */
    @Column(name = "promotion_name")
    @NotEmpty(message = "活动名称不能为空")
    @ApiModelProperty(name = "promotion_name", value = "活动名称", required = true)
    private String promotionName;
    /**
     * 活动标题
     */
    @Column(name = "promotion_title")
    @ApiModelProperty(name = "promotion_title", value = "活动标题", required = true)
    private String promotionTitle;
    /**
     * 活动开始时间
     */
    @Column(name = "start_time")
    @Min(message = "活动开始时间不能为空", value = 0)
    @ApiModelProperty(name = "start_time", value = "活动开始时间", required = true)
    private Long startTime;
    /**
     * 活动结束时间
     */
    @Column(name = "end_time")
    @Min(message = "活动结束时间不能为空", value = 0)
    @ApiModelProperty(name = "end_time", value = "活动结束时间", required = true)
    private Long endTime;
    /**
     * 成团人数
     */
    @Column(name = "required_num")
    @Min(message = "必须为数字", value = 0)
    @ApiModelProperty(name = "required_num", value = "成团人数")
    private Integer requiredNum;
    /**
     * 限购数量
     */
    @Column(name = "limit_num")
    @Min(message = "必须为数字", value = 0)
    @ApiModelProperty(name = "limit_num", value = "限购数量")
    private Integer limitNum;
    /**
     * 虚拟成团
     */
    @Column(name = "enable_mocker")
    @Min(message = "必须为数字", value = 0)
    @ApiModelProperty(name = "enable_mocker", value = "虚拟成团", required = true)
    private Integer enableMocker;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @Min(message = "必须为数字", value = 0)
    @ApiModelProperty(name = "create_time", value = "创建时间")
    private Long createTime;

    @Column(name = "status")
    @ApiModelProperty(name = "status", value = "状态")
    private String status;

    @Column(name = "option_status")
    @ApiModelProperty(name = "status", value = "api请求操作状态")
    private String optionStatus;

    @Column(name = "seller_name")
    @ApiModelProperty(name = "seller_name", value = "商家名称")
    private String sellerName;

    @Column(name = "seller_id")
    @ApiModelProperty(name = "seller_id", value = "商家id")
    private Integer sellerId;

    @PrimaryKeyField
    public Integer getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Integer promotionId) {
        this.promotionId = promotionId;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public String getPromotionTitle() {
        return promotionTitle;
    }

    public void setPromotionTitle(String promotionTitle) {
        this.promotionTitle = promotionTitle;
    }

    public String getOptionStatus() {
        return optionStatus;
    }

    public void setOptionStatus(String optionStatus) {
        this.optionStatus = optionStatus;
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

    public Integer getRequiredNum() {
        return requiredNum;
    }

    public void setRequiredNum(Integer requiredNum) {
        this.requiredNum = requiredNum;
    }

    public Integer getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(Integer limitNum) {
        this.limitNum = limitNum;
    }

    public Integer getEnableMocker() {
        return enableMocker;
    }

    public void setEnableMocker(Integer enableMocker) {
        this.enableMocker = enableMocker;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    @Override
    public String toString() {
        return "Pintuan{" +
                "promotionId=" + promotionId +
                ", promotionName='" + promotionName + '\'' +
                ", promotionTitle='" + promotionTitle + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", requiredNum=" + requiredNum +
                ", limitNum=" + limitNum +
                ", enableMocker=" + enableMocker +
                ", createTime=" + createTime +
                ", status='" + status + '\'' +
                ", optionStatus='" + optionStatus + '\'' +
                ", sellerName='" + sellerName + '\'' +
                ", sellerId='" + sellerId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Pintuan pintuan = (Pintuan) o;

        if (promotionId != null ? !promotionId.equals(pintuan.promotionId) : pintuan.promotionId != null) {
            return false;
        }
        if (promotionName != null ? !promotionName.equals(pintuan.promotionName) : pintuan.promotionName != null) {
            return false;
        }
        if (promotionTitle != null ? !promotionTitle.equals(pintuan.promotionTitle) : pintuan.promotionTitle != null) {
            return false;
        }
        if (startTime != null ? !startTime.equals(pintuan.startTime) : pintuan.startTime != null) {
            return false;
        }
        if (endTime != null ? !endTime.equals(pintuan.endTime) : pintuan.endTime != null) {
            return false;
        }
        if (requiredNum != null ? !requiredNum.equals(pintuan.requiredNum) : pintuan.requiredNum != null) {
            return false;
        }
        if (limitNum != null ? !limitNum.equals(pintuan.limitNum) : pintuan.limitNum != null) {
            return false;
        }
        if (enableMocker != null ? !enableMocker.equals(pintuan.enableMocker) : pintuan.enableMocker != null) {
            return false;
        }
        if (createTime != null ? !createTime.equals(pintuan.createTime) : pintuan.createTime != null) {
            return false;
        }
        if (status != null ? !status.equals(pintuan.status) : pintuan.status != null) {
            return false;
        }
        if (optionStatus != null ? !optionStatus.equals(pintuan.optionStatus) : pintuan.optionStatus != null) {
            return false;
        }
        if (sellerName != null ? !sellerName.equals(pintuan.sellerName) : pintuan.sellerName != null) {
            return false;
        }
        return sellerId != null ? sellerId.equals(pintuan.sellerId) : pintuan.sellerId == null;
    }

    @Override
    public int hashCode() {
        int result = promotionId != null ? promotionId.hashCode() : 0;
        result = 31 * result + (promotionName != null ? promotionName.hashCode() : 0);
        result = 31 * result + (promotionTitle != null ? promotionTitle.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (requiredNum != null ? requiredNum.hashCode() : 0);
        result = 31 * result + (limitNum != null ? limitNum.hashCode() : 0);
        result = 31 * result + (enableMocker != null ? enableMocker.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (optionStatus != null ? optionStatus.hashCode() : 0);
        result = 31 * result + (sellerName != null ? sellerName.hashCode() : 0);
        result = 31 * result + (sellerId != null ? sellerId.hashCode() : 0);
        return result;
    }
}