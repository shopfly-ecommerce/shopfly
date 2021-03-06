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
package cloud.shopfly.b2c.core.statistics.model.dto;

import cloud.shopfly.b2c.core.trade.order.model.dos.OrderDO;
import cloud.shopfly.b2c.core.trade.order.model.dos.OrderItemsDO;
import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import cloud.shopfly.b2c.framework.util.CurrencyUtil;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

/**
 * Order data
 *
 * @author chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/3/22 In the afternoon11:51
 */

@Table(name="es_sss_order_goods_data")
public class OrderGoodsData {

    @ApiModelProperty(value = "A primary keyid")
    @Column(name = "id")
    @Id
    private Integer id;

    @ApiModelProperty(value = "Order no.")
    @Column(name = "order_sn")
    private String orderSn;

    @ApiModelProperty(value = "productid")
    @Column(name = "goods_id")
    private Integer goodsId;

    @ApiModelProperty(value = "Purchase quantity")
    @Column(name = "goods_num")
    private Integer goodsNum;


    @ApiModelProperty(value = "Name")
    @Column(name = "goods_name")
    private String goodsName;

    @ApiModelProperty(value = "Price")
    @Column(name = "price")
    private Double price;


    @ApiModelProperty(value = "reporter")
    @Column(name = "sub_total")
    private Double subTotal;


    @ApiModelProperty(value = "Categoriesid")
    @Column(name = "category_id")
    private Integer categoryId;

    @ApiModelProperty(value = "Categoriespath")
    @Column(name = "category_path")
    private String categoryPath;

    @ApiModelProperty(value = "industryid")
    @Column(name = "industry_id")
    private Integer industryId;

    @ApiModelProperty(value = "Last update")
    @Column(name = "create_time")
    private Long createTime;

    public OrderGoodsData(OrderItemsDO orderItem, OrderDO order) {

        this.setCategoryId(orderItem.getCatId());
        this.setCreateTime(order.getCreateTime());
        this.setGoodsId(orderItem.getGoodsId());
        this.setGoodsName(orderItem.getName());
        this.setGoodsNum(orderItem.getNum());
        this.setOrderSn(order.getSn());
        this.setPrice(orderItem.getPrice());
        this.setSubTotal(CurrencyUtil.mul(orderItem.getPrice(), orderItem.getNum()));

    }

    public OrderGoodsData(Map<String, Object> map) {
        this.setCategoryId((Integer) map.get("category_id"));
        this.setCreateTime((Long) map.get("create_time"));
        this.setGoodsId((Integer) map.get("goods_id"));
        this.setGoodsName((String) map.get("goods_name"));
        this.setGoodsNum((Integer) map.get("goods_num"));
        this.setOrderSn((String) map.get("order_sn"));
        this.setPrice((Double) map.get("price"));
        this.setSubTotal((Double) map.get("sub_total"));
        this.setCategoryPath((String) map.get("category_path"));
        this.setIndustryId((Integer) map.get("industry_id"));
    }

    public OrderGoodsData() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategoryPath() {
        return categoryPath;
    }

    public void setCategoryPath(String categoryPath) {
        this.categoryPath = categoryPath;
    }

    public Integer getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Integer industryId) {
        this.industryId = industryId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "OrderGoodsData{" +
                "id=" + id +
                ", orderSn='" + orderSn + '\'' +
                ", goodsId=" + goodsId +
                ", goodsNum=" + goodsNum +
                ", goodsName='" + goodsName + '\'' +
                ", price=" + price +
                ", subTotal=" + subTotal +
                ", categoryId=" + categoryId +
                ", categoryPath='" + categoryPath + '\'' +
                ", industryId=" + industryId +
                ", createTime=" + createTime +
                '}';
    }
}
