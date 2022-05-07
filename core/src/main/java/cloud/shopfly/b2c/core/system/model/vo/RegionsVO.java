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
package cloud.shopfly.b2c.core.system.model.vo;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


/**
 * Regional entities
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-04-28 13:49:38
 */
public class RegionsVO {

    /**
     * The parent regionid
     */
    @Column(name = "parent_id")
    @Min(message = "Must be a number", value = 0)
    @NotNull(message = "The fatheridCant be empty")
    @ApiModelProperty(name = "parent_id", value = "The parent regionid, top-level classification fill0", required = true)
    private Integer parentId;
    /**
     * The name of the
     */
    @Column(name = "local_name")
    @ApiModelProperty(name = "local_name", value = "The name of the")
    @NotEmpty(message = "The region name cannot be empty")
    private String localName;
    /**
     * Zip code
     */
    @Column(name = "zipcode")
    @ApiModelProperty(name = "zipcode", value = "Zip code", required = false)
    private String zipcode;
    /**
     * Do you support CASH on delivery
     */
    @Column(name = "cod")
    @ApiModelProperty(name = "cod", value = "Do you support CASH on delivery,1support,0不support")
    @Min(message = "Do you support CASH on delivery,1support,0不support", value = 0)
    @Max(message = "Do you support CASH on delivery,1support,0不support", value = 1)
    @NotNull(message = "Whether cash on delivery is supported cannot be empty")
    private Integer cod;


    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    @Override
    public String toString() {
        return "RegionsVO{" +
                "parentId=" + parentId +
                ", localName='" + localName + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", cod=" + cod +
                '}';
    }
}
