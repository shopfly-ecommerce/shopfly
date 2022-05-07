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
package cloud.shopfly.b2c.core.trade.cart.model.vo;

import cloud.shopfly.b2c.core.goods.model.vo.GoodsSkuVO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kingapex on 2018/12/10.
 * Shopping cart raw data
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/12/10
 */
public class CartSkuOriginVo extends GoodsSkuVO implements Serializable {

    private static final long serialVersionUID = -7457589664804806186L;

    private int num;

    private int checked;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }


    /**
     * Can participate in single product activities
     */
    private List<CartPromotionVo> singleList;


    /**
     * Group activities that you can participate in
     */
    private List<CartPromotionVo> groupList;

    public List<CartPromotionVo> getSingleList() {
        return singleList;
    }

    public void setSingleList(List<CartPromotionVo> singleList) {
        this.singleList = singleList;
    }

    public List<CartPromotionVo> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<CartPromotionVo> groupList) {
        this.groupList = groupList;
    }

    @Override
    public String toString() {
        return "CartSkuOriginVo{" +
                "num=" + num +
                ", checked=" + checked +
                ", singleList=" + singleList +
                ", groupList=" + groupList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CartSkuOriginVo that = (CartSkuOriginVo) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(num, that.num)
                .append(checked, that.checked)
                .append(singleList, that.singleList)
                .append(groupList, that.groupList)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(num)
                .append(checked)
                .append(singleList)
                .append(groupList)
                .toHashCode();
    }
}
