/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.promotion.tool.model.vo;

import com.enation.app.javashop.core.promotion.fulldiscount.model.vo.FullDiscountVO;
import com.enation.app.javashop.framework.database.annotation.Column;

/**
 * Created by kingapex on 2018/12/18.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/18
 */
public class FullDiscountWithGoodsId extends FullDiscountVO {
    public FullDiscountWithGoodsId() {
    }

    @Column(name = "goods_id")
    private int goodsId;

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }
}
