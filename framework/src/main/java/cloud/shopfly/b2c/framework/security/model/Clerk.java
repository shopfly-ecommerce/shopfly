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
 * 店员
 *
 * @author zh
 * @version v7.0
 * @date 18/8/4 下午3:12
 * @since v7.0
 */

public class Clerk extends Seller {

    /**
     * 店员id
     */
    private Integer clerkId;
    /**
     * 店员名称
     */
    private String clerkName;
    /**
     * 是否是超级店员
     */
    private Integer founder;
    /**
     * 权限
     */
    private String role;

    public Clerk() {
        //clerk有 买家的角色和卖宾角色
        add(Role.CLERK.name());
    }

    public Integer getClerkId() {
        return clerkId;
    }

    public void setClerkId(Integer clerkId) {
        this.clerkId = clerkId;
    }

    public String getClerkName() {
        return clerkName;
    }

    public void setClerkName(String clerkName) {
        this.clerkName = clerkName;
    }

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
        return "Clerk{" +
                "clerkId=" + clerkId +
                ", clerkName='" + clerkName + '\'' +
                ", founder=" + founder +
                ", role='" + role + '\'' +
                '}';
    }
}
