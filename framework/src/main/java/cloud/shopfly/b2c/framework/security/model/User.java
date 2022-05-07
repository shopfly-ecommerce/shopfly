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

import cloud.shopfly.b2c.framework.auth.AuthUser;

import java.util.ArrayList;
import java.util.List;

/**
 * The user
 * Created by kingapex on 2018/3/8.
 *
 * @author kingapex
 * @version 1.0
 * @since 6.4.0
 * 2018/3/8
 */
public class User implements AuthUser {

    /**
     * membersid
     */
    private Integer uid;

    /**
     * A unique identifier
     */
    private String uuid;

    /**
     * Username
     */
    private String username;
    /**
     * role
     */
    private List<String> roles;

    public User() {

        roles = new ArrayList<>();
        this.test(String.class);
    }

    /**
     * Define roles for users
     *
     * @param roles Character set
     */
    public void add(String... roles) {
        for (String role : roles) {
            this.roles.add(role);
        }
    }

    void test(Class tClass) {


    }

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

    @Override
    public List<String> getRoles() {
        return roles;
    }

    @Override
    public void setRoles(List<String> roles) {
        this.roles= roles;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
