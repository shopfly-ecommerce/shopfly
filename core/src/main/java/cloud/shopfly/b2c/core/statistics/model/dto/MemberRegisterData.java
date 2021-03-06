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
package cloud.shopfly.b2c.core.statistics.model.dto;

import cloud.shopfly.b2c.core.member.model.dos.Member;
import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import io.swagger.annotations.ApiModelProperty;

/**
 * Member registration Data
 *
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018/3/25 In the afternoon10:47
 */

@Table(name="es_sss_member_register_data")
public class MemberRegisterData {


    private Integer id;

    @ApiModelProperty(value = "membersid")
    @Column(name = "member_id")
    private Integer memberId;

    @ApiModelProperty(value = "Member name")
    @Column(name = "member_name")
    private String memberName;

    @ApiModelProperty(value = "Creation date")
    @Column(name = "create_time")
    private Long createTime;

    public MemberRegisterData() {
    }


	public MemberRegisterData(Member member) {
		this.setCreateTime(member.getCreateTime());
		this.setMemberId(member.getMemberId());
		this.setMemberName(member.getUname());
	}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MemberRegisterData that = (MemberRegisterData) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (memberId != null ? !memberId.equals(that.memberId) : that.memberId != null) {
            return false;
        }
        if (memberName != null ? !memberName.equals(that.memberName) : that.memberName != null) {
            return false;
        }
        return createTime != null ? createTime.equals(that.createTime) : that.createTime == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (memberId != null ? memberId.hashCode() : 0);
        result = 31 * result + (memberName != null ? memberName.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MemberRegisterData{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", memberName='" + memberName + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
