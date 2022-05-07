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
package cloud.shopfly.b2c.core.promotion.pintuan.model;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;


/**
 * Spell group entities
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
     * The name of the event
     */
    @Column(name = "promotion_name")
    @NotEmpty(message = "The activity name cannot be empty")
    @ApiModelProperty(name = "promotion_name", value = "The name of the event", required = true)
    private String promotionName;
    /**
     * Activity title
     */
    @Column(name = "promotion_title")
    @ApiModelProperty(name = "promotion_title", value = "Activity title", required = true)
    private String promotionTitle;
    /**
     * Activity start time
     */
    @Column(name = "start_time")
    @Min(message = "The activity start time cannot be empty", value = 0)
    @ApiModelProperty(name = "start_time", value = "Activity start time", required = true)
    private Long startTime;
    /**
     * End time
     */
    @Column(name = "end_time")
    @Min(message = "The activity end time cannot be empty", value = 0)
    @ApiModelProperty(name = "end_time", value = "End time", required = true)
    private Long endTime;
    /**
     * The number of clusters
     */
    @Column(name = "required_num")
    @Min(message = "Must be a number", value = 0)
    @ApiModelProperty(name = "required_num", value = "The number of clusters")
    private Integer requiredNum;
    /**
     * The amount for purchasing
     */
    @Column(name = "limit_num")
    @Min(message = "Must be a number", value = 0)
    @ApiModelProperty(name = "limit_num", value = "The amount for purchasing")
    private Integer limitNum;
    /**
     * Virtual clusters
     */
    @Column(name = "enable_mocker")
    @Min(message = "Must be a number", value = 0)
    @ApiModelProperty(name = "enable_mocker", value = "Virtual clusters", required = true)
    private Integer enableMocker;
    /**
     * Last update
     */
    @Column(name = "create_time")
    @Min(message = "Must be a number", value = 0)
    @ApiModelProperty(name = "create_time", value = "Last update")
    private Long createTime;

    @Column(name = "status")
    @ApiModelProperty(name = "status", value = "Status")
    private String status;

    @Column(name = "option_status")
    @ApiModelProperty(name = "status", value = "apiRequest operation status")
    private String optionStatus;

    @Column(name = "seller_name")
    @ApiModelProperty(name = "seller_name", value = "Vendor name")
    private String sellerName;

    @Column(name = "seller_id")
    @ApiModelProperty(name = "seller_id", value = "merchantsid")
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
