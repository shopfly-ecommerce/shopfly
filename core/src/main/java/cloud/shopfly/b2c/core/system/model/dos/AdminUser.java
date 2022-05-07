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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * Platform Administrator Entity
 *
 * @author zh
 * @version v7.0
 * @since v7.0.0
 * 2018-06-20 20:38:26
 */
@Table(name = "es_admin_user")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AdminUser implements Serializable {

    private static final long serialVersionUID = 9076352194131639L;

    /**
     * Platform administratorid
     */
    @Id(name = "id")
    @ApiModelProperty(hidden = false)
    private Integer id;
    /**
     * Administrator name
     */
    @Column(name = "username")
    @ApiModelProperty(name = "username", value = "Administrator name", required = false)
    private String username;
    /**
     * Administrator password
     */
    @Column(name = "password")
    @ApiModelProperty(name = "password", value = "Administrator password", required = false)
    private String password;
    /**
     * department
     */
    @Column(name = "department")
    @ApiModelProperty(name = "department", value = "department", required = false)
    private String department;
    /**
     * permissionsid
     */
    @Column(name = "role_id")
    @ApiModelProperty(name = "role_id", value = "permissionsid", required = false)
    private Integer roleId;
    /**
     * Creation date
     */
    @Column(name = "date_line")
    @ApiModelProperty(name = "date_line", value = "Creation date", required = false)
    private Long dateLine;
    /**
     * note
     */
    @Column(name = "remark")
    @ApiModelProperty(name = "remark", value = "note", required = false)
    private String remark;
    /**
     * Whether or not to delete
     */
    @Column(name = "user_state")
    @ApiModelProperty(name = "user_state", value = "Whether or not to delete,0For the normal,-1Is deleted state", required = false)
    private Integer userState;
    /**
     * Real name of administrator
     */
    @Column(name = "real_name")
    @ApiModelProperty(name = "real_name", value = "Real name of administrator", required = false)
    private String realName;
    /**
     * Head portrait
     */
    @Column(name = "face")
    @ApiModelProperty(name = "face", value = "Head portrait", required = false)
    private String face;
    /**
     * Whether to be a super administrator
     */
    @Column(name = "founder")
    @ApiModelProperty(name = "founder", value = "Whether to be a super administrator,1As the super administrator,0For other", required = false)
    private Integer founder;

    @PrimaryKeyField
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Long getDateLine() {
        return dateLine;
    }

    public void setDateLine(Long dateLine) {
        this.dateLine = dateLine;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getUserState() {
        return userState;
    }

    public void setUserState(Integer userState) {
        this.userState = userState;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public Integer getFounder() {
        return founder;
    }

    public void setFounder(Integer founder) {
        this.founder = founder;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AdminUser that = (AdminUser) o;
        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (username != null ? !username.equals(that.username) : that.username != null) {
            return false;
        }
        if (password != null ? !password.equals(that.password) : that.password != null) {
            return false;
        }
        if (department != null ? !department.equals(that.department) : that.department != null) {
            return false;
        }
        if (roleId != null ? !roleId.equals(that.roleId) : that.roleId != null) {
            return false;
        }
        if (dateLine != null ? !dateLine.equals(that.dateLine) : that.dateLine != null) {
            return false;
        }
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) {
            return false;
        }
        if (userState != null ? !userState.equals(that.userState) : that.userState != null) {
            return false;
        }
        if (realName != null ? !realName.equals(that.realName) : that.realName != null) {
            return false;
        }
        if (face != null ? !face.equals(that.face) : that.face != null) {
            return false;
        }
        return founder != null ? founder.equals(that.founder) : that.founder == null;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (department != null ? department.hashCode() : 0);
        result = 31 * result + (roleId != null ? roleId.hashCode() : 0);
        result = 31 * result + (dateLine != null ? dateLine.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (userState != null ? userState.hashCode() : 0);
        result = 31 * result + (realName != null ? realName.hashCode() : 0);
        result = 31 * result + (face != null ? face.hashCode() : 0);
        result = 31 * result + (founder != null ? founder.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AdminUserVO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", department='" + department + '\'' +
                ", roleId=" + roleId +
                ", dateLine=" + dateLine +
                ", remark='" + remark + '\'' +
                ", userState=" + userState +
                ", realName='" + realName + '\'' +
                ", face='" + face + '\'' +
                ", founder=" + founder +
                '}';
    }


}
