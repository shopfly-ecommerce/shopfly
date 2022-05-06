/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.goods.model.vo;

import java.io.Serializable;

/**
 * @author fk
 * @version v2.0
 * @Description: 商品的操作权限
 * @date 2018/4/916:06
 * @since v7.0.0
 */
public class OperateAllowable implements Serializable {

    /**
     * 上架状态1上架  0下架
     */
    private Integer marketEnable;

    /**
     * 删除状态0 删除 1未删除
     */
    private Integer disabled;

    /**
     * 是否允许下架
     */
    private Boolean allowUnder;
    /**
     * 是否允许放入回收站
     */
    private Boolean allowRecycle;
    /**
     * 是否允许回收站还原
     */
    private Boolean allowRevert;
    /**
     * 是否允许彻底删除
     */
    private Boolean allowDelete;

    /**
     * 是否允许上架
     */
    private Boolean allowMarket;

    public OperateAllowable(Integer marketEnable, Integer disabled) {
        this.marketEnable = marketEnable;
        this.disabled = disabled;
    }

    public OperateAllowable() {

    }

    public Boolean getAllowUnder() {
        //上架并且没有删除的可以下架
        return marketEnable == 1 && disabled == 1;
    }

    public Boolean getAllowRecycle() {
        //下架的商品才能放入回收站

        return marketEnable == 0 && disabled == 1;
    }

    public Boolean getAllowRevert() {
        //下架的删除了的才能还原
        return marketEnable == 0 && disabled == 0;
    }

    public Boolean getAllowDelete() {
        //下架的删除了的才能还原
        return marketEnable == 0 && disabled == 0;
    }

    public Boolean getAllowMarket() {
        //下架未删除才能上架
        return marketEnable == 0 && disabled == 1;
    }
}
