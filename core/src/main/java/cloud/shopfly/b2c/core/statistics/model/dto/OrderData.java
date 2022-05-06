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
import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

/**
 * 统计订单数据
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018/3/22 下午11:50
 * @Description:
 */

@Table(name="es_sss_order_data")
public class OrderData {
	@ApiModelProperty(value = "主键id")
	@Id
	private Integer id;


	@ApiModelProperty(value = "会员id")
	@Column(name = "buyer_id")
	private Integer buyerId;

	@ApiModelProperty(value = "购买人")
	@Column(name = "buyer_name")
	private String buyerName;

	@ApiModelProperty(value = "订单编号")
	@Column(name = "sn")
	private String sn;


	@ApiModelProperty(value = "订单状态")
	@Column(name = "order_status")
	private String orderStatus;

	@ApiModelProperty(value = "付款状态")
	@Column(name = "pay_status")
	private String payStatus;

	@ApiModelProperty(value = "订单金额")
	@Column(name = "order_price")
	private Double orderPrice;

	@ApiModelProperty(value = "商品数量")
	@Column(name = "goods_num")
	private Integer goodsNum;

	@ApiModelProperty(value = "国家编码")
	@Column(name = "ship_country_code")
	private String shipCountryCode;
	
	@ApiModelProperty(value = "州/省编码")
	@Column(name = "ship_state_code")
	private String shipStateCode;

	@ApiModelProperty(value = "创建时间")
	@Column(name = "create_time")
	private Long createTime;

	public OrderData() {

	}

	public OrderData(Map<String,Object> order) {
		this.setSn((String)order.get("sn"));
		this.setBuyerName((String)order.get("buyer_name"));
		this.setBuyerId((Integer)order.get("buyer_id"));
		this.setOrderStatus((String)order.get("order_status"));
		this.setPayStatus((String)order.get("pay_status"));
		this.setOrderPrice((Double)order.get("order_price"));
		this.setGoodsNum((Integer)order.get("goods_num"));
		this.setCreateTime((Long)order.get("create_time"));
		this.setShipCountryCode((String)order.get("ship_country_code"));
		this.setShipStateCode((String)order.get("ship_state_code"));
	}
	public OrderData(OrderDO order) {
		this.setSn(order.getSn());
		this.setPayStatus(order.getPayStatus());
		this.setOrderStatus(order.getOrderStatus());
		this.setBuyerName(order.getMemberName());
		this.setBuyerId(order.getMemberId());
		this.setCreateTime(order.getCreateTime());
		this.setGoodsNum(order.getGoodsNum());
		this.setOrderPrice(order.getOrderPrice());
		this.setShipCountryCode(order.getShipCountryCode());
		this.setShipStateCode(order.getShipStateCode());
	}


	@Override
	public String toString() {
		return "OrderData{" +
				"id=" + id +
				", buyerId=" + buyerId +
				", buyerName='" + buyerName + '\'' +
				", sn='" + sn + '\'' +
				", orderStatus='" + orderStatus + '\'' +
				", payStatus='" + payStatus + '\'' +
				", orderPrice=" + orderPrice +
				", goodsNum=" + goodsNum +
				", shipCountryCode='" + shipCountryCode + '\'' +
				", shipStateCode='" + shipStateCode + '\'' +
				", createTime=" + createTime +
				'}';
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public Double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(Double orderPrice) {
		this.orderPrice = orderPrice;
	}

	public Integer getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}

	public String getShipCountryCode() {
		return shipCountryCode;
	}

	public void setShipCountryCode(String shipCountryCode) {
		this.shipCountryCode = shipCountryCode;
	}

	public String getShipStateCode() {
		return shipStateCode;
	}

	public void setShipStateCode(String shipStateCode) {
		this.shipStateCode = shipStateCode;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
}
