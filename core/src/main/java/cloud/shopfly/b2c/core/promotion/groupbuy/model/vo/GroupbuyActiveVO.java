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
package cloud.shopfly.b2c.core.promotion.groupbuy.model.vo;

import cloud.shopfly.b2c.core.promotion.groupbuy.model.dos.GroupbuyActiveDO;
import cloud.shopfly.b2c.core.promotion.groupbuy.model.enums.GroupBuyStatusEnum;
import cloud.shopfly.b2c.framework.util.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 团购活动VO
 *
 * @author Snow create in 2018/6/13
 * @version v2.0
 * @since v7.0.0
 */
@ApiModel
public class GroupbuyActiveVO extends GroupbuyActiveDO {

    @ApiModelProperty(name = "status_text", value = "状态值")
    private String statusText;

    @ApiModelProperty(name = "status", value = "活动状态标识,expired表示已失效")
    private String status;

    public String getStatusText() {
        Long now = DateUtil.getDateline();
        if (this.getStartTime() > now) {
            return GroupBuyStatusEnum.NOT_BEGIN.getStatus();
        } else if (this.getStartTime() < now && this.getEndTime() > now) {
            return GroupBuyStatusEnum.CONDUCT.getStatus();
        } else {
            return GroupBuyStatusEnum.OVERDUE.getStatus();
        }
    }

    public String getStatus() {
        Long now = DateUtil.getDateline();
        if (this.getStartTime() > now) {
            return null;
        } else if (this.getStartTime() < now && this.getEndTime() > now) {
            return null;
        } else {
            return "expired";
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    @Override
    public String toString() {
        return "GroupbuyActiveVO{" +
                "statusText='" + statusText + '\'' +
                '}';
    }
}
