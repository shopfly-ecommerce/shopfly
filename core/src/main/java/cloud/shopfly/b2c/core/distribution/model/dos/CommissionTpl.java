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
package cloud.shopfly.b2c.core.distribution.model.dos;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 提成模版
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/17 下午3:24
 */
@Table(name = "es_commission_tpl")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CommissionTpl implements Serializable {


    /**
     * 主键
     */
    @Id(name = "id")
    @ApiModelProperty(hidden = true)
    private int id;

    /**
     * 模板名称
     */
    @Column(name = "tpl_name")
    @NotEmpty(message = "模版名称不能为空")
    @ApiModelProperty(name = "tpl_name", value = "模版名称", required = false)
    private String tplName;

    /**
     * 模板说明
     */
    @Column(name = "tpl_describe")
    @ApiModelProperty(name = "tpl_describe", value = "模版描述", required = false)
    private String tplDescribe;

    /**
     * 自动切换条件
     */
    @Column(name = "switch_model")
    @NotEmpty(message = "切换模式不能为空")
    @ApiModelProperty(name = "switch_model", value = "模版描述 MANUAL=手动 / AUTOMATIC=自动", required = false)
    private String switchModel;


    /**
     * 切换条件：利润要求
     */
    @Column()
    @NotNull(message = "利润要求不能为空")
    @ApiModelProperty(value = "利润要求", required = false)
    private Double profit;


    /**
     * 切换条件：下线人数
     */
    @Column()
    @NotNull(message = "下线人数 不能为空")
    @ApiModelProperty(value = "下线人数", required = false)
    private Integer num;


    /**
     * 与消费者相差1级 时返利金额
     */
    @Column()
    @NotNull(message = "相差1级返利金额 不能为空")
    @ApiModelProperty(value = "相差1级返利金额 百分比值：填写1 则为百分之1", required = false)
    private Double grade1;

    /**
     * 与消费者相差2级 时返利金额
     */
    @Column()
    @NotNull(message = "相差2级返利金额 不能为空")
    @ApiModelProperty(value = "相差2级返利金额 百分比值：填写1 则为百分之1", required = false)
    private Double grade2;


    /**
     * 是否默认 1 默认，0 非默认
     */
    @Column(name = "is_default")
    @NotNull(message = "默认参数不能为空")
    @ApiModelProperty(name = "is_default", value = "是否默认：1默认，0非默认", required = false)
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommissionTpl that = (CommissionTpl) o;
        if (tplName != null ? !tplName.equals(that.tplName) : that.tplName != null) {
            return false;
        }
        if (tplDescribe != null ? !tplDescribe.equals(that.tplDescribe) : that.tplDescribe != null) {
            return false;
        }
        if (switchModel != null ? !switchModel.equals(that.switchModel) : that.switchModel != null) {
            return false;
        }
        if (profit != null ? !profit.equals(that.profit) : that.profit != null) {
            return false;
        }
        if (num != null ? !num.equals(that.num) : that.num != null) {
            return false;
        }
        if (grade1 != null ? !grade1.equals(that.grade1) : that.grade1 != null) {
            return false;
        }
        if (grade2 != null ? !grade2.equals(that.grade2) : that.grade2 != null) {
            return false;
        }
        return isDefault != null ? isDefault.equals(that.isDefault) : that.isDefault == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (tplName != null ? tplName.hashCode() : 0);
        result = 31 * result + (tplDescribe != null ? tplDescribe.hashCode() : 0);
        result = 31 * result + (switchModel != null ? switchModel.hashCode() : 0);
        result = 31 * result + (profit != null ? profit.hashCode() : 0);
        result = 31 * result + (num != null ? num.hashCode() : 0);
        result = 31 * result + (grade1 != null ? grade1.hashCode() : 0);
        result = 31 * result + (grade2 != null ? grade2.hashCode() : 0);
        result = 31 * result + (isDefault != null ? isDefault.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CommissionTpl{" +
                " tplName='" + tplName + '\'' +
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