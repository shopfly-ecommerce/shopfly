/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.trade.cart.model.vo;

import dev.shopflix.core.goods.model.vo.GoodsSkuVO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kingapex on 2018/12/10.
 * 购物车原始数据
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
     * 可以参与的单品活动
     */
    private List<CartPromotionVo> singleList;


    /**
     * 可以参与的组合活动
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
