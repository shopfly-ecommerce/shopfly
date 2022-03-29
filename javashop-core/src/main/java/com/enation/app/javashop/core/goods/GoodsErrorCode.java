/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.goods;

/**
 * 商品异常码 Created by kingapex on 2018/3/13.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0 2018/3/13
 */
public enum GoodsErrorCode {

    /**
     * 分类相关异常
     */
    E300("分类相关异常"),
    /**
     * 商品相关异常
     */
    E301("商品相关异常"),
    /**
     * 品牌相关异常
     */
    E302("品牌相关异常"),
    /**
     * 参数相关异常
     */
    E303("参数相关异常"),
    /**
     * 参数组相关异常
     */
    E304("参数组相关异常"),
    /**
     * 规格相关异常
     */
    E305("规格相关异常"),
    /**
     * 规格值相关异常
     */
    E306("规格值相关异常"),
    /**
     * 商品库存相关异常
     */
    E307("商品库存相关异常"),
    /**
     * 草稿商品相关异常
     */
    E308("草稿商品相关异常"),
    /**
     * 标签相关异常
     */
    E309("标签相关异常");

    private String describe;

    GoodsErrorCode(String des) {
        this.describe = des;
    }

    /**
     * 获取商品的异常码
     *
     * @return
     */
    public String code() {
        return this.name().replaceAll("E", "");
    }

}
