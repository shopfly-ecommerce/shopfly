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

import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.util.StringUtil;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 销售记录VO
 *
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018-06-29 上午9:32
 */
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SalesVO implements Serializable {
    @ApiModelProperty(name="buyer_name",value="买家",required=false)
    @Column(name = "buyer_name")
    private String buyerName;

    @ApiModelProperty(name="price",value="价格",required=false)
    @Column(name = "price")
    private Double price;

    @ApiModelProperty(name="num",value="数量",required=false)
    @Column(name = "num")
    private Integer num;

    @ApiModelProperty(name="pay_time",value="付款日期",required=false)
    @Column(name = "pay_time")
    private Integer payTime;


    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        if(!StringUtil.isEmpty(buyerName)){
            if(buyerName.length()==1){
                this.buyerName="***";
            }else {
                this.buyerName = buyerName.substring(0, 1) + "***" + buyerName.substring(buyerName.length() - 1, buyerName.length());
            }
        }
    }
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getPayTime() {
        return payTime;
    }

    public void setPayTime(Integer payTime) {
        this.payTime = payTime;
    }

    @Override
    public String toString() {
        return "SalesVO{" +
                "buyerName='" + buyerName + '\'' +
                ", price=" + price +
                ", num=" + num +
                ", payTime=" + payTime +
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

        SalesVO salesVO = (SalesVO) o;

        if (buyerName != null ? !buyerName.equals(salesVO.buyerName) : salesVO.buyerName != null) {
            return false;
        }
        if (price != null ? !price.equals(salesVO.price) : salesVO.price != null) {
            return false;
        }
        if (num != null ? !num.equals(salesVO.num) : salesVO.num != null) {
            return false;
        }
        return payTime != null ? payTime.equals(salesVO.payTime) : salesVO.payTime == null;
    }

    @Override
    public int hashCode() {
        int result = buyerName != null ? buyerName.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (num != null ? num.hashCode() : 0);
        result = 31 * result + (payTime != null ? payTime.hashCode() : 0);
        return result;
    }
}
