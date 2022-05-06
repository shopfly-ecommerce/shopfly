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
package cloud.shopfly.b2c.core.system.model.dto;

import cloud.shopfly.b2c.core.system.model.dos.AdminUser;
import io.swagger.annotations.ApiModelProperty;

/**
 * 管理员对象 用于管理员列表显示
 *
 * @author zh
 * @version v7.0
 * @date 18/6/27 下午2:42
 * @since v7.0
 */

public class AdminUserDTO extends AdminUser {

    @ApiModelProperty(name = "role_name", value = "角色名称", required = false)
    private String roleName;

    public String getRoleName() {
        if (this.getFounder().equals(1)) {
            roleName = "超级管理员";
        }
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }


    @Override
    public String toString() {
        return "AdminUserDTO{" +
                "roleName='" + roleName + '\'' +
                '}';
    }
}
