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
package cloud.shopfly.b2c.core.system.model.dos;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * 物流公司实体
 *
 * @author zjp
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-29 15:10:38
 */
@Table(name = "es_logi_company")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LogiCompanyDO implements Serializable {

    private static final long serialVersionUID = 2885097420270994L;

    public LogiCompanyDO() {
        super();
    }

    public LogiCompanyDO(String name, String code, String kdcode, Integer isWaybill) {
        super();
        this.name = name;
        this.code = code;
        this.kdcode = kdcode;
        this.isWaybill = isWaybill;
    }

    /**
     * ID
     */
    @Id(name = "id")
    @ApiModelProperty(name = "id", value = "物流公司id", required = false)
    private Integer id;
    /**
     * 物流公司名称
     */
    @Column(name = "name")
    @NotEmpty(message = "物流公司名称必填")
    @ApiModelProperty(name = "name", value = "物流公司名称", required = true)
    private String name;
    /**
     * 物流公司code
     */
    @Column(name = "code")
    @NotEmpty(message = "物流公司code必填")
    @ApiModelProperty(name = "code", value = "物流公司code", required = true)
    private String code;
    /**
     * 快递鸟物流公司code
     */
    @Column(name = "kdcode")
    @ApiModelProperty(name = "kdcode", value = "快递鸟物流公司code", required = true)
    private String kdcode;
    /**
     * 是否支持电子面单1：支持 0：不支持
     */
    @Column(name = "is_waybill")
    @NotNull(message = "是否支持电子面单必填")
    @ApiModelProperty(name = "is_waybill", value = "是否支持电子面单1：支持 0：不支持", required = true)
    private Integer isWaybill;
    /**
     * 物流公司客户号
     */
    @Column(name = "customer_name")
    @ApiModelProperty(name = "customer_name", value = "物流公司客户号", required = false)
    private String customerName;
    /**
     * 物流公司电子面单密码
     */
    @Column(name = "customer_pwd")
    @ApiModelProperty(name = "customer_pwd", value = "物流公司电子面单密码", required = false)
    private String customerPwd;

    @PrimaryKeyField
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getKdcode() {
        return kdcode;
    }

    public void setKdcode(String kdcode) {
        this.kdcode = kdcode;
    }

    public Integer getIsWaybill() {
        return isWaybill;
    }

    public void setIsWaybill(Integer isWaybill) {
        this.isWaybill = isWaybill;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPwd() {
        return customerPwd;
    }

    public void setCustomerPwd(String customerPwd) {
        this.customerPwd = customerPwd;
    }

    @Override
    public String toString() {
        return "LogiCompanyDO [id=" + id + ", name=" + name + ", code=" + code + ", kdcode=" + kdcode + ", isWaybill="
                + isWaybill + ", customerName=" + customerName + ", customerPwd=" + customerPwd + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        LogiCompanyDO other = (LogiCompanyDO) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }

        return true;
    }
}