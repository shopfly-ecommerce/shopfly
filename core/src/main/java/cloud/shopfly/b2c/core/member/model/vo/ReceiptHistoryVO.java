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

import cloud.shopfly.b2c.core.member.model.dos.ReceiptHistory;
import cloud.shopfly.b2c.core.trade.sdk.model.OrderDetailDTO;
import cloud.shopfly.b2c.core.trade.sdk.model.OrderSkuDTO;
import cloud.shopfly.b2c.framework.util.BeanUtil;
import cloud.shopfly.b2c.framework.util.CurrencyUtil;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Invoice history objectvo
 *
 * @author zh
 * @version v7.0
 * @date 18/7/24 In the afternoon4:22
 * @since v7.0
 */
public class ReceiptHistoryVO extends ReceiptHistory {

    public ReceiptHistoryVO(ReceiptHistory receiptHistory, OrderDetailDTO orderDetailDTO) {
        List<ReceiptGoodsSkuVO> list = new ArrayList<>();
        for (OrderSkuDTO orderSkuDTO : orderDetailDTO.getOrderSkuList()) {
            ReceiptGoodsSkuVO receiptGoodsSkuVO = new ReceiptGoodsSkuVO();
            BeanUtil.copyProperties(orderSkuDTO, receiptGoodsSkuVO);
            receiptGoodsSkuVO.setDiscount(CurrencyUtil.sub(CurrencyUtil.mul(receiptGoodsSkuVO.getOriginalPrice(),receiptGoodsSkuVO.getNum()), receiptGoodsSkuVO.getSubtotal()));
            list.add(receiptGoodsSkuVO);
        }
        this.setSkuList(list);
        this.setAddTime(receiptHistory.getAddTime());
        this.setReceiptAmount(receiptHistory.getReceiptAmount());
        this.setMemberId(receiptHistory.getMemberId());
        this.setMemberName(receiptHistory.getMemberName());
        this.setOrderSn(receiptHistory.getOrderSn());
        this.setHistoryId(receiptHistory.getHistoryId());
        this.setReceiptType(receiptHistory.getReceiptType());
        this.setReceiptContent(receiptHistory.getReceiptContent());
        this.setReceiptTitle(receiptHistory.getReceiptTitle());
        this.setTaxNo(receiptHistory.getTaxNo());
        this.setShipProvince(orderDetailDTO.getShipProvince());
        this.setShipCity(orderDetailDTO.getShipCity());
        this.setShipTown(orderDetailDTO.getShipTown());
        this.setShipCounty(orderDetailDTO.getShipCounty());
        this.setShipAddr(orderDetailDTO.getShipAddr());
    }

    public ReceiptHistoryVO() {

    }

    /**
     * The goodsskuinformation
     */
    private List<ReceiptGoodsSkuVO> skuList;
    /**
     * Delivery address province
     */
    @ApiModelProperty(value = "Delivery address province")
    private String shipProvince;

    /**
     * Delivery address Town
     */
    @ApiModelProperty(value = "Delivery address Town")
    private String shipTown;
    /**
     * Delivery address city
     */
    @ApiModelProperty(value = "Delivery address city")
    private String shipCity;
    /**
     * Delivery address County
     */
    @ApiModelProperty(value = "Delivery address County")
    private String shipCounty;
    /**
     * Delivery address
     */
    @ApiModelProperty(value = "Delivery address")
    private String shipAddr;

    public List<ReceiptGoodsSkuVO> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<ReceiptGoodsSkuVO> skuList) {
        this.skuList = skuList;
    }

    public String getShipProvince() {
        return shipProvince;
    }

    public void setShipProvince(String shipProvince) {
        this.shipProvince = shipProvince;
    }

    public String getShipTown() {
        return shipTown;
    }

    public void setShipTown(String shipTown) {
        this.shipTown = shipTown;
    }

    public String getShipCity() {
        return shipCity;
    }

    public void setShipCity(String shipCity) {
        this.shipCity = shipCity;
    }

    public String getShipCounty() {
        return shipCounty;
    }

    public void setShipCounty(String shipCounty) {
        this.shipCounty = shipCounty;
    }

    public String getShipAddr() {
        return shipAddr;
    }

    public void setShipAddr(String shipAddr) {
        this.shipAddr = shipAddr;
    }

    @Override
    public String toString() {
        return "ReceiptHistoryVO{" +
                "skuList=" + skuList +
                ", shipProvince='" + shipProvince + '\'' +
                ", shipTown='" + shipTown + '\'' +
                ", shipCity='" + shipCity + '\'' +
                ", shipCounty='" + shipCounty + '\'' +
                ", shipAddr='" + shipAddr + '\'' +
                '}';
    }
}
