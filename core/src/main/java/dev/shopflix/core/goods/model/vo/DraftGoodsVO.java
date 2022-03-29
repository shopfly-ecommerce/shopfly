/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.goods.model.vo;

import dev.shopflix.core.goods.model.dos.DraftGoodsDO;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Arrays;

/**
 * 草稿箱商品vo
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/7/6 上午3:00
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DraftGoodsVO extends DraftGoodsDO {

    /**
     * 商品分类名称
     */
    @ApiModelProperty(name = "category_name", value = "商品分类名称", required = false)
    private String categoryName;

    @ApiModelProperty(name = "category_ids", value = "分类id数组")
    private Integer[] categoryIds;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer[] getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(Integer[] categoryIds) {
        this.categoryIds = categoryIds;
    }

    @Override
    public String toString() {
        return "DraftGoodsVO{" +
                "categoryName='" + categoryName + '\'' +
                ", categoryIds=" + Arrays.toString(categoryIds) +
                '}';
    }
}