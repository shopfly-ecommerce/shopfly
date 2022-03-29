/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.client.goods;

import java.util.List;
import java.util.Map;

/**
 * @author fk
 * @version v2.0
 * @Description: 商品索引
 * @date 2018/8/14 14:11
 * @since v7.0.0
 */
public interface GoodsIndexClient {


    /**
     * 将某个商品加入索引<br>
     * @param goods
     */
    void addIndex(Map goods);

    /**
     * 更新某个商品的索引
     * @param goods
     */
    void updateIndex(Map goods);


    /**
     * 更新
     * @param goods
     */
    void deleteIndex(Map goods);

    /**
     * 初始化索引
     * @param list
     * @param index
     * @return  是否是生成成功
     */
    boolean addAll(List<Map<String, Object>> list, int index);

}
