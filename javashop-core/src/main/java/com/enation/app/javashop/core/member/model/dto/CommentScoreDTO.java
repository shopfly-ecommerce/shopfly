/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.member.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 评论评分VO
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-05-03 10:38:00
 */
@ApiModel(description = "评论动态评分vo")
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CommentScoreDTO implements Serializable {

    @ApiModelProperty(value = "会员评论vo的list")
    @NotNull(message = "商品评论不能为空")
    @Valid
    private List<CommentDTO> comments;

    /**
     * 订单编号
     */
    @ApiModelProperty(name = "order_sn", value = "订单编号", required = true)
    @NotEmpty(message = "订单编号不能为空")
    private String orderSn;


    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    @Override
    public String toString() {
        return "CommentScoreDTO{" +
                "comments=" + comments +
                ", orderSn='" + orderSn + '\'' +
                '}';
    }
}
