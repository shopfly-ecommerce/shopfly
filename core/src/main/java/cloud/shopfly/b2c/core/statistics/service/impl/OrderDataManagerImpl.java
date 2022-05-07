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
package cloud.shopfly.b2c.core.statistics.service.impl;

import cloud.shopfly.b2c.core.statistics.model.dto.OrderData;
import cloud.shopfly.b2c.core.statistics.model.dto.OrderGoodsData;
import cloud.shopfly.b2c.core.statistics.service.OrderDataManager;
import cloud.shopfly.b2c.core.trade.order.model.dos.OrderDO;
import cloud.shopfly.b2c.core.trade.order.model.dos.OrderItemsDO;
import cloud.shopfly.b2c.core.trade.order.service.OrderQueryManager;
import cloud.shopfly.b2c.core.goods.model.dos.CategoryDO;
import cloud.shopfly.b2c.core.goods.service.CategoryManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Order to achieve
 *
 * @author chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/6/22 In the afternoon10:11
 */
@Service
public class OrderDataManagerImpl implements OrderDataManager {

    @Autowired
    
    private DaoSupport daoSupport;


    @Autowired
    private OrderQueryManager orderQueryManager;

    @Autowired
    private CategoryManager categoryManager;

    @Override
    public void put(OrderDO order) {
        List<OrderItemsDO> itemsDOList = orderQueryManager.orderItems(order.getSn());
        int goodsNum = 0;
        for (OrderItemsDO oi : itemsDOList) {
            OrderGoodsData orderGoodsData = new OrderGoodsData(oi, order);
            CategoryDO categoryDO = categoryManager.getModel(oi.getCatId());
            orderGoodsData.setIndustryId(getIndustry(categoryDO.getCategoryPath()));
            orderGoodsData.setCategoryPath(categoryDO.getCategoryPath());
            this.daoSupport.insert("es_sss_order_goods_data", orderGoodsData);
            goodsNum = goodsNum + oi.getNum();
        }
        order.setGoodsNum(goodsNum);
        daoSupport.insert("es_sss_order_data", new OrderData(order));
    }

    @Override
    public void change(OrderDO order) {

        OrderData od = this.daoSupport.queryForObject("select * from es_sss_order_data where sn = ?", OrderData.class,
                order.getSn());
        if (od != null) {
            od.setOrderStatus(order.getOrderStatus());
            od.setPayStatus(order.getPayStatus());
        } else {
            od = new OrderData(order);
        }
        Map<String, String> where = new HashMap(16);
        where.put("sn", order.getSn());
        daoSupport.update("es_sss_order_data", od, where);
    }


    /**
     * Gets the second level classification.
     *
     * @param path
     * @return
     */
    private Integer getIndustry(String path) {
        try {
            String pattern = "(0\\|)(\\d+)";
            // Create Pattern object
            Pattern r = Pattern.compile(pattern);
            // Now create the Matcher object
            Matcher m = r.matcher(path);
            if (m.find()) {
                return new Integer(m.group(2));
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
