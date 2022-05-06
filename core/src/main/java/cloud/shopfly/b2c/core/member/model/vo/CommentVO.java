/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.member.model.vo;

import cloud.shopfly.b2c.core.member.model.dos.CommentReply;
import cloud.shopfly.b2c.core.member.model.dos.MemberComment;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author fk
 * @version v2.0
 * @Description: 评论vo
 * @date 2018/5/31 5:03
 * @since v7.0.0
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CommentVO extends MemberComment {

    @ApiModelProperty(name = "images", value = "评论图片", required = false)
    private List<String> images;

    @ApiModelProperty(name = "reply", value = "评论回复", required = false)
    private CommentReply reply;

    @ApiModelProperty(name = "add_images", value = "追加评论图片", required = false)
    private List<String> addImages;

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public CommentReply getReply() {
        return reply;
    }

    public void setReply(CommentReply reply) {
        this.reply = reply;
    }

    public List<String> getAddImages() {
        return addImages;
    }

    public void setAddImages(List<String> addImages) {
        this.addImages = addImages;
    }

    @Override
    public String toString() {
        return "CommentVO{" +
                "images=" + images +
                ", reply=" + reply +
                ", addImages=" + addImages +
                '}';
    }
}
