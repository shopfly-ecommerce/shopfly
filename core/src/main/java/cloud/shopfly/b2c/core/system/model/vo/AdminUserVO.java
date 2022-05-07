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

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.*;


/**
 * Platform Administrator EntityVO
 *
 * @author zh
 * @version v7.0
 * @since v7.0.0
 * 2018-06-20 20:38:26
 */
public class AdminUserVO {

    /**
     * Administrator name
     */
    @Pattern(regexp = "^(?![0-9]+$)[\\u4e00-\\u9fa5_0-9A-Za-z]{2,20}$", message = "The name cannot contain only digits or special characters, and contains2-20A character")
    @ApiModelProperty(name = "username", value = "Administrator name", required = false)
    private String username;
    /**
     * Administrator password
     */
    @ApiModelProperty(name = "password", value = "Administrator password", required = false)
    private String password;
    /**
     * department
     */
    @ApiModelProperty(name = "department", value = "department", required = false)
    private String department;
    /**
     * permissionsid
     */
    @Min(message = "permissionsidMust be a number", value = 0)
    @NotNull(message = "Permissions cannot be empty")
    @ApiModelProperty(name = "role_id", value = "permissionsid", required = false)
    private Integer roleId;
    /**
     * note
     */
    @Size(max = 90, message = "Maximum remarks90A character")
    @ApiModelProperty(name = "remark", value = "note", required = false)
    private String remark;
    /**
     * Real name of administrator
     */
    @Pattern(regexp = "^(?![0-9]+$)[\\u4e00-\\u9fa5_0-9A-Za-z]{2,20}$", message = "The real name cannot contain only digits or special characters2-20A character")
    @ApiModelProperty(name = "real_name", value = "Real name of administrator", required = false)
    private String realName;
    /**
     * Head portrait
     */
    @ApiModelProperty(name = "face", value = "Head portrait", required = false)
    private String face;
    /**
     * Whether to be a super administrator
     */
    @Min(message = "The value must be a number and,1As the super administrator,0For other", value = 0)
    @Max(message = "The value must be a number and,1As the super administrator,0For other", value = 1)
    @NotNull(message = "Super administrator Or not The value cannot be empty")
    @ApiModelProperty(name = "founder", value = "Whether to be a super administrator,1As the super administrator,0For other", required = false)
    private Integer founder;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        AdminUserVO that = (AdminUserVO) o;
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
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) {
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
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (department != null ? department.hashCode() : 0);
        result = 31 * result + (roleId != null ? roleId.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (realName != null ? realName.hashCode() : 0);
        result = 31 * result + (face != null ? face.hashCode() : 0);
        result = 31 * result + (founder != null ? founder.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AdminUserVO{" +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", department='" + department + '\'' +
                ", roleId=" + roleId +
                ", remark='" + remark + '\'' +
                ", realName='" + realName + '\'' +
                ", face='" + face + '\'' +
                ", founder=" + founder +
                '}';
    }


}
