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
