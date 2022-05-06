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
package cloud.shopfly.b2c.core.member.model.dto;

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
