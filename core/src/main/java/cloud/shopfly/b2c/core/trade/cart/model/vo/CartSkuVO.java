/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.trade.cart.model.vo;

import cloud.shopfly.b2c.core.goods.model.vo.SpecValueVO;
import cloud.shopfly.b2c.core.trade.order.model.enums.ServiceStatusEnum;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.core.annotation.Order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车中的产品
 *
 * @author Snow
 * @version v2.0
 * 2018年03月19日21:54:35
 * @since v7.0.0
 */
@ApiModel(value = "sku", description = "产品")
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
@Order
public class CartSkuVO implements Serializable {


    private static final long serialVersionUID = -2761425455060777922L;

    /**
     * 在构造器里初始化促销列表，规格列表
     */
    public CartSkuVO() {
        this.checked = 1;
        this.invalid = 0;
        this.purchaseNum = 0;
        specList = new ArrayList<>();
        promotionTags = new ArrayList<>();
    }

    @ApiModelProperty(value = "卖家id")
    private Integer sellerId;

    @ApiModelProperty(value = "卖家姓名")
    private String sellerName;

    @ApiModelProperty(value = "商品id")
    private Integer goodsId;

    @ApiModelProperty(value = "产品id")
    private Integer skuId;

    @ApiModelProperty(value = "产品sn")
    private String skuSn;

    @ApiModelProperty(value = "商品所属的分类id")
    private Integer catId;

    @ApiModelProperty(value = "购买数量")
    private Integer num;

    @ApiModelProperty(value = "优惠数量数量")
    private Integer purchaseNum;

    @ApiModelProperty(value = "商品重量")
    private Double goodsWeight;

    @ApiModelProperty(value = "商品原价")
    private Double originalPrice;

    @ApiModelProperty(value = "购买时的成交价")
    private Double purchasePrice;

