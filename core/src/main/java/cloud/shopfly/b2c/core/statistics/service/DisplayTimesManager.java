/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.statistics.service;

import cloud.shopfly.b2c.core.statistics.model.dos.GoodsPageView;

import java.util.List;

/**
 * 访问次数manager
 *
 * @author liushuai
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/8/7 上午8:21
 */

public interface DisplayTimesManager {


    /**
     * 访问某地址
     *
     * @param url  访问的地址
     * @param uuid 客户唯一id
     */
    void view(String url, String uuid);

    /**
     * 将统计好的商品数据 写入数据库
     *
     * @param list
     */
    void countGoods(List<GoodsPageView> list);


    /**
     * 立即整理现有的数据
     */
    void countNow();


}
