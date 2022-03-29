/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.statistics.model.dto;

import com.enation.app.javashop.core.trade.order.model.dos.OrderDO;
import com.enation.app.javashop.framework.database.annotation.Column;
import com.enation.app.javashop.framework.database.annotation.Id;
import com.enation.app.javashop.framework.database.annotation.Table;
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

	@ApiModelProperty(value = "省id")
	@Column(name = "ship_province_id")
	private Integer shipProvinceId;
	
	@ApiModelProperty(value = "区id")
	@Column(name = "ship_city_id")
	private Integer shipCityId;

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
		this.setShipProvinceId((Integer)order.get("ship_province_id"));
		this.setShipCityId((Integer)order.get("ship_city_id"));
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
		this.setShipCityId(order.getShipCityId());
		this.setShipProvinceId(order.getShipProvinceId());
	}


	@Override
	public String toString() {
		return "OrderData{" +
				", buyerId=" + buyerId +
				", buyerName=" + buyerName +
				", sn='" + sn + '\'' +
				", orderStatus='" + orderStatus + '\'' +
				", payStatus='" + payStatus + '\'' +
				", orderPrice=" + orderPrice +
				", goodsNum=" + goodsNum +
				", shipProvinceid=" + shipProvinceId +
				", shipCityid=" + shipCityId +
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

	public Integer getShipProvinceId() {
		return shipProvinceId;
	}

	public void setShipProvinceId(Integer shipProvinceId) {
		this.shipProvinceId = shipProvinceId;
	}

	public Integer getShipCityId() {
		return shipCityId;
	}

	public void setShipCityId(Integer shipCityId) {
		this.shipCityId = shipCityId;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
}
