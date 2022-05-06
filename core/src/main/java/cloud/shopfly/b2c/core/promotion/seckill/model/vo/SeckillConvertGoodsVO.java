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
package cloud.shopfly.b2c.core.promotion.seckill.model.vo;

/**
 * 注释
 *
 * @author Snow create in 2018/3/20
 * @version v2.0
 * @since v7.0.0
 */
@SuppressWarnings("AlibabaPojoMustOverrideToString")
public class SeckillConvertGoodsVO {

    private String goodsName;
    private Double price;
    private String thumbnail;
    private Integer goodsId;
    private Integer soldNum;

    @Override
    public String toString() {
        return "SeckillConvertGoodsVO{" +
                "goodsName='" + goodsName + '\'' +
                ", price=" + price +
                ", thumbnail='" + thumbnail + '\'' +
                ", goodsId=" + goodsId +
                ", soldNum=" + soldNum +
                '}';
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getSoldNum() {
        return soldNum;
    }

    public void setSoldNum(Integer soldNum) {
        this.soldNum = soldNum;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
