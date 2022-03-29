/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.promotion.pintuan.model;

import dev.shopflix.framework.database.annotation.Column;
import dev.shopflix.framework.database.annotation.Id;
import dev.shopflix.framework.database.annotation.PrimaryKeyField;
import dev.shopflix.framework.database.annotation.Table;
import dev.shopflix.framework.util.JsonUtil;
import dev.shopflix.framework.util.StringUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;


/**
 * 拼团订单实体
 *
 * @author admin
 * @version vv1.0.0
 * @since vv7.1.0
 * 2019-01-24 15:10:01
 */
@Table(name = "es_pintuan_order")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PintuanOrder {


    /**
     * 订单id
     */
    @Id(name = "order_id")
    @ApiModelProperty(hidden = true)
    private Integer orderId;

    @Column(name = "pintuan_id")
    @Min(message = "必须为数字", value = 0)
    @ApiModelProperty(name = "pintuan_id", value = "拼团id")
    private Integer pintuanId;
    /**
     * 结束时间
     */
    @Column(name = "end_time")
    @Min(message = "必须为数字", value = 0)
    @ApiModelProperty(name = "end_time", value = "结束时间")
    private Long endTime;
    /**
     * sku_id
     */
    @Column(name = "sku_id")
    @Min(message = "必须为数字", value = 0)
    @ApiModelProperty(name = "sku_id", value = "sku_id", required = true)
    private Integer skuId;

    /**
     * 商品名称
     */
    @Column(name = "goods_name")
    @ApiModelProperty(name = "goods_name", value = "商品名称")
    private String goodsName;

    @Column(name = "goods_id")
    @Min(message = "必须为数字", value = 0)
    @ApiModelProperty(name = "goods_id", value = "商品id")
    private Integer goodsId;

    /**
     * 缩略图路径
     */
    @Column(name = "thumbnail")
    @ApiModelProperty(name = "thumbnail", value = "缩略图路径")
    private String thumbnail;


    /**
     * 成团人数
     */
    @Column(name = "required_num")
    @Min(message = "必须为数字", value = 0)
    @ApiModelProperty(name = "required_num", value = "成团人数")
    private Integer requiredNum;


    /**
     * 已参团人数
     */
    @Column(name = "offered_num")
    @Min(message = "必须为数字", value = 0)
    @ApiModelProperty(name = "offered_num", value = "已参团人数")
    private Integer offeredNum;

    /**
     * 参团人
     */
    @Column(name = "offered_persons")
    @NotEmpty(message = "参团人不能为空")
    @ApiModelProperty(name = "offered_persons", value = "参团人", hidden = true)
    @JsonIgnore
    private String offeredPersons;

    /**
     * 订单状态
     * new_order 拼团订单创建时为新订单状态
     * wait 订单支付后
     * formed 成团
     */
    @Column(name = "order_status")
    @ApiModelProperty(name = "order_status", value = "订单状态")
    private String orderStatus;

    @ApiModelProperty(name = "participants", value = "参团人列表")
    private List<Participant> participants;

    public List<Participant> getParticipants() {
        if (!StringUtil.isEmpty(offeredPersons)) {
            participants = JsonUtil.jsonToList(offeredPersons, Participant.class);
        } else {
            participants = new ArrayList<>();
        }
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    /**
     * 加入一个参团者
     */
    public void appendParticipant(Participant participant) {
        List<Participant> list = new ArrayList<>();

        //如果不为空说明是参团
        if (!StringUtil.isEmpty(this.offeredPersons)) {
            list = JsonUtil.jsonToList(offeredPersons, Participant.class);
            List<Participant> listCopy = new ArrayList<>();
            listCopy.addAll(list);

            list.forEach(part -> {
                if (part.getId() == null || part.getId() == -1) {
                    listCopy.remove(part);
                }
            });

            list.clear();
            list.addAll(listCopy);

            //为空说明是团长
        } else {
            participant.setIsMaster(1);

        }
        list.add(participant);

        offeredPersons = JsonUtil.objectToJson(list);
    }

    public static void main(String[] args) {
        PintuanOrder pintuanOrder = new PintuanOrder();
        Participant participant = new Participant();
        participant.setName("kingapex");
        participant.setId(1);
        participant.setFace("//xx/xx.jpg");
        pintuanOrder.appendParticipant(participant);

        System.out.println(pintuanOrder.getOfferedPersons());

        Participant participant1 = new Participant();
        participant1.setName("kingapex1");
        participant1.setId(2);
        participant1.setFace("//xx/xx1.jpg");


        pintuanOrder.appendParticipant(participant1);
        System.out.println(pintuanOrder.getOfferedPersons());

    }

    @PrimaryKeyField
    public Integer getOrderId() {
        return orderId;
    }


    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }


    public Integer getPintuanId() {
        return pintuanId;
    }

    public void setPintuanId(Integer pintuanId) {
        this.pintuanId = pintuanId;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getRequiredNum() {
        return requiredNum;
    }

    public void setRequiredNum(Integer requiredNum) {
        this.requiredNum = requiredNum;
    }

    public Integer getOfferedNum() {
        return offeredNum;
    }

    public void setOfferedNum(Integer offeredNum) {
        this.offeredNum = offeredNum;
    }

    public String getOfferedPersons() {
        return offeredPersons;
    }

    public void setOfferedPersons(String offeredPersons) {
        this.offeredPersons = offeredPersons;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    @Override
    public String toString() {
        return "PintuanOrder{" +
                "orderId=" + orderId +
                ", pintuanId=" + pintuanId +
                ", endTime=" + endTime +
                ", skuId=" + skuId +
                ", goodsName='" + goodsName + '\'' +
                ", goodsId=" + goodsId +
                ", thumbnail='" + thumbnail + '\'' +
                ", requiredNum=" + requiredNum +
                ", offeredNum=" + offeredNum +
                ", offeredPersons='" + offeredPersons + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", participants=" + participants +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PintuanOrder that = (PintuanOrder) o;

        return new EqualsBuilder()
                .append(orderId, that.orderId)
                .append(pintuanId, that.pintuanId)
                .append(endTime, that.endTime)
                .append(skuId, that.skuId)
                .append(goodsId, that.goodsId)
                .append(requiredNum, that.requiredNum)
                .append(offeredNum, that.offeredNum)
                .append(offeredPersons, that.offeredPersons)
                .append(orderStatus, that.orderStatus)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(orderId)
                .append(pintuanId)
                .append(endTime)
                .append(skuId)
                .append(goodsId)
                .append(requiredNum)
                .append(offeredNum)
                .append(offeredPersons)
                .append(orderStatus)
                .toHashCode();
    }


}