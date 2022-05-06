/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.goods.model.dos;

import cloud.shopfly.b2c.framework.database.annotation.Column;
import cloud.shopfly.b2c.framework.database.annotation.Id;
import cloud.shopfly.b2c.framework.database.annotation.PrimaryKeyField;
import cloud.shopfly.b2c.framework.database.annotation.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import java.io.Serializable;
import java.util.Objects;


/**
 * 商品sku实体
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-21 11:48:40
 */
@Table(name = "es_goods_sku")
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GoodsSkuDO implements Serializable {

    private static final long serialVersionUID = 5102510694003249L;

    /**
     * 主键
     */
    @Id(name = "sku_id")
    @ApiModelProperty(hidden = true)
    private Integer skuId;
    /**
     * 商品id
     */
    @Column(name = "goods_id")
    @ApiModelProperty(name = "goods_id", value = "商品id", hidden = true)
    private Integer goodsId;
    /**
     * 商品名称
     */
    @Column(name = "goods_name")
    @ApiModelProperty(name = "goods_name", value = "商品名称", hidden = true)
    private String goodsName;
    /**
     * 商品编号
     */
    @Column(name = "sn")
    @ApiModelProperty(name = "sn", value = "商品编号", required = false)
    @Length(max = 30, message = "商品规格编号太长，不能超过30个字符")
    private String sn;
    /**
     * 库存
     */
    @Column(name = "quantity")
    @ApiModelProperty(name = "quantity", value = "库存", required = false)
    @Max(value = 99999999, message = "库存不能超过99999999")
    private Integer quantity;
    /**
     * 可用库存
     */
    @Column(name = "enable_quantity")
    @ApiModelProperty(name = "enable_quantity", value = "可用库存")
    private Integer enableQuantity;
    /**
     * 商品价格
     */
    @Column(name = "price")
    @ApiModelProperty(name = "price", value = "商品价格", required = false)
    @Max(value = 99999999, message = "价格不能超过99999999")
    private Double price;
    /**
     * 规格信息json
     */
    @Column(name = "specs")
    @ApiModelProperty(name = "specs", value = "规格信息json", hidden = true)
    @JsonIgnore
    private String specs;
    /**
     * 成本价格
     */
    @Column(name = "cost")
    @ApiModelProperty(name = "cost", value = "成本价格", required = true)
    @Max(value = 99999999, message = "成本价格不能超过99999999")
    private Double cost;
    /**
     * 重量
     */
    @Column(name = "weight")
    @ApiModelProperty(name = "weight", value = "重量", required = true)
    @Max(value = 99999999, message = "重量不能超过99999999")
    private Double weight;
    /**
     * 卖家id
     */
    @ApiModelProperty(name = "seller_id", value = "卖家id", hidden = true)
    private Integer sellerId;
    /**
     * 卖家名称
     */
    @ApiModelProperty(name = "seller_name", value = "卖家名称", hidden = true)
    private String sellerName;
    /**
     * 分类id
     */
    @Column(name = "category_id")
    @ApiModelProperty(name = "category_id", value = "分类id", hidden = true)
    private Integer categoryId;
    /**
     * 缩略图
     */
    @Column(name = "thumbnail")
    @ApiModelProperty(name = "thumbnail", value = "缩略图", hidden = true)
    private String thumbnail;


    @Column(name = "hash_code")
    @ApiModelProperty(name = "hash_code", value = "hash_code", hidden = true)
    private Integer hashCode;


    private Integer templateId;

    public GoodsSkuDO() {
    }

    @PrimaryKeyField
    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getEnableQuantity() {
        return enableQuantity;
    }

    public void setEnableQuantity(Integer enableQuantity) {
        this.enableQuantity = enableQuantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getHashCode() {
        return hashCode;
    }

    public void setHashCode(Integer hashCode) {
        this.hashCode = hashCode;
    }

    public Integer getSellerId() {
        return 1;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return "平台自营";
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    @Override
    public String toString() {
        return "GoodsSkuDO{" +
                "skuId=" + skuId +
                ", goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", sn='" + sn + '\'' +
                ", quantity=" + quantity +
                ", enableQuantity=" + enableQuantity +
                ", price=" + price +
                ", specs='" + specs + '\'' +
                ", cost=" + cost +
                ", weight=" + weight +
                ", categoryId=" + categoryId +
                ", thumbnail='" + thumbnail + '\'' +
                ", hashCode=" + hashCode +
                ", templateId=" + templateId +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        GoodsSkuDO that = (GoodsSkuDO) o;
        return Objects.equals(skuId, that.skuId) &&
                Objects.equals(goodsId, that.goodsId) &&
                Objects.equals(goodsName, that.goodsName) &&
                Objects.equals(sn, that.sn) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(enableQuantity, that.enableQuantity) &&
                Objects.equals(price, that.price) &&
                Objects.equals(specs, that.specs) &&
                Objects.equals(cost, that.cost) &&
                Objects.equals(weight, that.weight) &&
                Objects.equals(categoryId, that.categoryId) &&
                Objects.equals(thumbnail, that.thumbnail) &&
                Objects.equals(hashCode, that.hashCode) &&
                Objects.equals(templateId, that.templateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skuId, goodsId, goodsName, sn, quantity, enableQuantity, price, specs, cost, weight, categoryId, thumbnail, hashCode, templateId);
    }
}