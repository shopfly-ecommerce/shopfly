/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.base.plugin.waybill.vo;

import java.util.List;

/**
 * 快递鸟电子面板封装参数实体
 *
 * @author zh
 * @version v1.0
 * @since v7.0
 * 2018年6月10日 上午10:51:10
 */
public class WayBillJson {

    /**
     * 订单编号
     */
    private String orderCode;
    /**
     * 快递公司编码
     */
    private String shipperCode;
    /**
     * 邮费支付方式:1-现付，2-到付，3-月结，4-第三方支付
     */
    private Integer payType;
    /**
     * 快递类型：1-标准快件
     */
    private String expType;
    /**
     * 是否通知快递员上门揽件：0-通知；1-不通知；不填则默认为1
     */
    private Integer isNotice;
    /**
     * 电子面单客户账号（与快递网点申请或通过快递鸟官网申请或通过申请电子面单客户号申请）
     */
    private String customerName;
    /**
     * 电子面单密码
     */
    private String customerPwd;
    /**
     * 寄件费（运费）
     */
    private Double cost;
    /**
     * 其他费用
     */
    private Double otherCost;
    /**
     * 发件人的信息
     */
    private Information sender;
    /**
     * 收件人的信息
     */
    private Information receiver;
    /**
     * 寄送商品信息
     */
    private List<Commodity> commodity;
    /**
     * 物品总重量kg
     */
    private Double weight;
    /**
     * 件数/包裹数
     */
    private Integer quantity;
    /**
     * 物品总体积m3
     */
    private Double volume;
    /**
     * 备注
     */
    private String remark;
    /**
     * 返回电子面单模板：0-不需要；1-需要
     */
    private String isReturnPrintTemplate;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getShipperCode() {
        return shipperCode;
    }

    public void setShipperCode(String shipperCode) {
        this.shipperCode = shipperCode;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getExpType() {
        return expType;
    }

    public void setExpType(String expType) {
        this.expType = expType;
    }

    public Integer getIsNotice() {
        return isNotice;
    }

    public void setIsNotice(Integer isNotice) {
        this.isNotice = isNotice;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPwd() {
        return customerPwd;
    }

    public void setCustomerPwd(String customerPwd) {
        this.customerPwd = customerPwd;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getOtherCost() {
        return otherCost;
    }

    public void setOtherCost(Double otherCost) {
        this.otherCost = otherCost;
    }

    public Information getSender() {
        return sender;
    }

    public void setSender(Information sender) {
        this.sender = sender;
    }

    public Information getReceiver() {
        return receiver;
    }

    public void setReceiver(Information receiver) {
        this.receiver = receiver;
    }

    public List<Commodity> getCommodity() {
        return commodity;
    }

    public void setCommodity(List<Commodity> commodity) {
        this.commodity = commodity;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIsReturnPrintTemplate() {
        return isReturnPrintTemplate;
    }

    public void setIsReturnPrintTemplate(String isReturnPrintTemplate) {
        this.isReturnPrintTemplate = isReturnPrintTemplate;
    }

    @Override
    public String toString() {
        return "WayBillJson{" +
                "orderCode='" + orderCode + '\'' +
                ", shipperCode='" + shipperCode + '\'' +
                ", payType=" + payType +
                ", expType='" + expType + '\'' +
                ", isNotice=" + isNotice +
                ", customerName='" + customerName + '\'' +
                ", customerPwd='" + customerPwd + '\'' +
                ", cost=" + cost +
                ", otherCost=" + otherCost +
                ", add=" + sender +
                ", receiver=" + receiver +
                ", commodity=" + commodity +
                ", weight=" + weight +
                ", quantity=" + quantity +
                ", volume=" + volume +
                ", remark='" + remark + '\'' +
                ", isReturnPrintTemplate='" + isReturnPrintTemplate + '\'' +
                '}';
    }
}
