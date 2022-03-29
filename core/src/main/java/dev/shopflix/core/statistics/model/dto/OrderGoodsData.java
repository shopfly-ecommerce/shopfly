/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.statistics.model.dto;

import dev.shopflix.core.trade.order.model.dos.OrderDO;
import dev.shopflix.core.trade.order.model.dos.OrderItemsDO;
import dev.shopflix.framework.database.annotation.Column;
import dev.shopflix.framework.database.annotation.Id;
import dev.shopflix.framework.database.annotation.Table;
import dev.shopflix.framework.util.CurrencyUtil;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

/**
 * 订单商品数据
 *
 * @author chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/3/22 下午11:51
 */

@Table(name="es_sss_order_goods_data")
public class OrderGoodsData {

    @ApiModelProperty(value = "主键id")
    @Column(name = "id")
    @Id
    private Integer id;

    @ApiModelProperty(value = "订单编号")
    @Column(name = "order_sn")
    private String orderSn;

    @ApiModelProperty(value = "商品id")
    @Column(name = "goods_id")
    private Integer goodsId;

    @ApiModelProperty(value = "购买数量")
    @Column(name = "goods_num")
    private Integer goodsNum;


    @ApiModelProperty(value = "商品名称")
    @Column(name = "goods_name")
    private String goodsName;

    @ApiModelProperty(value = "商品价格")
    @Column(name = "price")
    private Double price;


    @ApiModelProperty(value = "小记")
    @Column(name = "sub_total")
    private Double subTotal;


    @ApiModelProperty(value = "分类id")
    @Column(name = "category_id")
    private Integer categoryId;

    @ApiModelProperty(value = "分类path")
    @Column(name = "category_path")
    private String categoryPath;

    @ApiModelProperty(value = "行业id")
    @Column(name = "industry_id")
    private Integer industryId;

    @ApiModelProperty(value = "创建时间")
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
