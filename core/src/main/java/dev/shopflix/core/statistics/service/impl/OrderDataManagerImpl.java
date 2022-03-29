/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.statistics.service.impl;

import dev.shopflix.core.goods.model.dos.CategoryDO;
import dev.shopflix.core.goods.service.CategoryManager;
import dev.shopflix.core.statistics.model.dto.OrderData;
import dev.shopflix.core.statistics.model.dto.OrderGoodsData;
import dev.shopflix.core.statistics.service.OrderDataManager;
import dev.shopflix.core.trade.order.model.dos.OrderDO;
import dev.shopflix.core.trade.order.model.dos.OrderItemsDO;
import dev.shopflix.core.trade.order.service.OrderQueryManager;
import dev.shopflix.framework.database.DaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 订单实现
 *
 * @author chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/6/22 下午10:11
 */
@Service
public class OrderDataManagerImpl implements OrderDataManager {

    @Autowired
    @Qualifier("sssDaoSupport")
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
     * 获取第二级别分类。
     *
     * @param path
     * @return
     */
    private Integer getIndustry(String path) {
        try {
            String pattern = "(0\\|)(\\d+)";
            // 创建 Pattern 对象
            Pattern r = Pattern.compile(pattern);
            // 现在创建 matcher 对象
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
