/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.member.model.vo;

import dev.shopflix.core.member.model.dos.ReceiptHistory;
import dev.shopflix.core.trade.sdk.model.OrderDetailDTO;
import dev.shopflix.core.trade.sdk.model.OrderSkuDTO;
import dev.shopflix.framework.util.BeanUtil;
import dev.shopflix.framework.util.CurrencyUtil;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * 发票历史对象vo
 *
 * @author zh
 * @version v7.0
 * @date 18/7/24 下午4:22
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
     * 商品的sku信息
     */
    private List<ReceiptGoodsSkuVO> skuList;
    /**
     * 配送地址 省
     */
    @ApiModelProperty(value = "配送地址 省")
    private String shipProvince;

    /**
     * 配送地址 镇
     */
    @ApiModelProperty(value = "配送地址 镇")
    private String shipTown;
    /**
     * 配送地址  城
     */
    @ApiModelProperty(value = "配送地址  城")
    private String shipCity;
    /**
     * 配送地址 县
     */
    @ApiModelProperty(value = "配送地址 县")
    private String shipCounty;
    /**
     * 配送详细地址
     */
    @ApiModelProperty(value = "配送详细地址")
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