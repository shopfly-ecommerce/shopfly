/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.goods.model.vo;

import dev.shopflix.core.goods.model.dos.CategoryDO;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 分类获取的插件返回的vo
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018年3月16日 下午4:53:39
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CategoryPluginVO extends CategoryDO implements Serializable {

    /**
     * 分类获取的插件返回的vo
     */
    private static final long serialVersionUID = -8428052730649034814L;

    @ApiModelProperty("分类id")
    private Integer id;
    @ApiModelProperty("分类名称")
    private String text;

    public CategoryPluginVO() {
    }

    public Integer getId() {
        id = this.getCategoryId();
        return id;
    }

    public String getText() {
        text = this.getName();
        return text;
    }

    @Override
    public String toString() {
        return "CategoryPluginVO [id=" + id + ", text=" + text + "]";
    }

}