    @ApiModelProperty(value = "小计")
    private Double subtotal;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "商品图片")
    private String goodsImage;

    /**
     * 是否选中，要去结算 0:未选中 1:已选中，默认
     */
    @ApiModelProperty(value = "是否选中，要去结算")
    private Integer checked;


    @ApiModelProperty(value = "是否免运费,1：商家承担运费（免运费） 0：买家承担运费")
    private Integer isFreeFreight;

    @ApiModelProperty(value = "已参与的单品活动工具列表")
    private List<CartPromotionVo> singleList;

    @ApiModelProperty(value = "已参与的组合活动工具列表")
    private List<CartPromotionVo> groupList;

    @ApiModelProperty(value = "此商品需要提示给顾客的优惠标签")
    private List<String> promotionTags;

    @ApiModelProperty(value = "不参与活动")
    private Integer notJoinPromotion;

    @ApiModelProperty(value = "运费模板id")
    private Integer templateId;

    /**
     * 商品规格列表
     */
    @ApiModelProperty(value = "规格列表")
    private List<SpecValueVO> specList;

    /**
     * 积分换购活动中，购买这个商品需要消费的积分。
     */
    @ApiModelProperty(value = "使用积分")
    private Integer point;

    @ApiModelProperty(value = "快照ID")
    private Integer snapshotId;

    @ApiModelProperty(value = "售后状态")
    private String serviceStatus;

    @ApiModelProperty(name = "last_modify", value = "最后修改时间")
    private Long lastModify;

    @ApiModelProperty(name = "enable_quantity", value = "可用库存")
    private Integer enableQuantity;

    private PromotionRule rule;

    @ApiModelProperty(value = "是否失效：0:正常 1:已失效")
    private Integer invalid;

    @ApiModelProperty(value = "购物车商品错误消息")
    private String errorMessage;

    @ApiModelProperty(value = "是否可配送  1可配送（有货）0  不可配送（无货）")
    private Integer isShip;

    @ApiModelProperty(name = "goods_type", value = "商品类型NORMAL普通POINT积分")
    private String goodsType;


    public Integer getPurchaseNum() {
        return purchaseNum;
    }

    public void setPurchaseNum(Integer purchaseNum) {
        this.purchaseNum = purchaseNum;
    }

    public Integer getIsShip() {
        if (isShip == null) {
            return 1;
        }
        return isShip;
    }

    public void setIsShip(Integer isShip) {
        this.isShip = isShip;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public String getSkuSn() {
        return skuSn;
    }

    public void setSkuSn(String skuSn) {
        this.skuSn = skuSn;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }


    public Integer getInvalid() {
        return invalid;
    }

    public void setInvalid(Integer invalid) {
        this.invalid = invalid;
    }

    public Double getGoodsWeight() {
        if (this.goodsWeight == null) {
            return 0.0;
        }
        return goodsWeight;
    }

    public void setGoodsWeight(Double goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }


    public Integer getIsFreeFreight() {
        return isFreeFreight;
    }

    public void setIsFreeFreight(Integer isFreeFreight) {
        this.isFreeFreight = isFreeFreight;
    }

    public List<CartPromotionVo> getSingleList() {
        return singleList;
    }

    public void setSingleList(List<CartPromotionVo> singleList) {
        this.singleList = singleList;
    }

    public List<CartPromotionVo> getGroupList() {
        if (groupList == null) {
            return new ArrayList<>();
        }
        return groupList;
    }

    public void setGroupList(List<CartPromotionVo> groupList) {
        this.groupList = groupList;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public List<SpecValueVO> getSpecList() {
        return specList;
    }

    public void setSpecList(List<SpecValueVO> specList) {
        this.specList = specList;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getSnapshotId() {
        return snapshotId;
    }

    public void setSnapshotId(Integer snapshotId) {
        this.snapshotId = snapshotId;
    }

    public Long getLastModify() {
        return lastModify;
    }

    public void setLastModify(Long lastModify) {
        this.lastModify = lastModify;
    }

    public String getServiceStatus() {
        if (serviceStatus == null) {
            serviceStatus = ServiceStatusEnum.NOT_APPLY.value();
        }
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public Integer getEnableQuantity() {
        return enableQuantity;
    }

    public void setEnableQuantity(Integer enableQuantity) {
        this.enableQuantity = enableQuantity;
    }

    public List<String> getPromotionTags() {
        return promotionTags;
    }

    public void setPromotionTags(List<String> promotionTags) {
        this.promotionTags = promotionTags;
    }

    public PromotionRule getRule() {
        return rule;
    }

    public void setRule(PromotionRule rule) {
        this.rule = rule;
    }

    public String getErrorMessage() {
        if (errorMessage == null) {
            return "";
        }
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Integer getNotJoinPromotion() {

        if (notJoinPromotion == null) {
            return 0;
        }
        return notJoinPromotion;
    }

    public void setNotJoinPromotion(Integer notJoinPromotion) {
        this.notJoinPromotion = notJoinPromotion;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    @Override
    public String toString() {
        return "CartSkuVO{" +
                "sellerId=" + sellerId +
                ", sellerName='" + sellerName + '\'' +
                ", goodsId=" + goodsId +
                ", skuId=" + skuId +
                ", skuSn='" + skuSn + '\'' +
                ", catId=" + catId +
                ", num=" + num +
                ", purchaseNum=" + purchaseNum +
                ", goodsWeight=" + goodsWeight +
                ", originalPrice=" + originalPrice +
                ", purchasePrice=" + purchasePrice +
                ", subtotal=" + subtotal +
                ", name='" + name + '\'' +
                ", goodsImage='" + goodsImage + '\'' +
                ", checked=" + checked +
                ", isFreeFreight=" + isFreeFreight +
                ", singleList=" + singleList +
                ", groupList=" + groupList +
                ", promotionTags=" + promotionTags +
                ", notJoinPromotion=" + notJoinPromotion +
                ", templateId=" + templateId +
                ", specList=" + specList +
                ", point=" + point +
                ", snapshotId=" + snapshotId +
                ", serviceStatus='" + serviceStatus + '\'' +
                ", lastModify=" + lastModify +
                ", enableQuantity=" + enableQuantity +
                ", rule=" + rule +
                ", invalid=" + invalid +
                ", errorMessage='" + errorMessage + '\'' +
                ", isShip=" + isShip +
                ", goodsType='" + goodsType + '\'' +
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

        CartSkuVO skuVO = (CartSkuVO) o;

        return new EqualsBuilder()
                .append(sellerId, skuVO.sellerId)
                .append(sellerName, skuVO.sellerName)
                .append(goodsId, skuVO.goodsId)
                .append(skuId, skuVO.skuId)
                .append(skuSn, skuVO.skuSn)
                .append(catId, skuVO.catId)
                .append(num, skuVO.num)
                .append(goodsWeight, skuVO.goodsWeight)
                .append(originalPrice, skuVO.originalPrice)
                .append(purchasePrice, skuVO.purchasePrice)
                .append(subtotal, skuVO.subtotal)
                .append(name, skuVO.name)
                .append(goodsImage, skuVO.goodsImage)
                .append(checked, skuVO.checked)
                .append(isFreeFreight, skuVO.isFreeFreight)
                .append(singleList, skuVO.singleList)
                .append(groupList, skuVO.groupList)
                .append(templateId, skuVO.templateId)
                .append(specList, skuVO.specList)
                .append(point, skuVO.point)
                .append(snapshotId, skuVO.snapshotId)
                .append(serviceStatus, skuVO.serviceStatus)
                .append(lastModify, skuVO.lastModify)
                .append(enableQuantity, skuVO.enableQuantity)
                .append(goodsType, skuVO.goodsType)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(sellerId)
                .append(sellerName)
                .append(goodsId)
                .append(skuId)
                .append(skuSn)
                .append(catId)
                .append(num)
                .append(goodsWeight)
                .append(originalPrice)
                .append(purchasePrice)
                .append(subtotal)
                .append(name)
                .append(goodsImage)
                .append(checked)
                .append(isFreeFreight)
                .append(singleList)
                .append(groupList)
                .append(templateId)
                .append(specList)
                .append(point)
                .append(snapshotId)
                .append(serviceStatus)
                .append(lastModify)
                .append(enableQuantity)
                .append(goodsType)
                .toHashCode();
    }


}
