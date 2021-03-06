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
package cloud.shopfly.b2c.core.member.model.dos;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author zjp
 * @version v7.0
 * @Description Trust the loginDO
 * @ClassName ConnectDO
 * @since v7.0 In the afternoon2:43 2018/6/20
 */
@Table(name = "es_connect")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ConnectDO {

    /**Id*/
    @Id(name = "id")
    @ApiModelProperty(hidden=true)
    private Integer id;

    /**
     * membersID
     */
    @Column(name = "member_id")
    @ApiModelProperty(name = "member_id",value = "membersid")
    private Integer memberId;
    /**
     * The only signunion_id
     */
    @Column(name = "union_id")
    @ApiModelProperty(name = "union_id", value = "The only sign")
    private String unionId;
    /**
     * Trusted Login type
     */
    @Column(name = "union_type")
    @ApiModelProperty(name = "union_type", value = "Trusted Login type")
    private String unionType;
    /**
     * Solution of time
     */
    @Column(name = "unbound_time")
    @ApiModelProperty(name = "unbound_time",value = "Solution of time")
    private Long unboundTime;

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

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getUnionType() {
        return unionType;
    }

    public void setUnionType(String unionType) {
        this.unionType = unionType;
    }

    public Long getUnboundTime() {
        return unboundTime;
    }

    public void setUnboundTime(Long unboundTime) {
        this.unboundTime = unboundTime;
    }

    @Override
    public String toString() {
        return "ConnectDO{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", unionId='" + unionId + '\'' +
                ", unionType='" + unionType + '\'' +
                ", unboundTime=" + unboundTime +
                '}';
    }
}
