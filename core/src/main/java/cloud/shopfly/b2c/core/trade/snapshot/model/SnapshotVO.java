/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.trade.snapshot.model;

import cloud.shopfly.b2c.core.goods.model.dos.GoodsGalleryDO;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsParamsGroupVO;
import cloud.shopfly.b2c.core.goods.model.vo.SpecValueVO;
import cloud.shopfly.b2c.core.promotion.coupon.model.dos.CouponDO;
import cloud.shopfly.b2c.core.promotion.tool.model.vo.PromotionVO;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author fk
 * @version v2.0
 * @Description: 快照
 * @date 2018/8/1 16:41
 * @since v7.0.0
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SnapshotVO extends GoodsSnapshot {

    @ApiModelProperty(value = "相册列表")
    private List<GoodsGalleryDO> galleryList;

    @ApiModelProperty(value = "参数列表")
    private List<GoodsParamsGroupVO> paramList;

    @ApiModelProperty(value = "规格列表")
    private List<SpecValueVO> specList;

    @ApiModelProperty(value = "促销列表")
    private List<PromotionVO> promotionList;

    @ApiModelProperty(value = "优惠券列表")
    private List<CouponDO> couponList;

    public SnapshotVO() {
    }


    public List<GoodsGalleryDO> getGalleryList() {

        if (!StringUtil.isEmpty(this.getImgJson())) {
            return JsonUtil.jsonToList(this.getImgJson(), GoodsGalleryDO.class);
        }

        return galleryList;
    }

    public void setGalleryList(List<GoodsGalleryDO> galleryList) {
        this.galleryList = galleryList;
    }

    public List<GoodsParamsGroupVO> getParamList() {
        if (!StringUtil.isEmpty(this.getParamsJson())) {
            return JsonUtil.jsonToList(this.getParamsJson(), GoodsParamsGroupVO.class);
        }
        return paramList;
    }

    public void setParamList(List<GoodsParamsGroupVO> paramList) {
        this.paramList = paramList;
    }

    public List<SpecValueVO> getSpecList() {

        return specList;
    }

    public void setSpecList(List<SpecValueVO> specList) {

        this.specList = specList;
    }

    public List<PromotionVO> getPromotionList() {

        if (!StringUtil.isEmpty(this.getPromotionJson())) {
            return JsonUtil.jsonToList(this.getPromotionJson(), PromotionVO.class);
        }

        return promotionList;
    }

    public void setPromotionList(List<PromotionVO> promotionList) {
        this.promotionList = promotionList;
    }

    public List<CouponDO> getCouponList() {

        if (!StringUtil.isEmpty(this.getCouponJson())) {
            return JsonUtil.jsonToList(this.getCouponJson(), CouponDO.class);
        }
        return couponList;
    }

    public void setCouponList(List<CouponDO> couponList) {
        this.couponList = couponList;
    }

    @Override
    public String toString() {
        return "SnapshotVO{" +
                "galleryList=" + galleryList +
                ", paramList=" + paramList +
                ", specList=" + specList +
                '}';
    }
}
