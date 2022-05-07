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
package cloud.shopfly.b2c.core.aftersale.model.dto;

import cloud.shopfly.b2c.core.aftersale.model.dos.RefundGoodsDO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author zjp
 * @version v7.0
 * @Description Return of the goods(paragraph)A single detailDTO
 * @ClassName RefundDetailDTO
 * @since v7.0 In the morning11:32 2018/5/8
 */
public class RefundDetailDTO implements Serializable {

    @ApiModelProperty(value = "Return of the goods（paragraph）single")
    private RefundDTO refund;

    @ApiModelProperty(value = "Return of the goods")
    private List<RefundGoodsDO> refundGoods;

    public RefundDTO getRefund() {
        return refund;
    }

    public void setRefund(RefundDTO refund) {
        this.refund = refund;
    }

    public List<RefundGoodsDO> getRefundGoods() {
        return refundGoods;
    }

    public void setRefundGoods(List<RefundGoodsDO> refundGoods) {
        this.refundGoods = refundGoods;
    }

    @Override
    public String toString() {
        return "RefundDetailDTO{" +
                "refund=" + refund +
                ", refundGoods=" + refundGoods +
                '}';
    }
}
