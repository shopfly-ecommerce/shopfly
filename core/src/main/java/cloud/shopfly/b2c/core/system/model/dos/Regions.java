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

import cloud.shopfly.b2c.core.member.model.vo.RegionVO;
import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * 地区实体
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-04-28 13:49:38
 */
@Table(name = "es_regions")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Regions implements Serializable {

    private static final long serialVersionUID = 8051779001011335L;

    /**
     * 地区id
     */
    @Id(name = "id")
    @ApiModelProperty(hidden = true)
    private Integer id;
    /**
     * 父地区id
     */
    @Column(name = "parent_id")
    @Min(message = "必须为数字", value = 0)
    @ApiModelProperty(name = "parent_id", value = "父地区id", required = false)
    private Integer parentId;
    /**
     * 路径
     */
    @Column(name = "region_path")
    @ApiModelProperty(name = "region_path", value = "路径", required = false)
    private String regionPath;
    /**
     * 级别
     */
    @Column(name = "region_grade")
    @ApiModelProperty(name = "region_grade", value = "级别", required = false)
    private Integer regionGrade;
    /**
     * 名称
     */
    @Column(name = "local_name")
    @ApiModelProperty(name = "local_name", value = "名称", required = false)
    private String localName;
    /**
     * 邮编
     */
    @Column(name = "zipcode")
    @ApiModelProperty(name = "zipcode", value = "邮编", required = false)
    private String zipcode;
    /**
     * 是否支持货到付款
     */
    @Column(name = "cod")
    @ApiModelProperty(name = "cod", value = "是否支持货到付款,1支持，0不支持", required = false)
    private Integer cod;

    @PrimaryKeyField
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getRegionPath() {
        return regionPath;
    }

    public void setRegionPath(String regionPath) {
        this.regionPath = regionPath;
    }

    public Integer getRegionGrade() {
        return regionGrade;
    }

    public void setRegionGrade(Integer regionGrade) {
        this.regionGrade = regionGrade;
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Regions that = (Regions) o;
        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (parentId != null ? !parentId.equals(that.parentId) : that.parentId != null) {
            return false;
        }
        if (regionPath != null ? !regionPath.equals(that.regionPath) : that.regionPath != null) {
            return false;
        }
        if (regionGrade != null ? !regionGrade.equals(that.regionGrade) : that.regionGrade != null) {
            return false;
        }
        if (localName != null ? !localName.equals(that.localName) : that.localName != null) {
            return false;
        }
        if (zipcode != null ? !zipcode.equals(that.zipcode) : that.zipcode != null) {
            return false;
        }
        return cod != null ? cod.equals(that.cod) : that.cod == null;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (regionPath != null ? regionPath.hashCode() : 0);
        result = 31 * result + (regionGrade != null ? regionGrade.hashCode() : 0);
        result = 31 * result + (localName != null ? localName.hashCode() : 0);
        result = 31 * result + (zipcode != null ? zipcode.hashCode() : 0);
        result = 31 * result + (cod != null ? cod.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Regions{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", regionPath='" + regionPath + '\'' +
                ", regionGrade=" + regionGrade +
                ", localName='" + localName + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", cod=" + cod +
                '}';
    }

    /**
     * region转vo
     *
     * @return
     */
    public RegionVO toVo() {
        RegionVO vo = new RegionVO();
        vo.setLocalName(this.getLocalName());
        vo.setParentId(this.getParentId());
        vo.setId(this.getId());
        vo.setChildren(new ArrayList<RegionVO>());
        vo.setLevel(this.getRegionGrade());
        return vo;
    }

}