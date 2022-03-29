/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.trade.order.model.vo;

import dev.shopflix.core.member.model.vo.MemberReceiptVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 发票
 *
 * @author kingapex
 * @version 1.0
 * @since v7.0.0
 * 2017年5月24日下午9:13:53
 */
@ApiModel(description = "发票")
public class ReceiptVO extends MemberReceiptVO implements Serializable {

    private static final long serialVersionUID = -6389742728556211209L;
    /**
     * 普票类型
     */
    @ApiModelProperty(name = "type", value = "普票类型，0为个人，其他为公司", required = false)
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ReceiptVO{" +
                "type=" + type +
                '}';
    }
}
