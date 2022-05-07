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
package cloud.shopfly.b2c.core.member.model.vo;

import java.io.Serializable;

/**
 * Member login message
 *
 * @author fk
 * @version v6.4
 * @since v6.4
 * 2017years10month18On the afternoon9:39:06
 */
public class MemberLoginMsg implements Serializable {

    private static final long serialVersionUID = 8173084471934834777L;

    /**
     * membersid
     */
    private Integer memberId;
    /**
     * Last login time
     */
    private Long lastLoginTime;


    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @Override
    public String toString() {
        return "MemberLoginMsg{" +
                "memberId=" + memberId +
                ", lastLoginTime=" + lastLoginTime +
                '}';
    }
}
