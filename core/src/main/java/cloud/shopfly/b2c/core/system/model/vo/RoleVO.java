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

import cloud.shopfly.b2c.core.system.model.dos.RoleDO;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * permissionsvo
 *
 * @author zh
 * @version v7.0
 * @date 18/6/25 In the morning10:59
 * @since v7.0
 */

public class RoleVO {

    @ApiModelProperty(hidden = true)
    private Integer roleId;
    /**
     * Character name
     */
    @NotEmpty(message = "The role name cannot be empty")
    @ApiModelProperty(name = "role_name", value = "Character name", required = true)
    private String roleName;
    /**
     * Role description
     */
    @ApiModelProperty(name = "role_describe", value = "Role description", required = true)
    private String roleDescribe;

    /**
     * Menu permissions of the role
     */
    @ApiModelProperty(name = "menus", value = "Menu permissions of the role", required = true)
    private List<Menus> menus;

    public RoleVO(RoleDO roleDO) {
        this.setRoleId(roleDO.getRoleId());
        this.setRoleName(roleDO.getRoleName());
        this.setRoleDescribe(roleDO.getRoleDescribe());
        this.setMenus(JsonUtil.jsonToList(roleDO.getAuthIds(), Menus.class));
    }

    public RoleVO() {

    }


    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<Menus> getMenus() {
        return menus;
    }

    public void setMenus(List<Menus> menus) {
        this.menus = menus;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleDescribe() {
        return roleDescribe;
    }

    public void setRoleDescribe(String roleDescribe) {
        this.roleDescribe = roleDescribe;
    }

    @Override
    public String toString() {
        return "RoleVO{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", roleDescribe='" + roleDescribe + '\'' +
                ", menus=" + menus +
                '}';
    }
}
