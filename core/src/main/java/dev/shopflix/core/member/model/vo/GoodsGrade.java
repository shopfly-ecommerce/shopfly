/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.member.model.vo;

/**
 * @author fk
 * @version v1.0
 * @Description: 商品好平率
 * @date 2018/5/4 10:45
 * @since v7.0.0
 */
public class GoodsGrade {
    /**
     * 商品id
     */
    private Integer goodsId;
    /**
     * 好评率
     */
    private Double goodRate;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Double getGoodRate() {
        return goodRate;
    }

    public void setGoodRate(Double goodRate) {
        this.goodRate = goodRate;
    }
}
