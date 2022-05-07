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
package cloud.shopfly.b2c.core.promotion.pintuan.model;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
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
 * Group order entity
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
     * The orderid
     */
    @Id(name = "order_id")
    @ApiModelProperty(hidden = true)
    private Integer orderId;

    @Column(name = "pintuan_id")
    @Min(message = "Must be a number", value = 0)
    @ApiModelProperty(name = "pintuan_id", value = "Spell groupid")
    private Integer pintuanId;
    /**
     * The end of time
     */
    @Column(name = "end_time")
    @Min(message = "Must be a number", value = 0)
    @ApiModelProperty(name = "end_time", value = "The end of time")
    private Long endTime;
    /**
     * sku_id
     */
    @Column(name = "sku_id")
    @Min(message = "Must be a number", value = 0)
    @ApiModelProperty(name = "sku_id", value = "sku_id", required = true)
    private Integer skuId;

    /**
     * Name
     */
    @Column(name = "goods_name")
    @ApiModelProperty(name = "goods_name", value = "Name")
    private String goodsName;

    @Column(name = "goods_id")
    @Min(message = "Must be a number", value = 0)
    @ApiModelProperty(name = "goods_id", value = "productid")
    private Integer goodsId;

    /**
     * Thumbnail path
     */
    @Column(name = "thumbnail")
    @ApiModelProperty(name = "thumbnail", value = "Thumbnail path")
    private String thumbnail;


    /**
     * The number of clusters
     */
    @Column(name = "required_num")
    @Min(message = "Must be a number", value = 0)
    @ApiModelProperty(name = "required_num", value = "The number of clusters")
    private Integer requiredNum;


    /**
     * Number of participants
     */
    @Column(name = "offered_num")
    @Min(message = "Must be a number", value = 0)
    @ApiModelProperty(name = "offered_num", value = "Number of participants")
    private Integer offeredNum;

    /**
     * Tuxedo people
     */
    @Column(name = "offered_persons")
    @NotEmpty(message = "Participants are not allowed to be empty")
    @ApiModelProperty(name = "offered_persons", value = "Tuxedo people", hidden = true)
    @JsonIgnore
    private String offeredPersons;

    /**
     * Status
     * new_order A group order is created as a new order
     * wait After payment of the order
     * formed clouds
     */
    @Column(name = "order_status")
    @ApiModelProperty(name = "order_status", value = "Status")
    private String orderStatus;

    @ApiModelProperty(name = "participants", value = "List of participants")
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
     * Join a participant
     */
    public void appendParticipant(Participant participant) {
        List<Participant> list = new ArrayList<>();

        // If it is not empty, it means participation
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

            // If it is empty, it indicates the leader
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
