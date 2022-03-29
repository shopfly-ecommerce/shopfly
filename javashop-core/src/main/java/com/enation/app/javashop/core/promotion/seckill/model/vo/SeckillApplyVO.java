/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.promotion.seckill.model.vo;

import com.enation.app.javashop.core.promotion.seckill.model.dos.SeckillApplyDO;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 限时抢购商品申请VO
 *
 * @author Snow create in 2018/6/28
 * @version v2.0
 * @since v7.0.0
 */
public class SeckillApplyVO extends SeckillApplyDO {

    @ApiModelProperty(value="状态文字值")
    private String statusText;

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    @Override
    public String toString() {
        return "SeckillApplyVO{" +
                "statusText='" + statusText + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (o == null || getClass() != o.getClass()){
            return false;
        }

        SeckillApplyVO that = (SeckillApplyVO) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(statusText, that.statusText)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(statusText)
                .toHashCode();
    }


}
