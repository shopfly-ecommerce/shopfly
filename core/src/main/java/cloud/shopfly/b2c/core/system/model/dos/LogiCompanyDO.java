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
 * Logistics company entity
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
    @ApiModelProperty(name = "id", value = "Logistics companyid", required = false)
    private Integer id;
    /**
     * Name of logistics Company
     */
    @Column(name = "name")
    @NotEmpty(message = "Logistics company Name This parameter is mandatory")
    @ApiModelProperty(name = "name", value = "Name of logistics Company", required = true)
    private String name;
    /**
     * Logistics companycode
     */
    @Column(name = "code")
    @NotEmpty(message = "Logistics companycoderequired")
    @ApiModelProperty(name = "code", value = "Logistics companycode", required = true)
    private String code;
    /**
     * Express Bird Logistics Companycode
     */
    @Column(name = "kdcode")
    @ApiModelProperty(name = "kdcode", value = "Express Bird Logistics Companycode", required = true)
    private String kdcode;
    /**
     * Whether electronic planes are supported1：support0：不support
     */
    @Column(name = "is_waybill")
    @NotNull(message = "Supported Or not This parameter is mandatory")
    @ApiModelProperty(name = "is_waybill", value = "Whether electronic planes are supported1：support0：不support", required = true)
    private Integer isWaybill;
    /**
     * Customer number of logistics company
     */
    @Column(name = "customer_name")
    @ApiModelProperty(name = "customer_name", value = "Customer number of logistics company", required = false)
    private String customerName;
    /**
     * Logistics company electronic surface single password
     */
    @Column(name = "customer_pwd")
    @ApiModelProperty(name = "customer_pwd", value = "Logistics company electronic surface single password", required = false)
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
