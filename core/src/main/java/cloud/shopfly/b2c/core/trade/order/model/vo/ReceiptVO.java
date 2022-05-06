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
package cloud.shopfly.b2c.core.trade.order.model.vo;

import cloud.shopfly.b2c.core.member.model.vo.MemberReceiptVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 发票
 *
 * @author kingapex
 * @version 1.0
 * @since v7.0.0
 * 2017年5月24日下午9:13:53
 */
@ApiModel(description = "发票")
public class ReceiptVO extends MemberReceiptVO implements Serializable {

    private static final long serialVersionUID = -6389742728556211209L;
    /**
     * 普票类型
     */
    @ApiModelProperty(name = "type", value = "普票类型，0为个人，其他为公司", required = false)
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ReceiptVO{" +
                "type=" + type +
                '}';
    }
}
