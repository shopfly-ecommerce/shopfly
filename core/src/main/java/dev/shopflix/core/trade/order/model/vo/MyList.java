/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.trade.order.model.vo;

import dev.shopflix.core.trade.order.model.enums.OrderStatusEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-03-11
 */

public class MyList {
    private List<OrderFlowNode> list;

    public MyList() {
        this.list = new ArrayList<>();
    }

    public MyList add(OrderStatusEnum status) {
        OrderFlowNode orderCreateFlow = new OrderFlowNode(status);
        list.add(orderCreateFlow);
        return this;
    }

    public List<OrderFlowNode> getList() {

        return list;
    }


}
