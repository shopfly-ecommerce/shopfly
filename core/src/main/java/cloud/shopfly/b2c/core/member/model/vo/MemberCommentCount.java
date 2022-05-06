/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.member.model.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author fk
 * @version v2.0
 * @Description: 评论数量
 * @date 2018/9/12 11:10
 * @since v7.0.0
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MemberCommentCount {

    /**
     * 全部评论数量
     */
    @ApiModelProperty(name = "all_count", value = "全部评论数量")
    private Integer allCount;

    /**
     * 好评数量
     */
    @ApiModelProperty(name = "good_count", value = "好评数量")
    private Integer goodCount;

    /**
     * 中评数量
     */
    @ApiModelProperty(name = "neutral_count", value = "中评数量")
    private Integer neutralCount;

    /**
     * 差评数量
     */
    @ApiModelProperty(name = "bad_count", value = "差评数量")
    private Integer badCount;

    /**
     * 带有图片的评论数量
     */
    @ApiModelProperty(name = "image_count", value = "带有图片的评论数量")
    private Integer imageCount;

    public Integer getAllCount() {
        return allCount;
    }

    public void setAllCount(Integer allCount) {
        this.allCount = allCount;
    }

    public Integer getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(Integer goodCount) {
        this.goodCount = goodCount;
    }

    public Integer getNeutralCount() {
        return neutralCount;
    }

    public void setNeutralCount(Integer neutralCount) {
        this.neutralCount = neutralCount;
    }

    public Integer getBadCount() {
        return badCount;
    }

    public void setBadCount(Integer badCount) {
        this.badCount = badCount;
    }

    public Integer getImageCount() {
        return imageCount;
    }

    public void setImageCount(Integer imageCount) {
        this.imageCount = imageCount;
    }

    public MemberCommentCount() {
    }

    public MemberCommentCount(Integer allCount, Integer goodCount, Integer neutralCount, Integer badCount, Integer imageCount) {
        this.allCount = allCount;
        this.goodCount = goodCount;
        this.neutralCount = neutralCount;
        this.badCount = badCount;
        this.imageCount = imageCount;
    }

    @Override
    public String toString() {
        return "MemberCommentCount{" +
                "allCount=" + allCount +
                ", goodCount=" + goodCount +
                ", neutralCount=" + neutralCount +
                ", badCount=" + badCount +
                ", imageCount=" + imageCount +
                '}';
    }
}
