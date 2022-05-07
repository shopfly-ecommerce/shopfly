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
package cloud.shopfly.b2c.core.payment.model.vo;

import java.io.Serializable;
import java.util.Map;

/**
 * @author fk
 * @version v2.0
 * @Description: Return to the original routevo
 * @date 2018/4/2314:12
 * @since v7.0.0
 */
public class RefundBill implements Serializable {

    private static final long serialVersionUID = 6902367702212390171L;

    /**
     * The refund number
     */
    private String refundSn;

    /**
     * The refund amount
     */
    private Double refundPrice;

    /**
     * Transaction amount
     */
    private Double tradePrice;

    /**
     * Third Party Order No.
     */
    private String returnTradeNo;

    /**
     * The parameters used to make the payment
     */
    private Map<String, String> configMap;


    public String getRefundSn() {
        return refundSn;
    }

    public void setRefundSn(String refundSn) {
        this.refundSn = refundSn;
    }

    public Double getRefundPrice() {
        return refundPrice;
    }

    public void setRefundPrice(Double refundPrice) {
        this.refundPrice = refundPrice;
    }

    public Double getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(Double tradePrice) {
        this.tradePrice = tradePrice;
    }

    public String getReturnTradeNo() {
        return returnTradeNo;
    }

    public void setReturnTradeNo(String returnTradeNo) {
        this.returnTradeNo = returnTradeNo;
    }

    public Map<String, String> getConfigMap() {
        return configMap;
    }

    public void setConfigMap(Map<String, String> configMap) {
        this.configMap = configMap;
    }
}
