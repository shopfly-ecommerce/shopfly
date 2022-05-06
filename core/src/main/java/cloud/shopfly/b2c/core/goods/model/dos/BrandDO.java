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
package cloud.shopfly.b2c.core.goods.model.dos;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


/**
 * 品牌实体
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-16 16:32:45
 */
@Table(name = "es_brand")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BrandDO implements Serializable {

    private static final long serialVersionUID = 9122931201151887L;

    /**
     * 主键
     */
    @Id(name = "brand_id")
    @ApiModelProperty(hidden = true)
    private Integer brandId;
    /**
     * 品牌名称
     */
    @Column()
    @NotEmpty(message = "品牌名称不能为空")
    @ApiModelProperty(value = "品牌名称", required = true)
    private String name;
    /**
     * 品牌图标
     */
    @Column()
    @NotEmpty(message = "品牌图标不能为空")
    @ApiModelProperty(value = "品牌图标", required = true)
    private String logo;
    /**
     * 是否删除，0删除1未删除
     */
    @Column()
    @ApiModelProperty(hidden = true)
    private Integer disabled;

    @PrimaryKeyField
    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    @Override
    public String toString() {
        return "[brandId=" + brandId + ", name=" + name + ", logo=" + logo + ", disabled=" + disabled + "]";
    }

}