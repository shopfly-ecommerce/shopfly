/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.goods.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 商品查询条件
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018年3月21日 下午3:46:04
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GoodsQueryParam {
    /**
     * 页码
     */
    @ApiModelProperty(name = "page_no", value = "页码", required = false)
    private Integer pageNo;
    /**
     * 分页数
     */
    @ApiModelProperty(name = "page_size", value = "分页数", required = false)
    private Integer pageSize;
    /**
     * 是否上架 0代表已下架，1代表已上架
     */
    @ApiModelProperty(name = "market_enable", value = "是否上架 0代表已下架，1代表已上架")
    @Min(value = 0 , message = "审核状态不正确")
    @Max(value = 2 , message = "审核状态不正确")
    private Integer marketEnable;

    /**
     * 关键字
     */
    @ApiModelProperty(name = "keyword", value = "关键字")
    private String keyword;
    /**
     * 商品名称
     */
    @ApiModelProperty(name = "goods_name", value = "商品名称")
    private String goodsName;
    /**
     * 商品编号
     */
    @ApiModelProperty(name = "goods_sn", value = "商品编号")
    private String goodsSn;

    @ApiModelProperty(name = "category_path", value = "商品分类路径，例如0|10|")
    private String categoryPath;

    @ApiModelProperty(name = "disabled", value = "0 查询回收站商品")
    @Min(value = 0 , message = "值不正确")
    @Max(value = 0 , message = "值不正确")
    private Integer disabled;

    @ApiModelProperty(name = "goods_type", value = "商品类型 NORMAL 正常商品  POINT 积分商品")
    private String  goodsType;


    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getMarketEnable() {
        return marketEnable;
    }

    public void setMarketEnable(Integer marketEnable) {
        this.marketEnable = marketEnable;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsSn() {
        return goodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }

    public String getCategoryPath() {
        return categoryPath;
    }

    public void setCategoryPath(String categoryPath) {
        this.categoryPath = categoryPath;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }
}
