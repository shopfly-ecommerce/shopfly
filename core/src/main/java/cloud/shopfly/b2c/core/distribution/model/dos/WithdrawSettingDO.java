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

/**
 * 提现设置实体
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/21 下午3:01
 */
@Table(name = "es_withdraw_setting")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class WithdrawSettingDO {

    /**
     * 主键id
     */
    @Id(name = "id")
    @ApiModelProperty(hidden = true)
    private Integer id;
    /**
     * 用户id
     **/
    @Column(name = "member_id")
    @ApiModelProperty(value = "用户id", required = true)
    private Integer memberId;
    /**
     * 参数
     **/
    @Column()
    @ApiModelProperty(value = "参数", required = true)
    private String param;

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

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return "WithdrawSettingDO{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", param='" + param + '\'' +
                '}';
    }
}
