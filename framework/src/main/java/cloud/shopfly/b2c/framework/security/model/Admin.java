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
package cloud.shopfly.b2c.framework.security.model;

/**
 * 管理员角色
 *
 * @author zh
 * @version v7.0
 * @date 18/6/27 上午10:09
 * @since v7.0
 */

public class Admin extends User{

    /**
     * 是否是超级管理员
     */
    private Integer founder;
    /**
     * 权限
     */
    private String role;


    public Integer getFounder() {
        return founder;
    }

    public void setFounder(Integer founder) {
        this.founder = founder;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    @Override
    public String toString() {
        return "Admin{" +
                ", founder=" + founder +
                ", role='" + role + '\'' +
                '}';
    }
}
