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
package cloud.shopfly.b2c.core.distribution.model.dos;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * Template upgrade logs
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/21 In the morning10:35
 */

@Table(name = "es_upgrade_log")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UpgradeLogDO {

    /**
     * Template upgrade logsid
     */
    @Id(name = "id")
    @ApiModelProperty(hidden = true)
    private Integer id;

    /**
     * membersid
     */
    @Column(name = "member_id")
    @ApiModelProperty(value = "membersid")
    private Integer memberId;

    /**
     * Member name
     */
    @Column(name = "member_name")
    @ApiModelProperty(value = "Member name")
    private String memberName;

    /**
     * Switch type（1=Manual,2=automatic）
     */
    @Column()
    @ApiModelProperty(value = "Switch type")
    private String type;

    /**
     * The old templateid
     */
    @Column(name = "old_tpl_id")
    @ApiModelProperty(value = "The old templateid")
    private Integer oldTplId;

    /**
     * Old template name
     */
    @Column(name = "old_tpl_name")
    @ApiModelProperty(value = "Old template name")
    private String oldTplName;

    /**
     * A new templateid
     */
    @Column(name = "new_tpl_id")
    @ApiModelProperty(value = "A new templateid")
    private Integer newTplId;

    /**
     * New template name
     */
    @Column(name = "new_tpl_name")
    @ApiModelProperty(value = "New template name")
    private String newTplName;

    /**
     * Update time
     */
    @Column(name = "create_time")
    @ApiModelProperty(value = "Creation date")
    private Long createTime;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UpgradeLogDO that = (UpgradeLogDO) o;

        if (!Objects.equals(id, that.id)) {
            return false;
        }
        if (!Objects.equals(memberId, that.memberId)) {
            return false;
        }
        if (type != that.type) {
            return false;
        }
        if (!Objects.equals(oldTplId, that.oldTplId)) {
            return false;
        }
        if (!Objects.equals(newTplId, that.newTplId)) {
            return false;
        }
        if (!Objects.equals(createTime, that.createTime)) {
            return false;
        }
        if (memberName != null ? !memberName.equals(that.memberName) : that.memberName != null) {
            return false;
        }
        if (oldTplName != null ? !oldTplName.equals(that.oldTplName) : that.oldTplName != null) {
            return false;
        }
        return newTplName != null ? newTplName.equals(that.newTplName) : that.newTplName == null;
    }


    @Override
    public String toString() {
        return "UpgradeLog{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", memberName='" + memberName + '\'' +
                ", type=" + type +
                ", oldTplId=" + oldTplId +
                ", oldTplName='" + oldTplName + '\'' +
                ", newTplId=" + newTplId +
                ", newTplName='" + newTplName + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getOldTplId() {
        return oldTplId;
    }

    public void setOldTplId(int oldTplId) {
        this.oldTplId = oldTplId;
    }

    public String getOldTplName() {
        return oldTplName;
    }

    public void setOldTplName(String oldTplName) {
        this.oldTplName = oldTplName;
    }

    public int getNewTplId() {
        return newTplId;
    }

    public void setNewTplId(int newTplId) {
        this.newTplId = newTplId;
    }

    public String getNewTplName() {
        return newTplName;
    }

    public void setNewTplName(String newTplName) {
        this.newTplName = newTplName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
