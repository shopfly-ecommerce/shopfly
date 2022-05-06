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
package cloud.shopfly.b2c.core.distribution.model.dto;

import java.io.Serializable;

/**
 * DistributionSellerBillDTO
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-09-07 上午7:58
 */
public class DistributionSellerBillDTO implements Serializable {

    /**
     * 商家id
     */
    private Integer sellerId;
    /**
     * 支出总计
     */
    private Double countExpenditure;
    /**
     * 支出退换
     */
    private Double returnExpenditure;

    @Override
    public String toString() {
        return "DistributionSellerBillDTO{" +
                "sellerId=" + sellerId +
                ", countExpenditure=" + countExpenditure +
                ", returnExpenditure=" + returnExpenditure +
                '}';
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public Double getCountExpenditure() {
        return countExpenditure;
    }

    public void setCountExpenditure(Double countExpenditure) {
        this.countExpenditure = countExpenditure;
    }

    public Double getReturnExpenditure() {
        return returnExpenditure;
    }

    public void setReturnExpenditure(Double returnExpenditure) {
        this.returnExpenditure = returnExpenditure;
    }
}
