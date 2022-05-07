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

/**
 * Administrator Loginvo
 *
 * @author zh
 * @version v7.0
 * @date 18/6/27 In the morning9:28
 * @since v7.0
 */

public class AdminLoginVO {
    /**
     * Platform administratorid
     */
    private Integer uid;
    /**
     * Administrator name
     */
    private String username;
    /**
     * department
     */
    private String department;
    /**
     * Head portrait
     */
    private String face;

    /**
     * roleid
     */
    private Integer roleId;
    /**
     * Yes No Super administrator
     */
    private Integer founder;
    /**
     * token
     */
    private String accessToken;
    /**
     * The refreshtoken
     */
    private String refreshToken;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getFounder() {
        return founder;
    }

    public void setFounder(Integer founder) {
        this.founder = founder;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "AdminLoginVO{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", department='" + department + '\'' +
                ", face='" + face + '\'' +
                ", roleId=" + roleId +
                ", founder=" + founder +
                ", accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
