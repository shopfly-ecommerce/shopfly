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
package cloud.shopfly.b2c.core.distribution.model.vo;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Royalty template
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/17 In the afternoon3:24
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CommissionTplVO implements Serializable {


    /**
     * A primary key
     */
    @Id(name = "id")
    @ApiModelProperty(hidden = true)
    private Integer id;

    /**
     * Template name
     */
    @Column(name = "tpl_name")
    @NotEmpty(message = "Template names cannot be empty")
    @ApiModelProperty(name = "tpl_name", value = "The name of the template", required = false)
    private String tplName;

    /**
     * Template description
     */
    @Column(name = "tpl_describe")
    @ApiModelProperty(name = "tpl_describe", value = "Template description", required = false)
    private String tplDescribe;

    /**
     * Automatic switching condition
     */
    @Column(name = "switch_model")
    @NotEmpty(message = "The switching mode cannot be null")
    @ApiModelProperty(name = "switch_model", value = "Template descriptionMANUAL=manual/ AUTOMATIC=automatic", required = false)
    private String switchModel;


    /**
     * Switching conditions：Margin calls
     */
    @Column()
    @NotNull(message = "The profit requirement cannot be empty")
    @ApiModelProperty(value = "Margin calls", required = false)
    private Double profit;


    /**
     * Switching conditions：The number of referrals
     */
    @Column()
    @NotNull(message = "The number of offline users cannot be empty")
    @ApiModelProperty(value = "The number of referrals", required = false)
    private Integer num;


    /**
     * Difference with consumer1Level of rebate amount
     */
    @Column()
    @NotNull(message = "Difference between1The amount of class ii rebate cannot be empty")
    @ApiModelProperty(value = "Difference between1Percentage value of level rebate amount：Fill in the1 Its percent1", required = false)
    private Double grade1;

    /**
     * Difference with consumer2Level of rebate amount
     */
    @Column()
    @NotNull(message = "Difference between2The amount of class ii rebate cannot be empty")
    @ApiModelProperty(value = "Difference between2Percentage value of level rebate amount：Fill in the1 Its percent1", required = false)
    private Double grade2;


    /**
     * Whether the default1 By default,0  default
     */
    @Column(name = "is_default")
    @NotNull(message = "The default parameter cannot be null")
    @ApiModelProperty(name = "is_default", value = "Whether the default：1By default,0 default", required = false)
    private Integer isDefault;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTplDescribe() {
        return tplDescribe;
    }

    public void setTplDescribe(String tplDescribe) {
        this.tplDescribe = tplDescribe;
    }

    public String getTplName() {
        return tplName;
    }

    public void setTplName(String tplName) {
        this.tplName = tplName;
    }

    public String getSwitchModel() {
        return switchModel;
    }

    public void setSwitchModel(String switchModel) {
        this.switchModel = switchModel;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Double getGrade1() {
        return grade1;
    }

    public void setGrade1(Double grade1) {
        this.grade1 = grade1;
    }

    public Double getGrade2() {
        return grade2;
    }

    public void setGrade2(Double grade2) {
        this.grade2 = grade2;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    @Override
    public String toString() {
        return "CommissionTplVO{" +
                "id=" + id +
                ", tplName='" + tplName + '\'' +
                ", tplDescribe='" + tplDescribe + '\'' +
                ", switchModel='" + switchModel + '\'' +
                ", profit=" + profit +
                ", num=" + num +
                ", grade1=" + grade1 +
                ", grade2=" + grade2 +
                ", isDefault=" + isDefault +
                '}';
    }
}
