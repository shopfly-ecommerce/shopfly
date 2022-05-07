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
 * @Description: Operation rights of goods
 * @date 2018/4/916:06
 * @since v7.0.0
 */
public class OperateAllowable implements Serializable {

    /**
     * On state1save0off
     */
    private Integer marketEnable;

    /**
     * Delete the state0 delete1未delete
     */
    private Integer disabled;

    /**
     * Whether to allow removal
     */
    private Boolean allowUnder;
    /**
     * Whether it is allowed in the recycle bin
     */
    private Boolean allowRecycle;
    /**
     * Whether to allow recycle bin restore
     */
    private Boolean allowRevert;
    /**
     * Whether to allow complete deletion
     */
    private Boolean allowDelete;

    /**
     * Is it allowed to be on shelves?
     */
    private Boolean allowMarket;

    public OperateAllowable(Integer marketEnable, Integer disabled) {
        this.marketEnable = marketEnable;
        this.disabled = disabled;
    }

    public OperateAllowable() {

    }

    public Boolean getAllowUnder() {
        // Shelves and not deleted can be removed
        return marketEnable == 1 && disabled == 1;
    }

    public Boolean getAllowRecycle() {
        // Only goods removed from the shelves can be put into the recycling bin

        return marketEnable == 0 && disabled == 1;
    }

    public Boolean getAllowRevert() {
        // Deleted from the shelves to restore
        return marketEnable == 0 && disabled == 0;
    }

    public Boolean getAllowDelete() {
        // Deleted from the shelves to restore
        return marketEnable == 0 && disabled == 0;
    }

    public Boolean getAllowMarket() {
        // It can only be put on the shelves if it is not deleted
        return marketEnable == 0 && disabled == 1;
    }
}
